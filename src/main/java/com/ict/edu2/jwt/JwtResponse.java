package com.ict.edu2.jwt;

public class JwtResponse {
    private final String token;

    public JwtResponse(String token){
        this.token = token;
    }
    public String getToken(){
        return token;
    }
}
