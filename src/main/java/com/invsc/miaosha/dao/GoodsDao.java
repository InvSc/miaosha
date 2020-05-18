package com.invsc.miaosha.dao;

import com.invsc.miaosha.domain.Goods;
import com.invsc.miaosha.domain.MiaoshaGoods;
import com.invsc.miaosha.domain.User;
import com.invsc.miaosha.vo.GoodsVo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface GoodsDao {
    // 进行miaosha_goods、goods的联表查询
    @Select("SELECT g.*, mg.miaosha_price, mg.stock_count, mg.start_date, mg.end_date " +
            "FROM miaosha_goods mg left join goods g " +
            "ON mg.goods_id = g.id")
    public List<GoodsVo> listGoodsVo();

	@Select("SELECT g.*, mg.miaosha_price, mg.stock_count, mg.start_date, mg.end_date " +
			"FROM miaosha_goods mg left join goods g " +
			"ON mg.goods_id = g.id " +
			"WHERE g.id = #{goodsId}")
	GoodsVo getGoodsVoByGoodsId(@Param("goodsId") long goodsId);

	@Update("UPDATE miaosha_goods " +
			"SET stock_count = stock_count - 1 " +
			"WHERE goods_id = #{goodsId}")
	void reduceStock(MiaoshaGoods g);
}
