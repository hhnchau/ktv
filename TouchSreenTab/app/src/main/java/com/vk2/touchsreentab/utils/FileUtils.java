package com.vk2.touchsreentab.utils;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;

public class FileUtils {


    public static File getPathFile(String fileName, String folder) {
        File dir = new File(Environment.getExternalStoragePublicDirectory(folder).toString());
        File file = new File(dir, fileName);
        if (!file.exists())
            return null;
        return file;
    }

    public static boolean checkExistFile(String fileName, String folderName) {
        File dir = new File(Environment.getExternalStoragePublicDirectory(folderName).toString());
        File file = new File(dir, fileName);
        return file.exists();
    }

    public static String getPath(String folderName) {
        return Environment.getExternalStoragePublicDirectory(folderName).toString();
    }

    public static void createFile(ResponseBody responseBody, String sourcePath, String fileName) {
        String path = null;
        try {
            File dir = new File(sourcePath);
            boolean success = true;
            if (!dir.exists())
                success = dir.mkdir();

            if (success) {
                File file = new File(dir, fileName);
                //path = imageFile.getAbsolutePath();
                int count;
                long lengthOfFile = responseBody.contentLength();
                InputStream inputStream = responseBody.byteStream();
                FileOutputStream fileOutputStream = new FileOutputStream(file);

                byte[] data = new byte[(int) lengthOfFile];
                long total = 0;
                while ((count = inputStream.read(data)) != -1) {
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
        //return path;
    }

    public static void moveFile(String sourcePath, String fileName, String descPath) {
        InputStream in;
        OutputStream out;
        try {
            File dir = new File(descPath);
            boolean success = true;
            if (!dir.exists())
                success = dir.mkdirs();

            if (success) {
                in = new FileInputStream(sourcePath + "/" + fileName);
                out = new FileOutputStream(descPath + "/" + fileName);

                byte[] buffer = new byte[1024];
                int read;
                while ((read = in.read(buffer)) != -1) {
                    out.write(buffer, 0, read);
                }
                in.close();
                out.flush();
                out.close();

                new File(sourcePath + "/" + fileName).delete();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean deleteFile(String sourcePath, String fileName) {
        boolean success;
        try {
            success = new File(sourcePath + "/" + fileName).delete();
        } catch (Exception e) {
            e.printStackTrace();
            success = false;
        }
        return success;
    }

    public static void copyFile(String sourcePath, String fileName, String descPath) {
        InputStream in;
        OutputStream out;
        try {
            File dir = new File(descPath);
            boolean success = true;
            if (!dir.exists())
                success = dir.mkdirs();
            if (success) {
                in = new FileInputStream(sourcePath + "/" + fileName);
                out = new FileOutputStream(descPath + "/" + fileName);

                byte[] buffer = new byte[1024];
                int read;
                while ((read = in.read(buffer)) != -1) {
                    out.write(buffer, 0, read);
                }
                in.close();
                out.flush();
                out.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
