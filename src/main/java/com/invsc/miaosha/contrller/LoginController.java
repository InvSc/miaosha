package com.invsc.miaosha.contrller;

import com.invsc.miaosha.redis.RedisService;
import com.invsc.miaosha.result.CodeMsg;
import com.invsc.miaosha.result.Result;
import com.invsc.miaosha.service.MiaoshaUserService;
import com.invsc.miaosha.service.UserService;
import com.invsc.miaosha.utils.ValidatorUtil;
import com.invsc.miaosha.vo.LoginVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequestMapping("/login")
public class LoginController {
    // 注意这是slf4j的log
	private static Logger log = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
    UserService userService;

	@Autowired
    MiaoshaUserService miaoshaUserService;
	
	@Autowired
    RedisService redisService;
	
    @RequestMapping("/to_login")
    public String toLogin()
    {
        return "login";
    }
    
    @RequestMapping("/do_login")
    @ResponseBody
    public Result<Boolean> doLogin(HttpServletResponse response, @Valid LoginVo loginVo) {
    	log.info(loginVo.toString());
        // 登陆
        miaoshaUserService.login(response, loginVo);
        return Result.success(true);
    }
}
