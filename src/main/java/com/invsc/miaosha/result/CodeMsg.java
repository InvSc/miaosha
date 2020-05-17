package com.invsc.miaosha.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

// 不应该有setter有利于更好封装
@Getter
@AllArgsConstructor
public class CodeMsg {

    private int    code;
    private String msg;

    // 通用异常
    public static final CodeMsg SUCCESS = new CodeMsg(0, "success");
    public static final CodeMsg SERVER_ERROR = new CodeMsg(500100, "服务端异常");
    public static final CodeMsg BIND_ERROR = new CodeMsg(500101, "参数校验异常:%s");

    // 登录模块 5002XX
    public static final CodeMsg SESSION_ERROR = new CodeMsg(500210, "Session不存在或已失效");
    public static final CodeMsg PASSWORD_EMPTY = new CodeMsg(500211, "密码不能为空");
    public static final CodeMsg MOBILE_EMPTY = new CodeMsg(500212, "手机号不能为空");
    public static final CodeMsg MOBILE_ERROR     = new CodeMsg(500213, "手机号格式错误");
    public static final CodeMsg MOBILE_NOT_EXIST = new CodeMsg(500213, "手机号不存在");
    public static final CodeMsg PASSWORD_ERROR   = new CodeMsg(500214, "密码错误");

    // 商品模块 5003XX
    // 订单模块 5004XX
    // 秒杀模块 5005XX
    public static final CodeMsg MIAOSHA_OVER   = new CodeMsg(500500, "秒杀完毕");
    public static final CodeMsg MIAOSHA_REPEAT   = new CodeMsg(500501, "秒杀重复");


    public CodeMsg fillArgs(Object... args) {
        int code = this.code;
        String message = String.format(this.msg, args);
        return new CodeMsg(code, message);
    }
}