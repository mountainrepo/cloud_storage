package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.*;

import org.apache.ibatis.annotations.*;

import java.io.*;
import java.util.*;

@Mapper
public interface FileMapper {

    @Insert("INSERT INTO File (name, contenttype, filesize, userid, data) VALUES (#{newFile.name}, #{newFile.contenttype}, #{newFile.size}, #{newFile.userid}, #{data})")
    @Options(useGeneratedKeys = true, keyProperty = "newFile.id")
    public Integer addFile(FileData newFile, FileInputStream data);

    @Delete("DELETE FROM File WHERE id = #{id}")
    public void deleteFile(int id);

    @Select("SELECT id, name FROM File WHERE userid = #{userid}")
    @Results( value = {
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name")
    })
    public List<FileData> getAllFiles(Integer userid);

    @Select("SELECT * FROM File WHERE id = #{id}")
    @Results( value = {
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "contenttype", column = "contenttype"),
            @Result(property = "size", column = "filesize"),
            @Result(property = "userid", column = "userid")
    })
    public FileData getFileData(int id);

    @Select("SELECT id FROM File WHERE name = #{name} and userid = #{userid}")
    public Integer getFileByName(String name, Integer userid);

    @Select("SELECT data FROM File WHERE id = #{id}")
    public InputStream getDataAsStream(int id);
}
