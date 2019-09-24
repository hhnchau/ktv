package com.vk2.touchsreentab.database.connection;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class SQLiteAssetHelper extends SQLiteOpenHelper {

    private static final String TAG = SQLiteAssetHelper.class.getSimpleName();
    private static final String ASSET_DB_PATH = "databases";

    private final Context mContext;
    private final String mName;
    private final SQLiteDatabase.CursorFactory mFactory;
    private final int mNewVersion;

    private SQLiteDatabase mDatabase = null;
    private boolean mIsInitializing = false;

    private String mDatabasePath;

    private String mAssetPath;

    private String mUpgradePathFormat;

    private int mForcedUpgradeVersion = 0;


    public SQLiteAssetHelper(Context context, String name, String storageDirectory, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

        if (version < 1) throw new IllegalArgumentException("Version must be >= 1, was " + version);
        if (name == null) throw new IllegalArgumentException("Database name cannot be null");

        mContext = context;
        mName = name;
        mFactory = factory;
        mNewVersion = version;

        mAssetPath = ASSET_DB_PATH + "/" + name;
        if (storageDirectory != null) {
            mDatabasePath = storageDirectory;
        } else {
            mDatabasePath = context.getApplicationInfo().dataDir + "/databases";
        }
        mUpgradePathFormat = ASSET_DB_PATH + "/" + name + "_upgrade_%s-%s.sql";
    }

    public SQLiteAssetHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        this(context, name, null, factory, version);
    }

    @Override
    public synchronized SQLiteDatabase getWritableDatabase() {
        if (mDatabase != null && mDatabase.isOpen() && !mDatabase.isReadOnly()) {
            return mDatabase;
        }

        if (mIsInitializing) {
            throw new IllegalStateException("getWritableDatabase called recursively");
        }

        boolean success = false;
        SQLiteDatabase db = null;
        try {
            mIsInitializing = true;
            db = createOrOpenDatabase(false);

            int version = db.getVersion();

            if (version != 0 && version < mForcedUpgradeVersion) {
                db = createOrOpenDatabase(true);
                db.setVersion(mNewVersion);
                version = db.getVersion();
            }

            if (version != mNewVersion) {
                db.beginTransaction();
                try {
                    if (version == 0) {
                        onCreate(db);
                    } else {
                        if (version > mNewVersion) {
                            Log.w(TAG, "Can't downgrade read-only database from version " +
                                    version + " to " + mNewVersion + ": " + db.getPath());
                        }
                        onUpgrade(db, version, mNewVersion);
                    }
                    db.setVersion(mNewVersion);
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
            }

            onOpen(db);
            success = true;
            return db;
        } finally {
            mIsInitializing = false;
            if (success) {
                if (mDatabase != null) {
                    try {
                        mDatabase.close();
                    } catch (Exception e) {
                    }
                }
                mDatabase = db;
            } else {
                if (db != null) db.close();
            }
        }

    }

    @Override
    public synchronized SQLiteDatabase getReadableDatabase() {
        if (mDatabase != null && mDatabase.isOpen()) {
            return mDatabase;
        }

        if (mIsInitializing) {
            throw new IllegalStateException("getReadableDatabase called recursively");
        }

        try {
            return getWritableDatabase();
        } catch (SQLiteException e) {
            if (mName == null) throw e;
            Log.e(TAG, "Couldn't open " + mName + " for writing (will try read-only):", e);
        }

        SQLiteDatabase db = null;
        try {
            mIsInitializing = true;
            String path = mContext.getDatabasePath(mName).getPath();
            db = SQLiteDatabase.openDatabase(path, mFactory, SQLiteDatabase.OPEN_READONLY);
            if (db.getVersion() != mNewVersion) {
                throw new SQLiteException("Can't upgrade read-only database from version " +
                        db.getVersion() + " to " + mNewVersion + ": " + path);
            }

            onOpen(db);
            Log.w(TAG, "Opened " + mName + " in read-only mode");
            mDatabase = db;
            return mDatabase;
        } finally {
            mIsInitializing = false;
            if (db != null && db != mDatabase) db.close();
        }
    }

    @Override
    public synchronized void close() {
        if (mIsInitializing) throw new IllegalStateException("Closed during initialization");

        if (mDatabase != null && mDatabase.isOpen()) {
            mDatabase.close();
            mDatabase = null;
        }
    }

    @Override
    public final void onConfigure(SQLiteDatabase db) {
    }

    @Override
    public final void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.w(TAG, "Upgrading database " + mName + " from version " + oldVersion + " to " + newVersion + "...");

        ArrayList<String> paths = new ArrayList<String>();
        getUpgradeFilePaths(oldVersion, newVersion - 1, newVersion, paths);

        if (paths.isEmpty()) {
            Log.e(TAG, "no upgrade script path from " + oldVersion + " to " + newVersion);
            throw new SQLiteAssetException("no upgrade script path from " + oldVersion + " to " + newVersion);
        }

        Collections.sort(paths, new VersionComparator());
        for (String path : paths) {
            try {
                Log.w(TAG, "processing upgrade: " + path);
                InputStream is = mContext.getAssets().open(path);
                String sql = convertStreamToString(is);
                if (sql != null) {
                    List<String> cmds = splitSqlScript(sql, ';');
                    for (String cmd : cmds) {
                        if (cmd.trim().length() > 0) {
                            db.execSQL(cmd);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Log.w(TAG, "Successfully upgraded database " + mName + " from version " + oldVersion + " to " + newVersion);

    }

    @Override
    public final void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    @Deprecated
    public void setForcedUpgradeVersion(int version) {
        setForcedUpgrade(version);
    }

    public void setForcedUpgrade(int version) {
        mForcedUpgradeVersion = version;
    }

    public void setForcedUpgrade() {
        setForcedUpgrade(mNewVersion);
    }

    private SQLiteDatabase createOrOpenDatabase(boolean force) throws SQLiteAssetException {
        SQLiteDatabase db = null;
        File file = new File(mDatabasePath + "/" + mName);
        if (file.exists()) {
            db = returnDatabase();
        }
        //SQLiteDatabase databases = returnDatabase();

        if (db != null) {
            // database already exists
            if (force) {
                Log.w(TAG, "forcing database upgrade!");
                copyDatabaseFromAssets();
                db = returnDatabase();
            }
            return db;
        } else {
            // database does not exist, copy it from assets and return it
            copyDatabaseFromAssets();
            db = returnDatabase();
            return db;
        }
    }

    private SQLiteDatabase returnDatabase() {
        try {
            SQLiteDatabase db = SQLiteDatabase.openDatabase(mDatabasePath + "/" + mName, mFactory, SQLiteDatabase.OPEN_READWRITE);
            Log.i(TAG, "successfully opened database " + mName);
            return db;
        } catch (SQLiteException e) {
            Log.w(TAG, "could not open database " + mName + " - " + e.getMessage());
            return null;
        }
    }

    private void copyDatabaseFromAssets() throws SQLiteAssetException {
        Log.w(TAG, "copying database from assets...");

        String path = mAssetPath;
        String dest = mDatabasePath + "/" + mName;
        InputStream is;
        boolean isZip = false;

        try {
            is = mContext.getAssets().open(path);
        } catch (IOException e) {
            try {
                is = mContext.getAssets().open(path + ".zip");
                isZip = true;
            } catch (IOException e2) {
                try {
                    is = mContext.getAssets().open(path + ".gz");
                } catch (IOException e3) {
                    SQLiteAssetException se = new SQLiteAssetException("Missing " + mAssetPath + " file (or .zip, .gz archive) in assets, or target folder not writable");
                    se.setStackTrace(e3.getStackTrace());
                    throw se;
                }
            }
        }

        try {
            File f = new File(mDatabasePath + "/");
            if (!f.exists()) {
                f.mkdir();
            }
            if (isZip) {
                ZipInputStream zis = getFileFromZip(is);
                if (zis == null) {
                    throw new SQLiteAssetException("Archive is missing a SQLite database file");
                }
                writeExtractedFileToDisk(zis, new FileOutputStream(dest));
            } else {
                writeExtractedFileToDisk(is, new FileOutputStream(dest));
            }

            Log.w(TAG, "database copy complete");

        } catch (IOException e) {
            SQLiteAssetException se = new SQLiteAssetException("Unable to write " + dest + " to data directory");
            se.setStackTrace(e.getStackTrace());
            throw se;
        }
    }

    private InputStream getUpgradeSQLStream(int oldVersion, int newVersion) {
        String path = String.format(mUpgradePathFormat, oldVersion, newVersion);
        try {
            return mContext.getAssets().open(path);
        } catch (IOException e) {
            Log.w(TAG, "missing database upgrade script: " + path);
            return null;
        }
    }

    private void getUpgradeFilePaths(int baseVersion, int start, int end, ArrayList<String> paths) {

        int a;
        int b;

        InputStream is = getUpgradeSQLStream(start, end);
        if (is != null) {
            String path = String.format(mUpgradePathFormat, start, end);
            paths.add(path);
            a = start - 1;
            b = start;
            is = null;
        } else {
            a = start - 1;
            b = end;
        }

        if (a < baseVersion) {
            return;
        } else {
            getUpgradeFilePaths(baseVersion, a, b, paths);
        }

    }

    @SuppressWarnings("serial")
    public static class SQLiteAssetException extends SQLiteException {

        public SQLiteAssetException() {
        }

        public SQLiteAssetException(String error) {
            super(error);
        }
    }


    public static List<String> splitSqlScript(String script, char delim) {
        List<String> statements = new ArrayList<String>();
        StringBuilder sb = new StringBuilder();
        boolean inLiteral = false;
        char[] content = script.toCharArray();
        for (int i = 0; i < script.length(); i++) {
            if (content[i] == '"') {
                inLiteral = !inLiteral;
            }
            if (content[i] == delim && !inLiteral) {
                if (sb.length() > 0) {
                    statements.add(sb.toString().trim());
                    sb = new StringBuilder();
                }
            } else {
                sb.append(content[i]);
            }
        }
        if (sb.length() > 0) {
            statements.add(sb.toString().trim());
        }
        return statements;
    }

    public static void writeExtractedFileToDisk(InputStream in, OutputStream outs) throws IOException {
        byte[] buffer = new byte[1024];
        int length;
        while ((length = in.read(buffer)) > 0) {
            outs.write(buffer, 0, length);
        }
        outs.flush();
        outs.close();
        in.close();
    }

    public static ZipInputStream getFileFromZip(InputStream zipFileStream) throws IOException {
        ZipInputStream zis = new ZipInputStream(zipFileStream);
        ZipEntry ze;
        while ((ze = zis.getNextEntry()) != null) {
            Log.w(TAG, "extracting file: '" + ze.getName() + "'...");
            return zis;
        }
        return null;
    }

    public static String convertStreamToString(InputStream is) {
        return new Scanner(is).useDelimiter("\\A").next();
    }


    private class VersionComparator implements Comparator<String> {
        private Pattern pattern = Pattern
                .compile(".*_upgrade_([0-9]+)-([0-9]+).*");

        @Override
        public int compare(String file0, String file1) {
            Matcher m0 = pattern.matcher(file0);
            Matcher m1 = pattern.matcher(file1);

            if (!m0.matches()) {
                Log.w(TAG, "could not parse upgrade script file: " + file0);
                throw new SQLiteAssetException("Invalid upgrade script file");
            }

            if (!m1.matches()) {
                Log.w(TAG, "could not parse upgrade script file: " + file1);
                throw new SQLiteAssetException("Invalid upgrade script file");
            }

            int v0_from = Integer.valueOf(m0.group(1));
            int v1_from = Integer.valueOf(m1.group(1));
            int v0_to = Integer.valueOf(m0.group(2));
            int v1_to = Integer.valueOf(m1.group(2));

            if (v0_from == v1_from) {
                // 'from' versions match for both; check 'to' version next

                if (v0_to == v1_to) {
                    return 0;
                }

                return v0_to < v1_to ? -1 : 1;
            }

            return v0_from < v1_from ? -1 : 1;
        }

        @SuppressWarnings("serial")
        public class SQLiteAssetException extends SQLiteException {

            public SQLiteAssetException() {
            }

            public SQLiteAssetException(String error) {
                super(error);
            }
        }
    }
}