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
```mysql
CREATE TABLE `goods`(
`id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '商品ID',
`goods_name` varchar(16) DEFAULT NULL COMMENT '商品名称',
`goods_title` varchar(64) DEFAULT NULL COMMENT '商品标题',
`goods_img` varchar(64) DEFAULT NULL COMMENT'商品的图片',
`goods_detail` longtext COMMENT '商品的详情介绍',
`goods_price` decimal(10,2) DEFAULT '0.00' COMMENT '商品单价',
`goods_stock` int(11) DEFAULT'0' COMMENT '商品库存，-1表示没有限制',
PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;
```
```mysql
INSERT INTO `goods`
VALUES
(1, 
'iphoneX',
'Apple iPhoneX（A1865）64GB 银色 移动联通电信4G手机',
'img/iphonex.png',
'Apple iPhoneX（A1865）64GB 银色 移动联通电信4G手机',
8765.00,
10000),
(2,
'华为Mate9',
'华为Mate9 4G + 32G版 月光银 移动联通电信4G手机 双卡双待',
'img/mate9.png',
'华为Mate9 4G + 32G版 月光银 移动联通电信4G手机 双卡双待',
3212,
-1);
```

   秒杀商品表
```mysql
CREATE TABLE `miaosha_goods`(
`id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '秒杀的商品表',
`goods_id` bigint(20) DEFAULT NULL COMMENT '商品ID',
`miaosha_price` decimal(10,2) DEFAULT '0.00' COMMENT '秒杀价',
`stock_count` int(11) DEFAULT'0' COMMENT '商品库存',
`start_date` datetime default null comment '秒杀开始时间',
`end_date` datetime default null comment '秒杀结束时间',
PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;
```
```mysql
INSERT into `miaosha_goods` values (1, 1, 0.01, 4, '2020-05-01 00:00:00', '2020-05-31 00:00:00'),(2, 2, 0.01, 9, '2020-05-01 00:00:00', '2020-05-31 00:00:00');
```

   订单表
```mysql
CREATE TABLE `order_info`(
`id` bigint(20) NOT NULL AUTO_INCREMENT,
`user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
`goods_id` bigint(20) DEFAULT NULL COMMENT '商品ID',
`delivery_addr_id` bigint(20) DEFAULT NULL COMMENT '收货地址ID',
`goods_name` varchar(16) DEFAULT NULL COMMENT '冗余过来的商品名称',
`goods_count` int(11) DEFAULT 0 COMMENT '商品数量',
`goods_price` decimal(10,2) DEFAULT '0.00' COMMENT '商品单价',
`order_channel` tinyint(4) DEFAULT 0 COMMENT '1pc, 2android, 3ios',
`status` tinyint(4) DEFAULT 0 COMMENT '订单状态,0新建未支付,1已支付,2已发货,3已收货,4已退款,5已完成',
`create_date` datetime DEFAULT NULL COMMENT '订单的创建时间',
`pay_date` datetime DEFAULT NULL COMMENT '支付时间',
PRIMARY KEY(`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4;
```

   秒杀订单表
```mysql
CREATE TABLE `miaosha_order`(
`id` bigint(20) NOT NULL AUTO_INCREMENT,
`user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
`order_id` bigint(20) DEFAULT NULL COMMENT '订单ID',
`goods_id` bigint(20) DEFAULT NULL COMMENT '商品ID',
PRIMARY KEY(`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;
```

注意： 实际生产过程中GoodsID字段很少会直接采用auto_increment,因为这样的话很容易被别人直接遍历，我们常用snowFlake算法
2. 页面设计
   1. 商品列表页
   2. 商品详情页
   3. 订单详情页

