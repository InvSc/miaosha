package com.invsc.miaosha.dao;

import com.invsc.miaosha.domain.MiaoshaOrder;
import com.invsc.miaosha.domain.OrderInfo;
import com.invsc.miaosha.vo.GoodsVo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface OrderDao {

	@Select("SELECT * " +
			"FROM miaosha_order " +
			"WHERE user_id=#{userId} " +
			"AND goods_id=#{goodsId}")
	MiaoshaOrder getMiaoshaOrderByUserIdAndGoodsId(@Param("userId") long userId, @Param("goodsId") long goodsId);

	@Insert("INSERT INTO order_info(user_id, goods_id, goods_name, goods_count, goods_price, order_channel, status, create_date) " +
			"VALUES(#{userId}, #{goodsId}, #{goodsName}, #{goodsCount}, #{goodsPrice}, #{orderChannel}, #{status}, #{createDate})")
	@SelectKey(keyColumn = "id", keyProperty = "id", resultType = long.class, before = false, statement = "select last_insert_id()")
	long insertOrder(OrderInfo orderInfo);

	@Insert("INSERT INTO miaosha_order(user_id, order_id, goods_id) VALUES(#{userId}, #{orderId}, #{goodsId})")
	void insertMiaoshaOrder(MiaoshaOrder miaoshaOrder);

}
