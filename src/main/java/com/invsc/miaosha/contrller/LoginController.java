package com.invsc.miaosha.contrller;

import com.invsc.miaosha.redis.RedisService;
import com.invsc.miaosha.result.CodeMsg;
import com.invsc.miaosha.result.Result;
import com.invsc.miaosha.service.MiaoshaUserService;
import com.invsc.miaosha.service.UserService;
import com.invsc.miaosha.utils.ValidateUtil;
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
    public String toLogin() {
        return "login";
    }
    
    @RequestMapping("/do_login")
    @ResponseBody
    public Result<Boolean> doLogin(HttpServletResponse response, @Valid LoginVo loginVo) {
    	log.info(loginVo.toString());
    	// 参数校验
        String passInput = loginVo.getPassword();
        String mobile = loginVo.getMobile();
        if (StringUtils.isEmpty(passInput)) {
            return Result.error(CodeMsg.PASSWORD_EMPTY);
        }
        if (StringUtils.isEmpty(mobile)) {
            return Result.error(CodeMsg.MOBILE_EMPTY);
        }
        if (!ValidateUtil.isMobile(mobile)) {
            return Result.error(CodeMsg.MOBILE_ERROR);
        }
        // 登陆
        CodeMsg codeMsg = miaoshaUserService.login(loginVo);
        if (codeMsg.getCode() == 0) {
            return Result.success(true);
        } else {
            return Result.error(codeMsg);
        }
    }
}
