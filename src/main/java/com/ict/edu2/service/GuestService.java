package com.ict.edu2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ict.edu2.mapper.MemberMapper;
import com.ict.edu2.vo.GuestVO;
import java.util.List;

@Service
public class GuestService {
    @Autowired
    private MemberMapper memberMapper;

    public List<GuestVO> getGuestList(){
        return memberMapper.getGuestList();
    }
}
