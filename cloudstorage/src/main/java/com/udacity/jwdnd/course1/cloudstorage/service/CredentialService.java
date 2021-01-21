package com.udacity.jwdnd.course1.cloudstorage.service;

import java.util.*;

import com.udacity.jwdnd.course1.cloudstorage.model.*;

public interface CredentialService {

    public boolean add(Credential newCredential) throws Exception;

    public boolean update(Credential updateCredential) throws Exception;

    public boolean delete(int id) throws Exception;

    public boolean isUrlExisting(String url, Integer userid) throws Exception;

    public List<Credential> getAllCredentials(Integer userid) throws Exception;

    public Credential getCredentialDecrypted(Integer id, Integer userid) throws Exception;
}
