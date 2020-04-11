package com.invsc.miaosha.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

// 不应该有setter有利于更好封装
@AllArgsConstructor
@Getter
public class CodeMsg {
    private int    code;
    private String msg;

    // 通用异常
    public static CodeMsg SUCCESS      = new CodeMsg(0, "success");
    public static CodeMsg SERVER_ERROR = new CodeMsg(500100, "服务端异常");

    // 登录模块 5002XX
    // 商品模块 5003XX
    // 订单模块 5004XX
    // 秒杀模块 5005XX
}