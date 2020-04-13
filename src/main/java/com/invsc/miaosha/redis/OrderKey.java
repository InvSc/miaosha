package com.invsc.miaosha.redis;

public class OrderKey extends BasePreFix {
    // 防止外部实例化
    private OrderKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }
}
