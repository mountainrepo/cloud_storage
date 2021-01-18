package com.udacity.jwdnd.course1.cloudstorage.model;

public class Credential {
    private Integer id;
    private String url;
    private String username;
    private String password;
    private Integer userid;
    private String passwordKey;

    public Credential(Integer id, String url, String username, String password, Integer userid, String passwordKey) {
        this.id = id;
        this.url = url;
        this.username = username;
        this.password = password;
        this.userid = userid;
        this.passwordKey = passwordKey;
    }

    public Credential() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getPasswordKey() {
        return passwordKey;
    }

    public void setPasswordKey(String passwordKey) {
        this.passwordKey = passwordKey;
    }
}
