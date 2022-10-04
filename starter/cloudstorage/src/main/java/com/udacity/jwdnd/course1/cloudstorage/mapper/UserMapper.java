package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.*;


@Mapper
public interface UserMapper {
    @Select("SELECT * FROM USERS WHERE username = #{username}")
    @Results(value = {
            @Result(property="userId", column = "userid"),
            @Result(property="username", column = "username"),
            @Result(property="password", column = "password"),
            @Result(property="firstName", column = "firstname"),
            @Result(property="lastName", column = "lastname"),
            @Result(property="salt", column = "salt"),

    })
    User getUser(String username);

    @Insert("INSERT INTO USERS (username, salt, password, firstname, lastname) VALUES(#{username}, #{salt}, #{password}, #{firstName}, #{lastName})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    int insert(User user);
}
