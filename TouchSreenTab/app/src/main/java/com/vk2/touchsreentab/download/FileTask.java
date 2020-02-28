package com.vk2.touchsreentab.download;

import android.os.AsyncTask;
import android.os.Environment;

import com.vk2.touchsreentab.utils.Constants;
import com.vk2.touchsreentab.utils.FileUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.ResponseBody;
import retrofit2.Response;


public class FileTask extends AsyncTask<ResponseBody, Void, String> {
    @Override
    protected String doInBackground(ResponseBody... responseBodies) {
        String path = null;
        ResponseBody responseBody = responseBodies[0];

        String sourcePath = Environment.getExternalStoragePublicDirectory(Constants.DB_FOLDER).toString();
        FileUtils.createFile(responseBody, sourcePath, Constants.DB_NAME);

        return path;
    }

    protected void onProgressUpdate(String... progress) {
        // setting progress percentage
        //pd.setProgress(Integer.parseInt(progress[0]));
    }
}
