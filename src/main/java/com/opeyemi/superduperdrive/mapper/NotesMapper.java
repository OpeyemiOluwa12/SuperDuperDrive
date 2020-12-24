package com.opeyemi.superduperdrive.mapper;

import com.opeyemi.superduperdrive.model.Notes;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface NotesMapper {

    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) VALUES(#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    void insert(Notes notes);

    @Select("SELECT * FROM NOTES WHERE userid= #{userId}")
    Notes getNoteByUserId(int userId);
}
