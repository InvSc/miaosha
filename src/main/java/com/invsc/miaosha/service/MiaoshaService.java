package com.invsc.miaosha.service;

import com.invsc.miaosha.dao.GoodsDao;
import com.invsc.miaosha.domain.Goods;
import com.invsc.miaosha.domain.MiaoshaUser;
import com.invsc.miaosha.domain.OrderInfo;
import com.invsc.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MiaoshaService {
	@Autowired
	GoodsService goodsService;
	@Autowired
	OrderService OrderService;
	@Transactional
	public OrderInfo miaosha(MiaoshaUser user, GoodsVo goods) {
		goodsService.reduceStock(goods);
		// order_info & miaosha_order
		return OrderService.createOrder(user, goods);
	}
}
