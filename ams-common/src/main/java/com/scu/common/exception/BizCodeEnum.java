package com.scu.common.exception;

/**
 * 系统错误码
 * - 5位数字
 * - 前2位表示业务场景，最后三位表示具体错误码
 * - 维护错误码的错误描述，枚举类型
 *   - 例子：
 *     - 10：通用
 *       - 001：参数格式校验
 *     - 11：校友基本信息
 *     - 12：校友登录
 */
public enum BizCodeEnum {
    UNKNOWN_EXCEPTION(10000, "系统未知异常"),
    VALID_EXCEPTION(10001, "参数校验错误"),
    LOGINACCOUNT_PASSWORD_INVALID_EXCEPTION(12001, "账号或密码错误");

    private int code;
    private String msg;

    BizCodeEnum(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
