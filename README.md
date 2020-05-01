## 参考网址
https://docs.spring.io/spring-boot/docs/1.5.9.RELEASE/reference/htmlsingle/
## Q&A
- Q:为什么thymeleaf不能解析到我放好的html页面

    A: 可能是开启了@RestController注解 
- Q:在登录模块密码错误不能在网页中提示，而是报错java.lang.NoSuchFieldError: PASSWORD_ERROR

    A:删除target目录下编译好的class文件尝试重新编译，如果没有解决可能是导入的依赖包出现了问题
- Q:为什么当我使用@AutoWired注解的时候，IDEA会提醒我Field injection is not recommended
  
    A:field注入缺点多多，应该避免，详情参见https://stackoverflow.com/questions/39890849/what-exactly-is-field-injection-and-how-to-avoid-it
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
# 第二章

## 两次MD5
两次MD5
1. 用户端：PASS=MD5（明文+固定salt）防止明文密码在网络上传输
2. 服务端：PASS=MD5（用户输入+随机Salt）防止数据库被盗反查彩虹表得出密码
  
## 自定义参数校验器+全局异常处理器
### 参数校验器
1. 在pom.xml添加依赖
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
```
2. 在需要被校验的参数被调用的形参前面加@Valid标签
3. 在LoginVo类中具体参数的位置添加对应的注解
4. 我们自定义了@isMobile参数校验器，具体通过调用utils.ValidatorUtil.isMobile来实现
### 异常处理器
1. 由com.invsc.miaosha.exception.GlobalExceptionHandler类负责处理
## 分布式session
1. 缘由：用户前后两次请求落在不同的服务器上会导致session信息丢失
2. 容器会提供原生的session同步，但是实现复杂并且性能很差
3. 解决：把session信息存放到第三方的缓存中，如redis
4. 核心实现如下，在controller中传入token，在通过getByToken方法从缓存找到对应用户
```java
@RequestMapping("/to_list")
public String toLogin(Model model,
                      @CookieValue(value = MiaoshaUserService.COOKIE_NAME_TOKEN, required = false) String cookieToken,
                      @RequestParam(value = MiaoshaUserService.COOKIE_NAME_TOKEN, required = false) String paramToken) {
  if (StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)) {
    return "login";
  }
  String token = StringUtils.isEmpty(paramToken) ? cookieToken : paramToken;
  MiaoshaUser user = miaoshaUserService.getByToken(token);
  model.addAttribute("user", user);
  return "goods_list";
}
```

# 第三章 实现秒杀功能

1. 数据库设计

   商品表

   秒杀商品表

   订单表

   秒杀订单表

2. 页面设计
   1. 商品列表页
   2. 商品详情页
   3. 订单详情页

