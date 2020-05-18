package com.invsc.miaosha.contrller;

import com.invsc.miaosha.domain.MiaoshaOrder;
import com.invsc.miaosha.domain.MiaoshaUser;
import com.invsc.miaosha.domain.OrderInfo;
import com.invsc.miaosha.redis.RedisService;
import com.invsc.miaosha.result.CodeMsg;
import com.invsc.miaosha.service.*;
import com.invsc.miaosha.vo.GoodsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/miaosha")
public class MiaoshaController {
	@Autowired
	UserService userService;
	@Autowired
	MiaoshaUserService miaoshaUserService;
	@Autowired
	RedisService redisService;
	@Autowired
	GoodsService goodsService;
	@Autowired
	OrderService orderService;
	@Autowired
	MiaoshaService miaoshaService;

    @RequestMapping("/do_miaosha")
    public String toLogin(Model model,
                          MiaoshaUser user,
                          @RequestParam("goodsId") long goodsId) {
	    model.addAttribute("user", user);
		if (user == null) {
			System.out.println("login");
			return "login";
		}
    	// 判断库存
	    GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
		int stock = goods.getStockCount();
		if (stock <= 0) {
			model.addAttribute("errmsg", CodeMsg.MIAOSHA_OVER.getMsg());
			return "miaosha_fail";
		}

		// 判断是否秒杀到了
	    MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdAndGoodsId(user.getId(), goodsId);
		if (order != null) {
			model.addAttribute("errmsg", CodeMsg.MIAOSHA_REPEAT.getMsg());
			return "miaosha_fail";
		}
		// 减库存 下订单 写入秒杀订单 这三步要成为一个事务
		OrderInfo orderInfo = miaoshaService.miaosha(user, goods);
		model.addAttribute("orderInfo", orderInfo);
		model.addAttribute("goods", goods);
	    return "order_detail";
    }

    @RequestMapping("/")
	public String miaoshaHello() {
    	return "hello";
    }
}
