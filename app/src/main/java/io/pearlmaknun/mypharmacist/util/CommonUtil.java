package io.pearlmaknun.mypharmacist.util;

import android.view.View;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class CommonUtil {

    public static boolean validateEmptyEntries(ArrayList<View> fields) {
        boolean check = true;
        for (int i = 0; i < fields.size(); i++) {
            EditText currentField = (EditText) fields.get(i);
            if (currentField.getText().toString().length() <= 0) {
                currentField.setError("Please fill out this field");
//                showToast("Please fill out this field");
                check = false;
            }
        }
        return check;
    }

    public static String getCurrentDate() {
        return getCurrentDate("yyyy-MM-dd HH:mm:ss");
    }

    public static String getCurrentDate(String fmt) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("Asia/Jakarta"));
        SimpleDateFormat format = new SimpleDateFormat(fmt);
        return format.format(cal.getTime());
    }

    public static String generalFormatDate(String dateAsal, String formatAsal, String formatTujuan) {
        SimpleDateFormat formatter = new SimpleDateFormat(formatAsal);
        SimpleDateFormat formatToView = new SimpleDateFormat(formatTujuan);

        Date convertedDate = new Date();
        try {
            convertedDate = formatter.parse(dateAsal);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String tglKlaim = formatToView.format(convertedDate);

        return tglKlaim;
    }
}
