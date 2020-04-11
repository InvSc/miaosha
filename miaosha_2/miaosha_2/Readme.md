## 参考网址
https://docs.spring.io/spring-boot/docs/1.5.9.RELEASE/reference/htmlsingle/
## Q&A
- Q:为什么thymeleaf不能解析到我放好的html页面

    A: 可能是开启了@RestController注解
## 集成MyBatis

    https://mybatis.org/spring-boot-starter/mybatis-spring-boot-autoconfigure/index.html
    1. 添加pom依赖
    mybatis-spring-boot-starter
    2. 添加配置 mybatis.
    3. 添加MySQL客户端、druid连接池依赖
## docker配置
- 没有用户权限怎么办，下面照做就好
    ```$bash
    sudo usermod -aG docker $USERNAME
    newgrp docker
    ```
- 下载太慢怎么办（阿里云服务器）

    https://cr.console.aliyun.com/cn-hangzhou/instances/mirrors
## mysql配置
- 启动

    docker run -p 3333:3306 -itd --name mysql5-7 -e MYSQL_ROOT_PASSWORD=123456 mysql:5.7
## redis
- 建议 认真通读redis.conf

    http://download.redis.io/redis-stable/redis.conf
- 启动
```bash
    docker run -itd \
    -p 6379:6379 \
    --privileged=true \
    -v ~/myredis/conf/redis.conf:/usr/local/etc/redis/redis.conf \
    --name myredis \
    redis \
    redis-server /usr/local/etc/redis/redis.conf \
    --appendonly yes
```
- SpringBoot集成
    添加Jedis依赖，不用RedistTemplate
    添加FastJson依赖, 把Java对象转换成Json字符串（序列化）
    理由：Protocol Buffer虽然更快，但是序列化后是二进制格式不可读，而FastJson慢一倍，但明文可读
- 通用缓存Key封装
  - 接口
  - 抽象类
  - 实现类
  ## 数据库设计
  注意 SQL语句中标识符用的是反引号，文本值用的是单引号，数值不需要
  ```mysql
    CREATE TABLE `miaosha_user` (
    `id` bigint(20) NOT NULL COMMENT '用户ID,手机号码',
    `nickname` varchar(255) NOT NULL COMMENT '用户昵称',
    `password` varchar(32) DEFAULT NULL COMMENT 'MD5(MD5(pass明文+固定salt)+salt)',
    `salt` varchar(10) DEFAULT NULL,
    `head` varchar(128) DEFAULT NULL COMMENT '头像,云存储的ID',
    `register_date` datetime DEFAULT NULL COMMENT '注册时间',
    `last_login_date` datetime DEFAULT NULL COMMENT '上次登录时间',
    `login_count` int(11) DEFAULT '0' COMMENT'登录次数',
    PRIMARY KEY (`id`)
    )ENGINE=InnoDB DEFAULT CHARSET=utf8mb4
    ```
  [Err] 1055 - Expression #1 of ORDER BY clause is not in GROUP BY clause 
  and contains nonaggregated column 'information_schema.PROFILING.SEQ' 
  which is not functionally dependent on columns in GROUP BY clause; 
  this is incompatible with sql_mode=only_full_group_by
  ## 两次MD5
  两次MD5
  1. 用户端：PASS=MD5（明文+固定salt）防止明文密码在网络上传输
  2. 服务端：PASS=MD5（用户输入+随机Salt）防止数据库被盗反查彩虹表得出密码
        

