package com.psyphertxt.cyfalibrary.utils;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.telephony.PhoneNumberUtils;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.WindowManager;
import android.widget.TextView;

import com.psyphertxt.cyfalibrary.CyfaConfig;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * This class contains mostly string manipulation methods
 */
public class TextUtils {

    private final static String NON_THIN = "[^iIl1\\.,']";

    public static String nullToEmpty(@Nullable String string) {
        return (string == null) ? "" : string;
    }

    public static String trimPhoneNumber(String number) {
        return PhoneNumberUtils.extractNetworkPortion(number.trim().replaceFirst("^0+(?!$)", ""));
    }

    public static String getBundleString(Bundle bundle, String key, String def) {
        if (bundle != null) {
            String value = bundle.getString(key);
            if (value != null) {
                return value;
            }
        }
        return def;
    }

    public static String channelErrors(String className, String message) {
        return String.format("%s reference[s] might not have been initialized. call %s first", className, message);
    }

    public static String listenerErrors(String message) {
        return String.format("%s might not have been initialized. call %s first", message, message);
    }

    public static String channelErrors(String className) {
        return channelErrors(className, className);
    }

    private static int textWidth(String str) {
        return (str.length() - str.replaceAll(NON_THIN, "").length() / 2);
    }

    public static String truncateText(String text, int max) {
        return text.substring(0, Math.min(text.length(), max)) + CyfaConfig.ELLIPSIS;

    }

    public static String trim(String text) {
        if (text.length() >= CyfaConfig.STATUS_TEXT_MAX_LENGTH) {
            return truncateText(text, CyfaConfig.STATUS_TEXT_MAX_LENGTH);
        }
        return text;
    }

    public static String trim(String text, int max) {
        if (text.length() >= max) {
            return truncateText(text, max);
        }
        return text;

    }

    public static String splitURI(String path) {
        String stringPath = CyfaConfig.EMPTY_STRING;
        try {
            URI uri = new URI(path);
            String[] segments = uri.getPath().split("/");
            stringPath = segments[segments.length - 1];
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return stringPath;
    }

    public static void filterAlphabets(TextView editView) {
        editView.setFilters(new InputFilter[]{
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence cs, int start,
                                               int end, Spanned spanned, int dStart, int dEnd) {
                        if (cs.equals("")) { // for backspace
                            return cs;
                        }
                        if (cs.toString().matches("[a-zA-Z ]+")) {
                            return cs;
                        }
                        return "";
                    }
                }
        });
    }

    public static void showKeyboard(Activity activity, TextView editView) {
        editView.requestFocus();
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

    }

    public static String removeSpecialChars(String string) {
        char[] allowed = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ-_ ".toCharArray();
        char[] charArray = string.toCharArray();
        StringBuilder result = new StringBuilder();
        for (char c : charArray) {
            for (char a : allowed) {
                if (c == a) result.append(a);
            }
        }
        return result.toString();

    }

    public static String toTitleCase(String str) {

        if (str == null) {
            return null;
        }

        boolean space = true;
        StringBuilder builder = new StringBuilder(str);
        final int len = builder.length();

        for (int i = 0; i < len; ++i) {
            char c = builder.charAt(i);
            if (space) {
                if (!Character.isWhitespace(c)) {
                    // Convert to title case and switch out of whitespace mode.
                    builder.setCharAt(i, Character.toTitleCase(c));
                    space = false;
                }
            } else if (Character.isWhitespace(c)) {
                space = true;
            } else {
                builder.setCharAt(i, Character.toLowerCase(c));
            }
        }

        return builder.toString();
    }

    public static String join(String separator, String... values) {
        StringBuilder sb = new StringBuilder(128);
        int end = 0;
        for (String s : values) {
            if (s != null) {
                sb.append(s);
                end = sb.length();
                sb.append(separator);
            }
        }
        return sb.substring(0, end);
    }

    public static String joinWithSlash(String... values) {
        return join(CyfaConfig.FORWARD_SLASH, values);
    }

}
