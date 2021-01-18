package com.udacity.jwdnd.course1.cloudstorage.mapper;

import org.apache.ibatis.annotations.*;
import com.udacity.jwdnd.course1.cloudstorage.model.*;

import java.util.*;

@Mapper
public interface NoteMapper {

    @Insert("INSERT INTO Note(title, description, userid) VALUES(#{title}, #{description}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    public Integer addNote(Note newNote);

    @Insert("INSERT INTO Note(id, title, description, userid) VALUES(#{id}, #{title}, #{description}, #{userid})")
    public void addNoteWithId(Note newNote);

    @Delete("DELETE FROM NOTE WHERE id = #{id}")
    public void deleteNote(int id);

    @Select("SELECT * FROM NOTE WHERE title = #{title} and userid = #{userid}")
    public Note getNoteByTitle(String title, Integer userid);

    @Select("SELECT * FROM NOTE WHERE userid = #{userid}")
    @Results(value = {
            @Result(property="id", column="id"),
            @Result(property="title", column="title"),
            @Result(property="description", column="description"),
            @Result(property="userid", column="userid")
    })
    public List<Note> getAllNotes(Integer userid);
}
