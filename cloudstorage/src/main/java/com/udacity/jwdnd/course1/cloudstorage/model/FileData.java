package com.udacity.jwdnd.course1.cloudstorage.model;

public class FileData {
    private Integer id;
    private String name;
    private String contenttype;
    private String size;
    private Integer userid;

    public FileData(Integer id, String name, String contenttype, String size, Integer userid) {
        this.id = id;
        this.name = name;
        this.contenttype = contenttype;
        this.size = size;
        this.userid = userid;
    }

    public FileData() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContentType() {
        return contenttype;
    }

    public void setContenttype(String contenttype) {
        this.contenttype = contenttype;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }
}
