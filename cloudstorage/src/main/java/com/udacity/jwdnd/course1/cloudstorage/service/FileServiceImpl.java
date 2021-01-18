package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.*;
import com.udacity.jwdnd.course1.cloudstorage.model.*;

import org.springframework.stereotype.*;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.io.*;
import java.time.format.*;
import java.util.*;
import java.time.*;

@Service
public class FileServiceImpl implements FileService {
    private FileMapper mapper;
    private PlatformTransactionManager transactionManager;

    public FileServiceImpl(FileMapper mapper, PlatformTransactionManager transactionManager) {
        this.mapper = mapper;
        this.transactionManager = transactionManager;
    }

    public List<FileData> getAllFiles(Integer userid) throws Exception {
        List<FileData> fileList = null;

        try {
            fileList = mapper.getAllFiles(userid);
        }
        catch(Exception ex) {
            throw ex;
        }

        return fileList;
    }

    public boolean delete(int id) throws Exception {
        TransactionStatus txStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());

        try {
            mapper.deleteFile(id);
        }
        catch(Exception ex) {
            transactionManager.rollback(txStatus);
            throw ex;
        }

        transactionManager.commit(txStatus);
        return true;
    }

    public boolean upload(FileData newFile, InputStream data) throws Exception {
        TransactionStatus txStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());

        // Create file with timestamp appended to name
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddhhmmss");
        String formattedDateTime =  dateTime.format(formatter);

        String filePath = "C:\\cloudstorage\\";
        String filename = newFile.getName() + formattedDateTime;
        File newFileInDisk = null;

        try {
            newFileInDisk = new File(filePath + filename);
            boolean isCreated = newFileInDisk.createNewFile();
            if(!isCreated) {
                throw new Exception("File not created");
            }

            storeDataIntoFile(newFileInDisk, data);
            mapper.addFile(new FileData(null, newFile.getName(), newFile.getContentType(), newFile.getSize(), newFile.getUserid()), new FileInputStream(newFileInDisk));
        }
        catch(Exception ex) {
            transactionManager.rollback(txStatus);
            throw ex;
        }
        finally {
            data.close();
        }

        transactionManager.commit(txStatus);
        return true;
    }

    private void storeDataIntoFile(File newFile, InputStream data) throws Exception {
        FileOutputStream outputStream = null;

        try {
            outputStream = new FileOutputStream(newFile, true);

            while(true) {
                byte[] currentBytes = new byte[1000];
                int bytesRead = data.read(currentBytes);

                if(bytesRead == -1) {
                    break;
                }

                outputStream.write(currentBytes);
            }
        }
        catch(Exception ex) {
            throw ex;
        }
        finally {
            outputStream.close();
        }
    }

    public Integer getFileByName(String name, Integer userid) throws Exception {
        Integer id = null;

        try {
            id = mapper.getFileByName(name, userid);
        }
        catch(Exception ex) {
            throw ex;
        }

        return id;
    }

    public InputStream getDataAsStream(int id) throws Exception {
        InputStream fileData = null;

        try {
            fileData = mapper.getDataAsStream(id);
        }
        catch(Exception ex) {
            throw ex;
        }

        return fileData;
    }

    public FileData getFileData(int id) throws Exception {
        FileData selectedFileData = null;

        try {
            selectedFileData = mapper.getFileData(id);
        }
        catch(Exception ex) {
            throw ex;
        }

        return selectedFileData;
    }
}
