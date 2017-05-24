package com.psyphertxt.cyfalibrary.models;


import com.psyphertxt.cyfalibrary.CyfaConfig;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PhoneContact implements Serializable, FormatType.Namable {
    private final String id;
    private final String displayName;
    private final String thumbnailUri;
    private final List<String> phoneNumbers;
    private final List<String> emailAddresses;
    private boolean isSelected = false;

    private PhoneContact(Builder builder) {
        this.id = builder.id;
        this.displayName = builder.displayName;
        this.thumbnailUri = builder.thumbnailUri;
        this.phoneNumbers = builder.phoneNumbers;
        this.emailAddresses = builder.emailAddresses;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void isSelected(boolean selected) {
        isSelected = selected;
    }

    public String getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String getString() {
        return this.getDisplayName();
    }

    public String getThumbnailUri() {
        return thumbnailUri;
    }

    public List<String> getPhoneNumbers() {
        return phoneNumbers;
    }

    public List<String> getEmailAddresses() {
        return emailAddresses;
    }

    public boolean hasPhoneNumber() {
        return phoneNumbers.size() > 0;
    }

    public String getPrimaryPhoneNumber() {

        return (phoneNumbers.size() > 0) ? phoneNumbers.get(0) : CyfaConfig.EMPTY_STRING;

    }

    public boolean matches(String key) {
        if (hasPhoneNumber()) {
            for (String phoneNumber : phoneNumbers) {
                if (phoneNumber.contains(key)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static class Builder {
        private String id;
        private String displayName;
        private String thumbnailUri;
        private List<String> phoneNumbers;
        private List<String> emailAddresses;

        public Builder() {
            this.phoneNumbers = new ArrayList<>();
            this.emailAddresses = new ArrayList<>();
        }

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setDisplayName(String displayName) {
            this.displayName = displayName;
            return this;
        }

        public Builder setThumbnailUri(String thumbnailUri) {
            this.thumbnailUri = thumbnailUri;
            return this;
        }

        public Builder addPhoneNumber(String phoneNumber) {
            this.phoneNumbers.add(phoneNumber);
            return this;
        }

        public Builder addEmailAddress(String emailAddress) {
            this.emailAddresses.add(emailAddress);
            return this;
        }

        public PhoneContact build() {
            return new PhoneContact(this);
        }
    }
}
