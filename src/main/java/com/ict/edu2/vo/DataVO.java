package com.ict.edu2.vo;

import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataVO {
    private boolean success;
    private Object data;
    private String token;
    private String message;
    private UserDetails userDetails;

    // 사용 방법
    // DataVO dataVO = new DataVO()
    // dataVO.setSuccess(true);
    // dataVO.setData(MemberList) 또는 dataVO.setData(MemberVO) 
    // dataVO.setToken(token)

    // 사용 방법2
    // Map<String,Object> resultMap = new HashMap<>();
    // resultMap.put("MList", MemberList)
    // resultMap.put("rowTotal", rowTotal)
    
    // DataVO dataVO = new DataVO()
    // dataVO.setSuccess(true);
    // dataVO.setData(resultMap) 
    // dataVO.setToken(token)

}
