package com.invsc.miaosha.redis;

public class UserKey extends BasePreFix {
    private UserKey(String prefix) {
        super(prefix);
    }
    public static UserKey getById = new UserKey("ID"); // 为什么此处大小写失效
    public static UserKey getByName = new UserKey("NAME");
}
