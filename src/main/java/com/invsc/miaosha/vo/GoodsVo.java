package com.invsc.miaosha.vo;

import com.invsc.miaosha.domain.Goods;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class GoodsVo extends Goods {
	private double miaoshaPrice;
	private Integer stockCount;
	private Date startDate;
	private Date endDate;
}
