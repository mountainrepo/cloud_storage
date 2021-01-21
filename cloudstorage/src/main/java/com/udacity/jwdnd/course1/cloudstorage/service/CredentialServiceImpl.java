package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.*;
import com.udacity.jwdnd.course1.cloudstorage.model.*;

import org.springframework.stereotype.*;
import org.springframework.transaction.*;
import org.springframework.transaction.support.*;

import java.security.SecureRandom;
import java.util.*;

@Service
public class CredentialServiceImpl implements CredentialService {
    private CredentialMapper mapper;
    private PlatformTransactionManager transactionManager;
    private EncryptionService encryptionService;

    public CredentialServiceImpl(CredentialMapper mapper, PlatformTransactionManager transactionManager, EncryptionService encryptionService) {
        this.mapper = mapper;
        this.transactionManager = transactionManager;
        this.encryptionService = encryptionService;
    }

    public boolean add(Credential newCredential) throws Exception {
        TransactionStatus txStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
        String encodedSalt = getEncodedSalt();

        try {
            String encryptedPassword = encryptionService.encryptValue(newCredential.getPassword(), encodedSalt);
            mapper.addCredential(new Credential(null, newCredential.getUrl(), newCredential.getUsername(), encryptedPassword, newCredential.getUserid(), encodedSalt));
        }
        catch(Exception ex) {
            transactionManager.rollback(txStatus);
            throw ex;
        }

        transactionManager.commit(txStatus);
        return true;
    }

    private String getEncodedSalt() {
        // Generate salt
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);

        return encodedSalt;
    }

    public boolean update(Credential updateCredential) throws Exception {
        TransactionStatus txStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
        String encodedSalt = getEncodedSalt();

        try {
            mapper.deleteCredential(updateCredential.getId());

            String encryptedPassword = encryptionService.encryptValue(updateCredential.getPassword(), encodedSalt);
            mapper.addCredentialWithId(new Credential(updateCredential.getId(), updateCredential.getUrl(), updateCredential.getUsername(), encryptedPassword, updateCredential.getUserid(), encodedSalt));
        }
        catch(Exception ex) {
            transactionManager.rollback(txStatus);
            throw ex;
        }

        transactionManager.commit(txStatus);
        return true;
    }

    public boolean delete(int id) throws Exception {
        TransactionStatus txStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());

        try {
            mapper.deleteCredential(id);
        }
        catch(Exception ex) {
            transactionManager.rollback(txStatus);
            throw ex;
        }

        transactionManager.commit(txStatus);
        return true;
    }

    public boolean isUrlExisting(String url, Integer userid) throws Exception {
        Credential cred = null;

        try {
            cred = mapper.getCredentialByUrl(url, userid);
        }
        catch(Exception ex) {
            throw ex;
        }

        return cred == null ? false : true;
    }

    public List<Credential> getAllCredentials(Integer userid) throws Exception {
        List<Credential> credentialList = null;

        try {
            credentialList = mapper.getAllCredentials(userid);

            /*
            for(int index = 0; index < credentialList.size(); index++) {
                Credential currentCredential = credentialList.get(index);
                String unencryptedPassword = encryptionService.decryptValue(currentCredential.getPassword(), currentCredential.getPasswordKey());
                currentCredential.setPassword(unencryptedPassword);
            }
            */
        }
        catch(Exception ex) {
            throw ex;
        }

        return credentialList;
    }

    public Credential getCredentialDecrypted(Integer id, Integer userid) throws Exception {
        Credential credential = null;

        try {
            credential = mapper.getCredential(id, userid);

            String unencryptedPassword = encryptionService.decryptValue(credential.getPassword(), credential.getPasswordKey());
            credential.setPassword(unencryptedPassword);
        }
        catch(Exception ex) {
            throw ex;
        }

        return credential;
    }
}
