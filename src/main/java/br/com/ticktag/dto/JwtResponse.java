package br.com.ticktag.dto;

public class JwtResponse {
    private String token;
    private String type = "Bearer";

    public JwtResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public String getType() {
        return type;
    }
}

