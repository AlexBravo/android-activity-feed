package com.bandsintown.activityfeed.util;

import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Map;

/**
 * Created by rjaylward on 5/2/16 for Bandsintown
 */
public class Logger {

    public static boolean mIsDebugMode = true;
    public static boolean mIsLoggingEnabled = true;

    private static final String SHORT_TAG = "(BIT Feed)";

    private static CrashReporter mCrashReporter = null;

    public static void init(boolean debug, @Nullable CrashReporter crashReporter) {
        mIsDebugMode = debug;
        mCrashReporter = crashReporter;
    }

    public static void setLoggingEnabled(boolean enabled) {
        mIsLoggingEnabled = enabled;
    }

    /**
     * Used for logging whatever in log cat.
     *
     * @param message - log message
     */
    public static void log(Object message) {
        if(mIsDebugMode && mIsLoggingEnabled) {
            if(message == null || message.toString() == null)
                Log.d(SHORT_TAG, "The Message is null");
            else
                Log.d(SHORT_TAG, message.toString());
        }
    }

    /**
     * Used for logging whatever in log cat.
     *
     * @param tag - log tag
     * @param messages - log messages, separated by commas
     */
    public static void log(String tag, Object... messages) {
        if(mIsDebugMode && mIsLoggingEnabled) {
            String message;
            if(messages == null || messages.length == 0)
                message = "The Message is null";
            else {
                message = "";
                for(int i = 0; i < messages.length; i++) {
                    if(messages[i] != null)
                        message += messages[i].toString();
                    else
                        message += null;

                    if(i != messages.length - 1)
                        message += ", ";
                }
            }

            //this is so log cat can be filtered by bit tag
            try {
                Log.d(SHORT_TAG + " " + tag, message);
            }
            catch(Exception e) {
                //you can't log in unit tests so this stops it from throwing an exception
            }
        }
    }

    public static void exception(Exception e) {
        exception(e, true);
    }

    public static void exception(Exception e, boolean report) {
        if(mIsDebugMode)
            e.printStackTrace();
        else if(report && mCrashReporter != null)
            mCrashReporter.logException(e);
    }

    public static void logScreen(String string) {
        if(string != null) {
            if(mIsDebugMode)
                log("Class", string);
            else if(mCrashReporter != null)
                mCrashReporter.log(string);
        }
    }

    public static void analyticsScreenView(String screenName, Map<String, String> params) {
        if(mIsDebugMode)
            Log.d("Analytics screen view", "Page: " + screenName + "Params: " + params.toString());
    }

    public interface CrashReporter {
        void log(String message);
        void logException(Exception e);
    }

}
