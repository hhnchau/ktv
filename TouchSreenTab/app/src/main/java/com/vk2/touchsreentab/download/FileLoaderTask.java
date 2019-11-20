package com.vk2.touchsreentab.download;
import android.os.AsyncTask;
import android.os.Environment;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class FileLoaderTask extends AsyncTask<String, Void, String> {
    private static final String REQUEST_METHOD = "GET";
    private static final int READ_TIMEOUT = 15000;
    private static final int CONNECTION_TIMEOUT = 15000;

    @Override
    protected String doInBackground(String... strings) {
        String stringUrl = strings[0];
        String path = null;

        try {

            URL myUrl = new URL(stringUrl);
            HttpURLConnection connection = (HttpURLConnection) myUrl.openConnection();
            connection.setRequestMethod(REQUEST_METHOD);
            connection.setReadTimeout(READ_TIMEOUT);
            connection.setConnectTimeout(CONNECTION_TIMEOUT);
            connection.setRequestProperty ("Authorization", "FS F8kyw8cqfMVLX5Bk9y8G4vDVxEcanAMH:1573703676767:QL02jywEnRLsGSi5JrEhEPVN5gw=");
            connection.setRequestProperty ("Content-Type", "application/json");
            connection.connect();

            File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES) + "Song_Folder");
            boolean success = true;
            if (!dir.exists())
                success = dir.mkdir();

            if (success) {
                File file = new File(dir, "fileName1" + ".mp4");
                //path = imageFile.getAbsolutePath();
                int count;
                int lengthOfFile = connection.getContentLength();
                InputStream inputStream = new BufferedInputStream(myUrl.openStream());
                FileOutputStream fileOutputStream = new FileOutputStream(file);

                byte[] data = new byte[1024];
                long total = 0;
                while ((count = inputStream.read(data)) != -1){
                    total += count;
                    //publishProgress("" + (int) ((total * 100) / lengthOfFile));
                    fileOutputStream.write(data, 0, count);
                }

                fileOutputStream.flush();
                fileOutputStream.close();
                inputStream.close();
                //return path;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }

    protected void onProgressUpdate(String... progress) {
        // setting progress percentage
        //pd.setProgress(Integer.parseInt(progress[0]));
    }
}
