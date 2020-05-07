package com.invsc.miaosha.contrller;

import com.invsc.miaosha.domain.MiaoshaUser;
import com.invsc.miaosha.domain.User;
import com.invsc.miaosha.redis.RedisService;
import com.invsc.miaosha.service.GoodsService;
import com.invsc.miaosha.service.MiaoshaUserService;
import com.invsc.miaosha.service.UserService;
import com.invsc.miaosha.vo.GoodsVo;
import groovyjarjarcommonscli.MissingOptionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/goods")
public class GoodsController {
    // 注意这是slf4j的log
	private static Logger log = LoggerFactory.getLogger(GoodsController.class);
	@Autowired
    UserService userService;
	@Autowired
    MiaoshaUserService miaoshaUserService;
	@Autowired
    RedisService redisService;
	@Autowired
	GoodsService goodsService;
	// paramToken 这个参数是为了读取有些移动端不采用cookie而直接url传参的情况
    @RequestMapping("/to_list")
    public String toLogin(Model model, MiaoshaUser user) {
    	model.addAttribute("user", user);

    	// 查询商品列表
	    List<GoodsVo> goodsList = goodsService.listGoodsVo();
	    model.addAttribute("goodsList", goodsList);

        return "goods_list";
    }
	@RequestMapping("/to_detail/{goodsId}")
	public String toDetail(Model model, MiaoshaUser user,
	                       @PathVariable("goodsId") long goodsId) {
		model.addAttribute("user", user);

		GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
		model.addAttribute("goods", goods);

		//
		long starAt = goods.getStartDate().getTime();
		long endAt = goods.getEndDate().getTime();
		long now = System.currentTimeMillis();

		int miaoshaStatus = 0;
		int remainSeconds = 0;

		if (now < starAt) { // 秒杀没开始
			miaoshaStatus = 0;
			remainSeconds = (int) ((starAt - now) / 1000);
		} else if (now > endAt) { // 秒杀已结束
			miaoshaStatus = 2;
			remainSeconds = -1;
		} else {
			miaoshaStatus = 1;
			remainSeconds = 0;
		}

		model.addAttribute("miaoshaStatus", miaoshaStatus);
		model.addAttribute("remainSeconds", remainSeconds);

		return "goods_detail";
	}
}
