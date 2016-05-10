package com.bandsintown.activityfeed.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.os.Bundle;

import com.bandsintown.activityfeed.FeedValues;
import com.bandsintown.activityfeed.R;
import com.bandsintown.activityfeed.objects.FeedItemInterface;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by rjaylward on 5/4/16 for Bandsintown
 */
public class FeedUtil {

    public static String printCommaSeparatedArray(Object[] array) {
        String result = "";
        for(int i = 0; i < array.length; i++) {
            if(i > 0)
                result += ", ";

            result += array[i].toString();
        }

        return result;
    }

    public static String printArrayListAsString(ArrayList arrayList) {
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < arrayList.size(); i++) {
            if(i > 0)
                builder.append(", ");

            builder.append(arrayList.get(i));
        }

        return builder.toString();
    }

    public static String formatDatetime(String datetime, SimpleDateFormat format) {
        if(datetime != null) {
            Calendar cal = convertDatetimeToCalendar(datetime);

            if(cal != null) {
                String formatted = format.format(cal.getTime());

                if(format.equals(new SimpleDateFormat(FeedValues.DATE_FORMAT_MONTH_ABV_WITH_DAY, Locale.getDefault())))
                    formatted += getDayOfMonthSuffix(cal.get(Calendar.DAY_OF_MONTH));

                return formatted;
            }
            else return null;
        }
        else
            return null;
    }

    public static Calendar convertDatetimeToCalendar(String datetime) {
        if(datetime != null) {
            Calendar cal = Calendar.getInstance();

            try {
                cal.setTime(new SimpleDateFormat(FeedValues.DATE_FORMAT_BANDSINTOWN_API_WITH_TIMEZONE, Locale.getDefault()).parse(datetime));
                return cal;
            } catch(ParseException e) {
                try {
                    cal.setTime(new SimpleDateFormat(FeedValues.DATE_FORMAT_BANDSINTOWN_API, Locale.getDefault()).parse(datetime));
                    return cal;
                } catch(ParseException e1) {
                    Print.exception(e);
                    return null;
                }
            }
        }
        else
            return null;
    }

    public static String getDayOfMonthSuffix(int n) {
        if(n < 1 || n > 31)
            throw new IllegalArgumentException("n needs to be a real day of the month");
        if(n >= 11 && n <= 13)
            return "th";
        switch (n % 10) {
            case 1:  return "st";
            case 2:  return "nd";
            case 3:  return "rd";
            default: return "th";
        }
    }

    public static long convertDatetimeToMillis(String datetime) {
        Calendar calendar = convertDatetimeToCalendar(datetime);
        if(calendar != null)
            return calendar.getTimeInMillis();
        else
            return 0;
    }

    public static String timestampToDatetime(long timestamp) {
        Date date = new Date(timestamp);
        return new SimpleDateFormat(FeedValues.DATE_FORMAT_BANDSINTOWN_API, Locale.getDefault()).format(date);
    }

    public static String timestampToDatetimeWithTimezone(long timestamp) {
        Date date = new Date(timestamp);
        return new SimpleDateFormat(FeedValues.DATE_FORMAT_BANDSINTOWN_API_WITH_TIMEZONE, Locale.getDefault()).format(date);
    }

    public static String timestampToDatetimeCalendarDay(long timestamp) {
        Date date = new Date(timestamp);
        return new SimpleDateFormat(FeedValues.DATE_FORMAT_CALENDAR_DAY, Locale.getDefault()).format(date);
    }

    public static boolean isSameDay(String datetime1, String datetime2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();

        try {
            cal1.setTime(new SimpleDateFormat(FeedValues.DATE_FORMAT_BANDSINTOWN_API, Locale.getDefault()).parse(datetime1));
            cal2.setTime(new SimpleDateFormat(FeedValues.DATE_FORMAT_BANDSINTOWN_API, Locale.getDefault()).parse(datetime2));

            return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
                    && cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
        } catch(ParseException e) {
            Print.exception(e);
            throw new IllegalArgumentException("parser didn't work");
        }
    }

    /**
     * Method to compare to datetimes
     *
     * @param time1 first time
     * @param time2 second time
     * @return 1 if time1 is later, 2 if time2 is later, 0 if equal
     */
    public static int compareDatetimes(String time1, String time2) {
        long millis1 = convertDatetimeToMillis(time1);
        long millis2 = convertDatetimeToMillis(time2);

        if(millis1 > millis2)
            return 1;
        else if(millis2 > millis1)
            return 2;
        else
            return 0;
    }

    public static boolean checkIfDatetimeHasPassed(String datetime) {
        return convertDatetimeToMillis(datetime) < System.currentTimeMillis();
    }

    /**
     * Formats the time for a timer (3:12 for instance)
     *
     * @param millis the millis
     * @return string rep of the time
     */
    public static String formatTimerStringFromMillis(long millis) {
        return new SimpleDateFormat(FeedValues.DATE_FORMAT_TIMER, Locale.getDefault()).format(new Date(millis));
    }

    public static boolean isAppInstalled(Context context, String uri) {
        PackageManager pm = context.getPackageManager();

        boolean installed;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            installed = true;
        }
        catch (Exception e) {
            installed = false;
        }

        Print.log("Is installed?", uri, installed);

        return installed;
    }

    public static String getFormattedLocationFromAddress(Address address) {
        if(address == null || (address.getCountryCode() == null && address.getLocality() == null))
            return null;
        if(address.getCountryCode() != null) {
            boolean useAdminArea = address.getCountryCode().equals("US") || address.getCountryCode().equals("CA");

            return String.format("%s, %s", address.getLocality(), useAdminArea ? address.getAdminArea() : address.getCountryCode());
        }
        else
            return address.getLocality();
    }

    public static String getStars(Context context, FeedItemInterface item) {
        String star = context.getString(R.string.star_icon);
        String half = context.getString(R.string.half_character);

        StringBuilder builder = new StringBuilder();

        for(int i = 1; i < item.getObject().getPost().getRating(); i++)
            builder.append(star);

        if(item.getObject().getPost().getRating() % 1 != 0) {
            //It's not a whole number, needs a 1/2 symbol at the end
            builder.append(half);
        }

        return builder.toString();
    }

    public static int getTypeFromVerb(String verb, int groupSize) {
        //FeedItemInterface always has a size of 1, otherwise it is an FeedGroupInterface
        if(groupSize != 1) {
            switch(verb) {
                case FeedValues.VERB_USER_TRACKING:
                    return FeedValues.VERB_CODE_GROUP_USER_TRACKING;
                case FeedValues.VERB_ARTIST_TRACKING:
                    return FeedValues.VERB_CODE_GROUP_ARTIST_TRACKING;
                case FeedValues.VERB_RSVP:
                    return FeedValues.VERB_CODE_GROUP_RSVP;
                case FeedValues.VERB_LIKE:
                    return FeedValues.VERB_CODE_GROUP_LIKE;
                case FeedValues.VERB_LISTEN:
                    return FeedValues.VERB_CODE_GROUP_LISTEN;
                case FeedValues.VERB_REQUEST:
                    return FeedValues.VERB_CODE_GROUP_REQUEST;
                case FeedValues.VERB_RATE:
                    return FeedValues.VERB_CODE_GROUP_RATE;
                case FeedValues.VERB_USER_POST:
                    return FeedValues.VERB_CODE_GROUP_USER_POST;
                case FeedValues.VERB_EVENT_ANNOUNCEMENT:
                    return FeedValues.VERB_CODE_GROUP_EVENT_ANNOUNCEMENT;
                case FeedValues.VERB_MESSAGE_RSVPS:
                    return FeedValues.VERB_CODE_GROUP_MESSAGE_RSVPS;
                case FeedValues.VERB_PROMOTE:
                    return FeedValues.VERB_CODE_GROUP_PROMOTE;
                case FeedValues.VERB_ARTIST_POST:
                    return FeedValues.VERB_CODE_GROUP_ARTIST_POST;
                case FeedValues.VERB_WATCH_TRAILER:
                    return FeedValues.VERB_CODE_GROUP_WATCH_TRAILER;
                case FeedValues.VERB_POST_TRAILER:
                    return FeedValues.VERB_CODE_GROUP_POST_TRAILER;
                default:
                    Print.exception(new Exception("did not recognize: " + verb));
                    return FeedValues.VERB_CODE_UNRECOGNIZED;
            }
        }
        else
            return getTypeFromVerb(verb);
    }

    private static int getTypeFromVerb(String verb) {
        switch(verb) {
            case FeedValues.VERB_USER_TRACKING :
                return FeedValues.VERB_CODE_USER_TRACKING;
            case FeedValues.VERB_ARTIST_TRACKING :
                return FeedValues.VERB_CODE_ARTIST_TRACKING;
            case FeedValues.VERB_RSVP :
                return FeedValues.VERB_CODE_RSVP;
            case FeedValues.VERB_LIKE :
                return FeedValues.VERB_CODE_LIKE;
            case FeedValues.VERB_LISTEN :
                return FeedValues.VERB_CODE_LISTEN;
            case FeedValues.VERB_REQUEST :
                return FeedValues.VERB_CODE_REQUEST;
            case FeedValues.VERB_RATE :
                return FeedValues.VERB_CODE_RATE;
            case FeedValues.VERB_USER_POST :
                return FeedValues.VERB_CODE_USER_POST;
            case FeedValues.VERB_EVENT_ANNOUNCEMENT :
                return FeedValues.VERB_CODE_EVENT_ANNOUNCEMENT;
            case FeedValues.VERB_MESSAGE_RSVPS :
                return FeedValues.VERB_CODE_MESSAGE_RSVPS;
            case FeedValues.VERB_PROMOTE :
                return FeedValues.VERB_CODE_PROMOTE;
            case FeedValues.VERB_ARTIST_POST :
                return FeedValues.VERB_CODE_ARTIST_POST;
            case FeedValues.VERB_WATCH_TRAILER :
                return FeedValues.VERB_CODE_WATCH_TRAILER;
            case FeedValues.VERB_POST_TRAILER :
                return FeedValues.VERB_CODE_POST_TRAILER;
            default :
                Print.exception(new Exception("did not recognize: " + verb));
                return FeedValues.VERB_CODE_UNRECOGNIZED;
        }
    }

    public static boolean stringHasContent(String string) {
        return string != null && string.trim().length() > 0;
    }

    public static boolean isDateStringValid(String dateString) {
        if(stringHasContent(dateString)) {
            String[] date = dateString.split("/");

            try {
                int month = Integer.valueOf(date[0]);

                return month <= 12 && month > 0
                        && date[1].length() == 2;
            } catch(Exception e) {
                Print.exception(e);
            }
        }

        return false;
    }

    public static String intentToString(Intent intent) {
        Bundle extras = intent.getExtras();
        StringBuilder stringBuilder = new StringBuilder();

        if(extras != null) {
            if(intent.getAction() != null) {
                stringBuilder.append(intent.getAction());
                stringBuilder.append("\n");
            }

            for(String key : extras.keySet()) {
                Object value = extras.get(key);

                if(value != null) {
                    stringBuilder.append(String.format("%s %s (%s)", key,
                            value.toString(), value.getClass().getName()));
                    stringBuilder.append("\n");
                }
            }
        }

        return stringBuilder.toString();
    }

    public static String buildStaticMapUrl(double lat, double lng, int imageWidth, int imageHeight) {
        return String.format(FeedValues.GOOGLE_MAPS_STATIC_URL_TEMPLATE, lat, lng, imageWidth, imageHeight);
    }

}
