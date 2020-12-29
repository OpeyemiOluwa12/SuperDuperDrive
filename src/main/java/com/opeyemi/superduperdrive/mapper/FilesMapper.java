package com.opeyemi.superduperdrive.mapper;

import com.opeyemi.superduperdrive.model.Files;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FilesMapper {

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata)VALUES(#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insert(Files files);

    @Select("SELECT * FROM FILES WHERE userid = #{userId}")
    List<Files> getFilesByUserId(int userId);

    @Delete("DELETE FROM FILES WHERE fileid = #{fileId}")
    int delete(int fileId);

    @Select("SELECT * FROM FILES WHERE fileid = #{fileId}")
    Files getFileByFileId(int fileId);
}
