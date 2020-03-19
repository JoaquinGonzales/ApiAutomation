package Entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class Token {
    private String token;
    private String userEmail;
    private String expirationTime;

    public String getExpirationTime() {
        return expirationTime;
    }

    public String getToken() {
        return token;
    }

    public String getUserEmail() {
        return userEmail;
    }


}
