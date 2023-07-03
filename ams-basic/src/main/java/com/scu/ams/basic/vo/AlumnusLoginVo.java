package com.scu.ams.basic.vo;

import lombok.Data;

/**
 * 校友登录vo
 */
@Data
public class AlumnusLoginVo {
    // 学号
    private String loginAccount;
    // 密码
    private String password;

    //可选
    //private boolean rememberMe;
}
