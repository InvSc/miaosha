package com.invsc.miaosha.contrller;

import com.invsc.miaosha.domain.User;
import com.invsc.miaosha.redis.RedisService;
import com.invsc.miaosha.redis.UserKey;
import com.invsc.miaosha.result.CodeMsg;
import com.invsc.miaosha.result.Result;
import com.invsc.miaosha.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    UserService userService;
    @Autowired
    RedisService redisService;

    @RequestMapping("/")
    @ResponseBody
    public String home() {
        return "Hello World!";
    }

    // 1 restApi JSON输出
    @RequestMapping("/hello")
    @ResponseBody
    public Result<String> hello() {
        return Result.success("Hello,Invsc!");
    }

    @RequestMapping("/helloError, /error")
    @ResponseBody
    public Result<String> helloError() {
        return Result.error(CodeMsg.SERVER_ERROR);
    }

    // 2 页面
    @RequestMapping("/thymeleaf")
    public String thymeleaf(Model model) {
        model.addAttribute("name", "invsc");
        return "hello";
    }

    @RequestMapping("/db/get")
    @ResponseBody
    public Result<User> dbGet() {
        User user = userService.getById(1);
        return Result.success(user);
    }

    @RequestMapping("/db/tx")
    @ResponseBody
    public Result<Boolean> dbTx() {
        userService.tx();
        return Result.success(true);
    }

    @RequestMapping("/redis/get")
    @ResponseBody
    public Result<Long> redisGet() {
        Long v1 = redisService.get(UserKey.getById, "key1", Long.class);
        return Result.success(v1);
    }

    @RequestMapping("/redis/set")
    @ResponseBody
    public Result<String> redisSet() {
        boolean ret = redisService.set(UserKey.getById, "key2", "hello, imooc"); // UserKey：idkey2
        String str = redisService.get(UserKey.getById, "key2", String.class);
        return Result.success(str);
    }
}
