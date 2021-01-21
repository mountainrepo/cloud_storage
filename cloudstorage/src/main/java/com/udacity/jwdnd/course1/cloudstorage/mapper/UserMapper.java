package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.*;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.*;

@Repository
@Mapper
public interface UserMapper {
    @Select("SELECT * FROM USERTABLE WHERE id = #{id}")
    public User getUserById(int id);

    @Select("SELECT * FROM USERTABLE WHERE firstname = #{firstname} AND lastname = #{lastname}")
    public User getUserByName(String firstname, String lastname);

    @Select("SELECT * FROM USERTABLE WHERE username = #{username}")
    public User getUserByUsername(String username);

    @Select("INSERT INTO USERTABLE (firstname, lastname, salt, username, password) VALUES (#{firstname}, #{lastname}, #{salt}, #{username}, #{password})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    public Integer addUser(User newUser);
}
