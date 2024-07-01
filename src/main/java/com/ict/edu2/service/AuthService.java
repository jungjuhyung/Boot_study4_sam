package com.ict.edu2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.ict.edu2.jwt.JWTUtil;
import com.ict.edu2.jwt.JwtResponse;
import com.ict.edu2.vo.DataVO;
import com.ict.edu2.vo.MembersVO;
import com.ict.edu2.vo.UserVO;

@Service
public class AuthService {
    
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private JWTUtil jwtUtil;

    public DataVO authenticate( MembersVO mvo){
       DataVO dataVO = new DataVO();
       try {
            Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(mvo.getId(), mvo.getPassword()));

            // DB에서 사용자 정보 가져오기 
            UserVO uvo = userDetailsService.getUserDetail(mvo.getId());
            String jwt = jwtUtil.generateToken(mvo.getId());

            // 리턴할 dataVO에 uvo, jwt 를 넣자 
            dataVO.setSuccess(true);
            dataVO.setToken(jwt);
            dataVO.setUserDetails(uvo);

            return dataVO;
       } catch (Exception e) {
            dataVO.setSuccess(false);
            dataVO.setMessage(e.getMessage());
            return dataVO ;
       }
    }
}
