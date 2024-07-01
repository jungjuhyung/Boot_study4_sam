package com.ict.edu2.vo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

// UserDetails : Spring Security를 거친 사용자 정보들 
@Data
public class UserVO implements UserDetails{
    private String id = "";
    private String password = "";
    private String email = "";
    private String phone = "";
    private String name = "";
    private String provider = "";
    private String created_at = "";
    private String updated_at = "";
    private String last_login = "";
    private String kakao = "";
    private String naver = "";
    private String google = "";
    
    private List<GrantedAuthority> authorities = new ArrayList<>();
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {       return authorities;    }
    @Override
    public String getPassword() {          return password;    }
    @Override
    public String getUsername() {        return id;    }
    @Override
    public boolean isAccountNonExpired() {        return true;    }
    @Override
    public boolean isAccountNonLocked() {        return true;    }
    @Override
    public boolean isCredentialsNonExpired() {        return true;    }
    @Override
    public boolean isEnabled() {        return true;    }
    
}
