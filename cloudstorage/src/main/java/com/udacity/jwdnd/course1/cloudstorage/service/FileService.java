package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.model.*;

import java.util.*;
import java.io.*;

public interface FileService {

    public boolean upload(FileData newFile, InputStream data) throws Exception;

    public boolean delete(int id) throws Exception;

    public List<FileData> getAllFiles(Integer userid) throws Exception;

    public FileData getFileData(int id) throws Exception;

    public InputStream getDataAsStream(int id) throws Exception;

    public Integer getFileByName(String name, Integer userid) throws Exception;
}
