package com.invsc.miaosha.redis;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "redis")
public class RedisConfig {
    private String host;
    private int port;
    private int timeout; // second
    private String password;
    private int poolMaxTotal;
    private int poolMaxIdle;
    private int poolMaxWait; // second
}
