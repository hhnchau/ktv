package com.vk2.touchsreentab.download;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.vk2.touchsreentab.utils.Constants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.util.concurrent.ExecutionException;

public class ImageLoaderTask extends AsyncTask<String, Void, Bitmap> {
    private WeakReference<Context> context;

    public interface Callback {
        void onBitmap(Bitmap bitmap);
    }

    private Callback callback;
    private String fileName;

    public ImageLoaderTask(Context context, String fileName, Callback callback) {
        this.context = new WeakReference<>(context);
        this.callback = callback;
        this.fileName = fileName;
    }

    protected Bitmap doInBackground(String... urls) {
        String link = urls[0];
        Bitmap bitmap = null;
        try {
            bitmap = Glide.with(context.get())
                    .asBitmap()
                    .load(link)
                    .apply(new RequestOptions().override(-1, -1))
                    .submit().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    protected void onPostExecute(Bitmap result) {
        saveImage(result);
        if (callback != null)
            callback.onBitmap(result);
    }

    private void saveImage(Bitmap bitmap) {
        //String path = null;
        File dir = new File(Environment.getExternalStoragePublicDirectory(Constants.SINGER_FOLDER).toString());
        boolean success = true;
        if (!dir.exists())
            success = dir.mkdir();

        if (success) {
            File imageFile = new File(dir, fileName+".png");
            //path = imageFile.getAbsolutePath();
            try {
                OutputStream fOut = new FileOutputStream(imageFile);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                fOut.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //return path;
    }

    public String getImagePath(String fileName) {
        File dir = new File(Environment.getExternalStoragePublicDirectory(Constants.SINGER_FOLDER).toString());
        File file = new File(dir, fileName + ".png");
        return file.getAbsolutePath();
    }

    public static Uri getImageUri(String fileName) {
        File dir = new File(Environment.getExternalStoragePublicDirectory(Constants.SINGER_FOLDER).toString());
        File file = new File(dir, fileName + ".png");
        if (!file.exists())
            file = new File(dir, "default.png");
        return Uri.fromFile(file);
    }
}