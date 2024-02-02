package a2_2001040230.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static String pattern = "dd/MM/yyyy";

    public static Date getCurrentDate() {
        return new Date();
    }

    public static String getCurrentDateStr() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(getCurrentDate());
    }

    public static String convertDateFormatStr(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
    }

    public static Date convertDateFormat(String dateStr) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.parse(dateStr);
    }
}
