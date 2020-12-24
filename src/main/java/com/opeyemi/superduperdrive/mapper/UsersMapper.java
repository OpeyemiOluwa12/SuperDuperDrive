package com.opeyemi.superduperdrive.mapper;

import com.opeyemi.superduperdrive.model.Users;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UsersMapper {

    @Insert("INSERT INTO USERS(username, salt, password, firstname, lastname) VALUES(#{username}, #{salt}, #{password}, #{firstName}, #{lastName})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    void insert(Users users);

    @Select("SELECT * FROM USERS WHERE username = #{username} ")
    Users getUser(String username);

    @Delete("DELETE FROM USERS WHERE username = #{username} ")
    int deleteUser(String username);

}
