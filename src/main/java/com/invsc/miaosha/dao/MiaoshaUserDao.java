package com.invsc.miaosha.dao;

import com.invsc.miaosha.domain.MiaoshaUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface MiaoshaUserDao {
    @Select(" SELECT * FROM miaosha_user WHERE id = #{id}")
    public MiaoshaUser getById(@Param("id") long id);
}
