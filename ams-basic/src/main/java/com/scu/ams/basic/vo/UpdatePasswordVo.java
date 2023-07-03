package com.scu.ams.basic.vo;

import lombok.Data;

@Data
public class UpdatePasswordVo {
    // 原密码
    private String password;
    // 新密码
    private String newPassword;
    // 确认密码
    //private String confirmPassword;
}
