package com.bellkung.anidesu.custom;

import com.bellkung.anidesu.utils.KeyUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by BellKunG on 9/11/2017 AD.
 */

public class FormatCustomManager {

    public static String parseString(String text) {
        if (text == null) {
            return KeyUtils.NULL_TEXT;
        }
        return text;
    }

    public static String parseToDate(int date) {
        String newDateText = KeyUtils.NULL_TEXT;
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd"); // 20171001
            Date dateValue = format.parse(String.valueOf(date));
            SimpleDateFormat newFormat = new SimpleDateFormat("dd MMM yyyy"); // 01 Oct 2017

            newDateText = newFormat.format(dateValue);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return newDateText;
    }

    public static String parseOnFirebaseDateTime(String dateTime) {

        String newDateText = null;
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm"); // 20171001
            Date dateValue = format.parse(String.valueOf(dateTime));
            SimpleDateFormat newFormat = new SimpleDateFormat("dd MMM yyyy - HH:mm"); // 01 Oct 2017

            newDateText = newFormat.format(dateValue);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return newDateText;
    }

    public static String getCurrentDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
        Date date = Calendar.getInstance().getTime();

        return dateFormat.format(date);
    }

}
