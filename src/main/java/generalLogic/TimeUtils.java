package generalLogic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class TimeUtils {
    static final String DATEFORMAT = "yyyy-MM-dd HH:mm:ss";
    static final String UTC_TIMEZONE_CODE = "UTC";

    public static Date getCurrentUtcDate() {
        SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMAT);
        sdf.setTimeZone(TimeZone.getTimeZone(UTC_TIMEZONE_CODE));
        String utcTime = sdf.format(new Date());

        Date dateToReturn = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATEFORMAT);
        try {
            dateToReturn = dateFormat.parse(utcTime);
        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return dateToReturn;
    }

    public static Date convertStringToDate(String input) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATEFORMAT);
        Date date = null;
        try {
            date = formatter.parse(input);
        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return date;
    }
}
