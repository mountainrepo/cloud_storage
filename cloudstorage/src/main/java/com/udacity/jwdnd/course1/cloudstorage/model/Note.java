package com.udacity.jwdnd.course1.cloudstorage.model;

public class Note {
    private Integer id;
    private String title;
    private String description;
    private Integer userid;

    public Note(Integer id, String title, String description, Integer userid) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.userid = userid;
    }

    public Note() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }
}
