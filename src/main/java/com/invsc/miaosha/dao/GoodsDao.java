package com.invsc.miaosha.dao;

import com.invsc.miaosha.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserDao {
    @Select("SELECT * FROM user WHERE id = #{id}")
    User getById(@Param("id") int id);

    @Insert("INSERT INTO user(id, name) VALUES(#{id}, #{name})")
    void insert(User user);
}
