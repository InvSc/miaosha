package com.invsc.miaosha.service;

import com.invsc.miaosha.dao.GoodsDao;
import com.invsc.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
	@Autowired
	OrderDao orderDao;
}
