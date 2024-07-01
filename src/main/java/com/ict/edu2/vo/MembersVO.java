package com.ict.edu2.vo;

import lombok.Data;

@Data
public class MembersVO {
    private String id, email, password, created_at, last_login, is_activated ;
}
