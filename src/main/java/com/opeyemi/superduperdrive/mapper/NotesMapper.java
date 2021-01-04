package com.opeyemi.superduperdrive.mapper;

import com.opeyemi.superduperdrive.model.Notes;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NotesMapper {

    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) VALUES(#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    void insert(Notes notes);

    @Select("SELECT * FROM NOTES WHERE userid= #{userId}")
    List<Notes> getAllNotesByUserId(int userId);

    @Delete("DELETE FROM NOTES WHERE noteid = #{noteId}")
    int delete(int noteId);

    @Update("UPDATE NOTES SET notetitle = #{noteTitle}, notedescription = #{noteDescription} WHERE noteid = #{noteId} AND userid = #{userId}")
    void update(Notes notes);
}
