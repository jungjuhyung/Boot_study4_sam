package com.ict.edu2.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ict.edu2.vo.GuestVO;
import com.ict.edu2.vo.MembersVO;
import com.ict.edu2.vo.UserVO;

import java.util.List;

@Mapper
public interface MemberMapper {
    UserVO selectMember(@Param("id") String id) ;
    List<GuestVO> getGuestList();
    List<MembersVO> getAdminList();

    UserVO findUserByEmail(@Param("email") String email);
    void insertUser(UserVO uvo);
    void updateUser(UserVO uvo);
}
