package com.invsc.miaosha_1.contrller;

import com.invsc.miaosha_1.result.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @RequestMapping("/")
    String home() {
        return "Hello World!";
    }

    @RequestMapping("/hello")
    Result<String> hello() {
        return Result.success("Hello,invsc!");
    }
}
