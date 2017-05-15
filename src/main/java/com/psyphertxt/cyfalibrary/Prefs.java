package com.psyphertxt.cyfalibrary;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.psyphertxt.cyfalibrary.models.SignUp;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * This is the Applications Settings Class
 * all application settings are suppose
 * to be retrieved and saved using this class
 */
public class Prefs {

    private SharedPreferences sharedPreferences;
    private Context context;
    private Gson mGson;

    //initialize shared preferences by getting the default
    //and passing the context on each initialization
    public Prefs(Context context) {
        this.context = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.mGson = new Gson();
    }

    public String getTheme() {
        return sharedPreferences.getString(Config.KEY_THEME, Config.THEME_NAME_DEFAULT);
    }

    public void setTheme(String themeName) {
        sharedPreferences
                .edit()
                .putString(Config.KEY_THEME, themeName)
                .apply();

    }

    public String getPasscode() {
        return sharedPreferences.getString(Config.KEY_PASSCODE, Config.EMPTY_STRING);
    }

    public void setPasscode(String passcode) {
        sharedPreferences
                .edit()
                .putString(Config.KEY_PASSCODE, passcode)
                .apply();

    }

    public String getSecurityQuestion() {
        return sharedPreferences.getString(Config.KEY_QUESTION, Config.EMPTY_STRING);
    }

    public void setSecurityQuestion(String question) {
        sharedPreferences
                .edit()
                .putString(Config.KEY_QUESTION, question)
                .apply();

    }

    public String getSecurityAnswer() {
        return sharedPreferences.getString(Config.KEY_ANSWER, Config.EMPTY_STRING);
    }

    public void setSecurityAnswer(String answer) {
        sharedPreferences
                .edit()
                .putString(Config.KEY_ANSWER, answer)
                .apply();

    }

    public void clearPasscode() {
        if (sharedPreferences.contains(Config.KEY_PASSCODE)) {
            sharedPreferences
                    .edit()
                    .remove(Config.KEY_PASSCODE)
                    .apply();
        }

    }

    public void setMessageCount(String userId, int message) {
        sharedPreferences
                .edit()
                .putInt(userId, message)
                .apply();

    }

    public int getMessageCount(String userId) {
        return sharedPreferences.getInt(userId, 0);
    }

    //phone number format is callingCode+user entered number
    //we store it after twilio validates it
    //to make sure it's a correct number
    public String getPhoneNumber() {
        return sharedPreferences.getString(Config.KEY_PHONE_NUMBER, Config.EMPTY_STRING);
    }

    public void setPhoneNumber(String phoneNumber) {
        sharedPreferences
                .edit()
                .putString(Config.KEY_PHONE_NUMBER, phoneNumber)
                .apply();

    }

    public String getCallingCode() {
        return sharedPreferences.getString(Config.KEY_CALLING_CODE, Config.EMPTY_STRING);
    }

    public void setCallingCode(String callingCode) {
        sharedPreferences
                .edit()
                .putString(Config.KEY_CALLING_CODE, callingCode)
                .apply();

    }

    public String getDisplayName() {
        return sharedPreferences.getString(Config.KEY_DISPLAY_NAME, Config.EMPTY_STRING);
    }

    public void setDisplayName(String profileName) {
        sharedPreferences
                .edit()
                .putString(Config.KEY_DISPLAY_NAME, profileName)
                .apply();

    }

    public void setStatusMessage(String statusMessage) {
        sharedPreferences
                .edit()
                .putString(Config.KEY_STATUS_MESSAGE, statusMessage)
                .apply();

    }

    public String getProfileImageName() {
        return sharedPreferences.getString(Config.KEY_IMAGE_NAME, Config.EMPTY_STRING);
    }

    public void setProfileImageName(String profileImageName) {
        sharedPreferences
                .edit()
                .putString(Config.KEY_IMAGE_NAME, profileImageName)
                .apply();

    }

    public Boolean isProfileSetup() {
        return sharedPreferences.getBoolean(Config.KEY_PROFILE_SETUP, false);
    }

    public void isProfileSetup(Boolean profileSetup) {
        sharedPreferences
                .edit()
                .putBoolean(Config.KEY_PROFILE_SETUP, profileSetup)
                .apply();

    }

    public Boolean isProfileImageOnServer() {
        return sharedPreferences.getBoolean(Config.KEY_PROFILE_IMAGE, false);
    }

    public void isProfileImageOnServer(Boolean profileImage) {
        sharedPreferences
                .edit()
                .putBoolean(Config.KEY_PROFILE_IMAGE, profileImage)
                .apply();

    }

    //Settings for Message Action Buttons //

    public Boolean isLiveTyping() {
        return sharedPreferences.getBoolean(Config.KEY_LIVE_TYPING, false);
    }

    public void isLiveTyping(Boolean typing) {
        sharedPreferences
                .edit()
                .putBoolean(Config.KEY_LIVE_TYPING, typing)
                .apply();

    }

    public Boolean isHideRegular() {
        return sharedPreferences.getBoolean(Config.KEY_HIDE_REGULAR, false);
    }

    public void isHideRegular(Boolean regular) {
        sharedPreferences
                .edit()
                .putBoolean(Config.KEY_HIDE_REGULAR, regular)
                .apply();

    }

    //TODO remove this for now
    public Boolean isTimerShowing() {
        return sharedPreferences.getBoolean(Config.KEY_SHOW_TIMER, false);
    }

    public void isTimerShowing(Boolean typing) {
        sharedPreferences
                .edit()
                .putBoolean(Config.KEY_SHOW_TIMER, typing)
                .apply();

    }

    public int getTimerIndex() {
        return sharedPreferences.getInt(Config.KEY_TIMER_INDEX, 0);
    }

    public void setTimerIndex(int index) {
        sharedPreferences
                .edit()
                .putInt(Config.KEY_TIMER_INDEX, index)
                .apply();

    }

    public String getTimerValue() {
        return sharedPreferences.getString(Config.KEY_TIMER_VALUE, Config.TIMER_DEFAULT);
    }

    public void setTimerValue(String value) {
        sharedPreferences
                .edit()
                .putString(Config.KEY_TIMER_VALUE, value)
                .apply();

    }

    public void setHasCameraPermission(boolean b) {
        sharedPreferences
                .edit()
                .putBoolean(Config.HAS_CAMERA_PERMISSION, b)
                .apply();
    }

    public Boolean getCameraPermission() {
        return sharedPreferences.getBoolean(Config.HAS_CAMERA_PERMISSION, false);
    }

    public synchronized void storeObject(String key, Object object)
    {
        final Type type = new TypeToken<Object>() {}.getType();
        sharedPreferences.edit().putString(key, mGson.toJson(object, type)).commit();
    }

    public Object getStoredSignUpObject(String key)
    {
        final Type type = new TypeToken<SignUp>() {}.getType();

        if(mGson.fromJson(sharedPreferences.getString(key, null), type) == null){
            return null;
        }else {
            return mGson.fromJson(sharedPreferences.getString(key, null), type);
        }
    }
    //Settings for Message Action Buttons //

}
