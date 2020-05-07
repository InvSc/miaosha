package com.invsc.miaosha.dao;

import com.invsc.miaosha.domain.User;
import com.invsc.miaosha.vo.GoodsVo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface GoodsDao {
    // 进行miaosha_goods、goods的联表查询
    @Select("SELECT g.*, mg.stock_count, mg.start_date, mg.end_date " +
            "FROM miaosha_goods mg left join goods g " +
            "ON mg.goods_id = g.id")
    public List<GoodsVo> listGoodsVo();

	@Select("SELECT g.*, mg.stock_count, mg.start_date, mg.end_date " +
			"FROM miaosha_goods mg left join goods g " +
			"ON mg.goods_id = g.id" +
			"WHERE g.id = #{goodsId}")
	GoodsVo getGoodsVoByGoodsId(@Param("goodsId") long goodsId);
}
