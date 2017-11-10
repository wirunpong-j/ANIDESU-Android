package com.bellkung.anidesu.custom;

import com.bellkung.anidesu.utils.KeyUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
            Date dateValue = format.parse(String.valueOf(date));
            SimpleDateFormat newFormat = new SimpleDateFormat("dd MMM yyyy");

            newDateText = newFormat.format(dateValue);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return newDateText;
    }
}
