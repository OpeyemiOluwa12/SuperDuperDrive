package com.opeyemi.superduperdrive.mapper;

import com.opeyemi.superduperdrive.model.Credentials;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CredentialsMapper {

    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userid) VALUES (#{url}, #{username}, #{key}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialsId")
    void insert(Credentials credentials);

    @Select("SELECT FROM CREDENTIALS WHERE userid = #{userId}")
    Credentials getCredentialsByUserId(int userId);
}
