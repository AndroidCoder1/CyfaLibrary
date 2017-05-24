package com.psyphertxt.cyfalibrary;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import com.parse.Parse;

/**
 * This class contains application configuration and setup methods and constants
 */
public class CyfaConfig {

    public static final String HAS_CAMERA_PERMISSION = "has_camera_permission";
    //theme color names
    public static final String THEME_NAME_DEFAULT = "Base";
    //application constants
    public static final String KEY_USER_ID = "userId";
    public static final String KEY_PUSH_ID = "pushId";
    public static final String KEY_QUESTION = "question";
    public static final String KEY_ANSWER = "answer";
    public static final String KEY_CONTACTS = "contacts";
    public static final String KEY_USER = "user";
    public static final String KEY_PHONE_NUMBER = "phoneNumber";
    public static final String KEY_CALLING_CODE = "callingCode";
    public static final String KEY_THEME = "theme";
    public static final String KEY_NAME = "cyfa_name";
    public static final String KEY_IMAGE_NAME = "imageName";
    public static final String KEY_PROFILE_SETUP = "profileSetup";
    public static final String KEY_PROFILE_IMAGE = "profileImage";
    public static final String KEY_PASSCODE = "passcode";
    public static final String KEY_DISPLAY_NAME = "displayName";
    public static final String KEY_TYPE = "type";
    public static final String KEY_DEFAULT = "default";
    public static final String KEY_SESSION_TOKEN = "sessionToken";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_CODE = "code";
    public static final String KEY_IS_EXISTING_USER = "isExistingUser";
    public static final String TAG = CyfaConfig.class.getSimpleName();
    public static final String KEY_STATUS_MESSAGE = "statusMessage";
    public static final String KEY_LIVE_TYPING = "live_typing";
    public static final String KEY_HIDE_REGULAR = "hide_regular";
    public static final String KEY_SHOW_TIMER = "show_timer";
    public static final String KEY_TIMER_INDEX = "timer_index";
    public static final String KEY_TIMER_VALUE = "timer_value";
    public static final String ID = "id";
    public static final String STRING = "string";
    public static final String TOKEN = "token";
    public static final String DATA = "data";
    public static final String HASH = "hash";
    //convenient constants
    public static final String TIMER_DEFAULT = "20";
    public static final String FORWARD_SLASH = "/";
    public static final int CODE_LENGTH = 4;
    public static final int STATUS_TEXT_MAX_LENGTH = 70;
    public static final int NUMBER_ZERO = 0;
    public static final String EMPTY_STRING = "";
    public static final String ELLIPSIS = "...";
    //parse cloud code function names
    public static final String DEFINE_USER_VALIDATION = "userValidation";
    public static final String DEFINE_GET_CONTACTS = "getContacts";
    public static final String DEFINE_GENERATE_HASH = "generateHash";
    public static final String DEFINE_GENERATE_TOKEN = "generateToken";
    public static final String KEY_SIGN_UP = "key_sign_up";
    private static String parse_app_id = "";
    private static String parse_client_id = "";
    private static String parse_url = "";


    public static void initilaizeParseConfigs(Context context){
        try {
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = ai.metaData;
            parse_app_id = bundle.getString("parse_app_id");
            parse_client_id = bundle.getString("parse_client_id");
            parse_url = bundle.getString("parse_url");

            Log.d(TAG, "Parse App id "+parse_app_id);
            Log.d(TAG, "Parse Url "+parse_url);
            Log.d(TAG, "Parse Client key "+parse_client_id);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "Failed to load meta-data, NameNotFound: " + e.getMessage());
        } catch (NullPointerException e) {
            Log.e(TAG, "Failed to load meta-data, NullPointer: " + e.getMessage());
        }

        //initialize parse server
        Parse.initialize(new Parse.Configuration.Builder(context)
                .applicationId(parse_app_id)
                .clientKey(parse_client_id)
                .server(parse_url)
                .build());
    }

}
