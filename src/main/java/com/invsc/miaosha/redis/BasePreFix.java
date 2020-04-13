package com.invsc.miaosha.redis;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class BasePreFix implements KeyPrefix{
    private int expireSeconds;
    private String prefix;
    // abstract class 可以有自己实现的方法， interface不行
    public BasePreFix(String prefix) {
        // 0代表永不过期
        this(0, prefix);
    }

    public int expireSeconds() {
        return expireSeconds;
    }

    public String getPrefix() {
        String className = getClass().getSimpleName();
        return className + ":" + prefix;
    }
}
