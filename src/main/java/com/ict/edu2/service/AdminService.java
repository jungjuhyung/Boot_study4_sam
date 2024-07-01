package com.ict.edu2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ict.edu2.mapper.MemberMapper;
import com.ict.edu2.vo.MembersVO;
import java.util.List;


@Service
public class AdminService {
     @Autowired
    private MemberMapper memberMapper;

    public List<MembersVO> getAdminList(){
        return memberMapper.getAdminList();
    }
}
