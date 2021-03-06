package com.psyphertxt.cyfalibrary.models;

/**
 * This model handles all sign up values
 * and gradually builds up the sign up process using
 * EventBus
 */
public class SignUp {

    private String number;
    private int code;
    private String sessionToken;
    private Boolean isExisting;
    private String username;
    private String callingCode;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPhoneNumber() {
        return callingCode + number;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public Boolean isExisting() {
        return isExisting;
    }

    public void isExisting(Boolean isExisting) {
        this.isExisting = isExisting;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCallingCode() {
        return callingCode;
    }

    public void setCallingCode(String callingCode) {
        this.callingCode = callingCode;
    }


    @Override
    public String toString() {
        return "SignUp{" +
                "number='" + number + '\'' +
                ", code=" + code +
                ", sessionToken='" + sessionToken + '\'' +
                ", isExisting=" + isExisting +
                ", username='" + username + '\'' +
                ", callingCode='" + callingCode + '\'' +
                '}';
    }
}
