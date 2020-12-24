package com.opeyemi.superduperdrive.mapper;

import com.opeyemi.superduperdrive.model.Files;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface FilesMapper {

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata)VALUES(#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    void insert(Files files);

    @Select("SELECT * FROM FILES WHERE userid = #{userId}")
    Files getFilesByUserId(int userId);
}
