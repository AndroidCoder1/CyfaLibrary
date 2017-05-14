package com.psyphertxt.cyfalibrary.models;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.psyphertxt.cyfalibrary.Config;
import com.psyphertxt.cyfalibrary.channel.StorageChannel;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Profile {

    private String displayName;
    private String statusMessage;
    private String imageName;
    private String userId;
    private String pushId;
    private String theme;
    private StorageChannel storageChannel;

    public Profile() {
        // Default constructor required for calls to DataSnapshot.getValue(Profile.class)
    }

    @Exclude
    public static Profile fromMap(String userId, Map<String, Object> map) {
        Profile profile = new Profile();

        if (!userId.isEmpty()) {
            profile.setUserId(userId);
        }

        if (map.get(Config.KEY_DISPLAY_NAME) != null) {
            profile.setDisplayName((String) map.get(Config.KEY_DISPLAY_NAME));
        }

        if (map.get(Config.KEY_STATUS_MESSAGE) != null) {
            profile.setStatusMessage((String) map.get(Config.KEY_STATUS_MESSAGE));
        }

        if (map.get(Config.KEY_IMAGE_NAME) != null) {
            profile.setImageName((String) map.get(Config.KEY_IMAGE_NAME));
        }

        if (map.get(Config.KEY_PUSH_ID) != null) {
            profile.setPushId((String) map.get(Config.KEY_PUSH_ID));
        }

        if (map.get(Config.KEY_THEME) != null) {
            profile.setTheme((String) map.get(Config.KEY_THEME));
        }

        return profile;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String profileName) {
        this.displayName = profileName;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    @Exclude
    public HashMap<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();

        if (displayName != null) {
            result.put(Config.KEY_DISPLAY_NAME, displayName);
        }

        if (statusMessage != null) {
            result.put(Config.KEY_STATUS_MESSAGE, statusMessage);
        }

        if (imageName != null) {
            result.put(Config.KEY_IMAGE_NAME, imageName);
        }

        if (pushId != null) {
            result.put(Config.KEY_PUSH_ID, pushId);
        }

        if (theme != null) {
            result.put(Config.KEY_THEME, theme);
        }

        return result;
    }
}
