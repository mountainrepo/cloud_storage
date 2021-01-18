package com.udacity.jwdnd.course1.cloudstorage.mapper;

import java.util.*;
import org.apache.ibatis.annotations.*;

import com.udacity.jwdnd.course1.cloudstorage.model.*;

@Mapper
public interface CredentialMapper {

    @Insert("INSERT INTO Credential (url, username, password, passwordKey, userid) VALUES (#{url}, #{username}, #{password}, #{passwordKey}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    public Integer addCredential(Credential newCred);

    @Insert("INSERT INTO Credential (id, url, username, password, passwordKey, userid) VALUES (#{id}, #{url}, #{username}, #{password}, #{passwordKey}, #{userid})")
    public Integer addCredentialWithId(Credential newCred);

    @Delete("DELETE FROM Credential WHERE id = #{id}")
    public void deleteCredential(int id);

    @Select("SELECT * FROM Credential WHERE url = #{url} and userid = #{userid}")
    public Credential getCredentialByUrl(String url, Integer userid);

    @Select("SELECT * FROM Credential WHERE userid = #{userid}")
    @Results(value = {
        @Result(property="id", column="id"),
        @Result(property="url", column="url"),
        @Result(property="username", column="username"),
        @Result(property="password", column="password"),
        @Result(property="passwordKey", column = "passwordKey"),
        @Result(property="userid", column="userid")
    })
    public List<Credential> getAllCredentials(Integer userid);
}
