package com.vk2.touchsreentab.utils;

import androidx.annotation.NonNull;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TimeUtils {
    public static final String yyyyMMdd = "yyyyMMdd";
    public static final String yyyyMMddd = "yyyy-MM-dd";
    public static final String ddMMyyyy = "dd/MM/yyyy";
    public static final String ddMMyyyyHHmm = "dd/MM/yyyy HH:mm";
    public static final String ddMMyyyyTHHmmss = "yyyy-MM-dd'T'HH:mm:ss";


    public static String dateConvert(String date, String requestFormat, String responseFormat) {
        if (date != null) {
            SimpleDateFormat response = new SimpleDateFormat(responseFormat, Locale.ENGLISH);
            SimpleDateFormat request = new SimpleDateFormat(requestFormat, Locale.ENGLISH);

            try {
                Date d = request.parse(date);
                return response.format(d);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public static Calendar dateStringConvert(String date, String requestFormat) {
        Calendar cal = Calendar.getInstance();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(requestFormat, Locale.ENGLISH);
            cal.setTime(sdf.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return cal;
    }

    public static String getCurrentDate(String responseFormat) {
        Calendar calendar = Calendar.getInstance();
        //return dateFormat(calendar, responseFormat);
        return "20190101";
    }

    public static String dateFormat(Calendar calendar, @NonNull String responseFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(responseFormat, Locale.ENGLISH);

        return sdf.format(calendar.getTime());
    }

    public static String formatCurrency(float currency) {
        DecimalFormat formatter = new DecimalFormat("#,###");
        String current = formatter.format(currency);
        return current.replaceAll("\\.", ",");
    }
}
