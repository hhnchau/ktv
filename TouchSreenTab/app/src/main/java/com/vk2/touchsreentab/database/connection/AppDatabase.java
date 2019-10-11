package com.vk2.touchsreentab.database.connection;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.vk2.touchsreentab.database.dao.CategoryDao;
import com.vk2.touchsreentab.database.dao.SingerDao;
import com.vk2.touchsreentab.database.dao.SongDao;
import com.vk2.touchsreentab.database.entity.Singer;
import com.vk2.touchsreentab.database.entity.Song;
import com.vk2.touchsreentab.database.entity.SongLang;
import com.vk2.touchsreentab.database.entity.SongType;

import static android.content.Context.MODE_PRIVATE;

@Database(entities = {Singer.class, Song.class, SongType.class, SongLang.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static final String DB_NAME = "song-0905.db";

    private static AppDatabase instance;

    public static AppDatabase getAppDatabase(Context context) {
        if (instance == null) {
            copyDB(context, DB_NAME, null, null);
            instance = Room.databaseBuilder(context, AppDatabase.class, DB_NAME)
                    .addMigrations(new Migration(1, 2) {
                        @Override
                        public void migrate(@NonNull SupportSQLiteDatabase database) {
                            database.execSQL("ALTER TABLE song ADD COLUMN Selected INTEGER DEFAULT 0");
                            database.execSQL("ALTER TABLE song ADD COLUMN Spell TEXT");
                            database.execSQL("ALTER TABLE song ADD COLUMN NamePinyin TEXT");
                            database.execSQL("UPDATE song SET Spell = (SELECT singer.Spell FROM singer WHERE singer.ID = song.SingerID1) WHERE EXISTS (SELECT singer.Spell FROM singer WHERE singer.ID = song.SingerID1)");
                            database.execSQL("UPDATE song SET NamePinyin = (SELECT singer.NamePinyin FROM singer WHERE singer.ID = song.SingerID1) WHERE EXISTS (SELECT singer.Spell FROM singer WHERE singer.ID = song.SingerID1)");
                        }
                    })
                    .build();
        }
        return instance;
    }

    public static void destroyAppDatabase() {
        if (instance != null) instance = null;
    }

    private static void copyDB(Context context, String dbName, String path, SQLiteDatabase.CursorFactory factory) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, MODE_PRIVATE);
        if (!sharedPreferences.getBoolean("db", false)) {
            new SQLiteAssetHelper(context, dbName, path, factory, 1).getWritableDatabase().close();
            sharedPreferences.edit().putBoolean("db", true).apply();
        }
    }

    public abstract SongDao songDao();

    public abstract SingerDao singerDao();

    public abstract CategoryDao categoryDao();

}
