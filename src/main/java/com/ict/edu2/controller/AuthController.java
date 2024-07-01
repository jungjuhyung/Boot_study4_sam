package com.ict.edu2.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ict.edu2.jwt.JWTUtil;
import com.ict.edu2.service.AdminService;
import com.ict.edu2.service.AuthService;
import com.ict.edu2.service.GuestService;
import com.ict.edu2.service.MyUserDetailsService;
import com.ict.edu2.vo.DataVO;
import com.ict.edu2.vo.GuestVO;
import com.ict.edu2.vo.MembersVO;
import com.ict.edu2.vo.UserVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;


@RestController
@RequestMapping("/api")
public class AuthController {
    
    @Autowired
    private AuthService authService;

    @Autowired
    private GuestService guestService;

    @Autowired
    private AdminService adminService;
    
    @Autowired
    private JWTUtil jwtUtil;
    
    @Autowired
    private MyUserDetailsService userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<DataVO> postMethodName(@RequestBody MembersVO mvo) {
         DataVO dataVO = authService.authenticate(mvo);
         if(dataVO != null){
            return ResponseEntity.ok(dataVO);
         }else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
         }
       
    }
    
    @GetMapping("/guest")
    public List<GuestVO> getGuestList() {
        return guestService.getGuestList();
    }

    @GetMapping("/admin")
    public List<MembersVO> getAdminList() {
        return adminService.getAdminList();
    }
    
    @GetMapping("/userInfo")
    public ResponseEntity<UserVO> getUserInfo(@RequestParam("token") String token) throws Exception {
        // 토큰 가지고 id를 추출
        String id = jwtUtil.extractUsername(token);
        // 추출한 id로 사용자 정보 추출
        UserVO user = userDetailsService.getUserDetail(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }
}
