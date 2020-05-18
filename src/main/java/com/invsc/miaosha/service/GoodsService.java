package com.invsc.miaosha.service;

import com.invsc.miaosha.dao.GoodsDao;
import com.invsc.miaosha.domain.Goods;
import com.invsc.miaosha.domain.MiaoshaGoods;
import com.invsc.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class GoodsService {
	@Autowired
	GoodsDao goodsDao;
	public List<GoodsVo> listGoodsVo() {
		return goodsDao.listGoodsVo();
	}

	public GoodsVo getGoodsVoByGoodsId(long goodsId) {
		return goodsDao.getGoodsVoByGoodsId(goodsId);
	}

	public void reduceStock(GoodsVo goods) {
		MiaoshaGoods g = new MiaoshaGoods();
		g.setGoodsId(goods.getId());
		goodsDao.reduceStock(g);
	}
}
