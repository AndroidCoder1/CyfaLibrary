package com.psyphertxt.cyfalibrary.models;

/**
 * Created by Lisa on 5/14/17.
 **/

public enum ErrorCodes{
    NETWORK_ERROR("NETWORK_ERROR"),
    JSON_EXCEPTION_ERROR("JSON_EXCEPTION_ERROR"),
    RESPONSE_CODE_EXCEPTION_ERROR("RESPONSE_CODE_EXCEPTION_ERROR"),
    UNKNOWN_ERROR("UNKNOWN_ERROR"),
    API_FAILED_ERROR("API_FAILED_ERROR"),
    RESOURCE_FAILED_ERROR("RESOURCE_FAILED_ERROR"),
    WRONG_VALIDATION_CODE_ERROR("WRONG_VAIDATION_CODE_ERROR"),
    ;

    private final String text;

    /**
     * @param text
     */
    private ErrorCodes(final String text) {
        this.text = text;
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return text;
    }
}