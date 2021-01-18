package com.udacity.jwdnd.course1.cloudstorage.service;

import org.springframework.stereotype.*;
import org.springframework.transaction.*;
import org.springframework.transaction.support.*;

import com.udacity.jwdnd.course1.cloudstorage.model.*;
import com.udacity.jwdnd.course1.cloudstorage.mapper.*;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UserServiceImpl implements UserService {
    private UserMapper mapper;
    private HashService hashService;
    private PlatformTransactionManager transactionManager;

    public UserServiceImpl(UserMapper mapper, HashService hashService, PlatformTransactionManager transactionManager) {
        this.mapper = mapper;
        this.hashService = hashService;
        this.transactionManager = transactionManager;
    }

    public User getUser(int userid) throws Exception {
        User user = null;

        try {
            user = mapper.getUserById(userid);
        }
        catch(Exception ex) {
            throw ex;
        }

        return user;
    }

    public boolean add(User newUser) throws Exception {
        TransactionStatus txStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());

        // Generate salt
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);

        // Hash password
        HashService hashService = new HashService();
        String hashedPassword = hashService.getHashedValue(newUser.getPassword(), encodedSalt);

        try {
            mapper.addUser(new User(null, newUser.getFirstname(), newUser.getLastname(), newUser.getUsername(), hashedPassword, encodedSalt));
        }
        catch(Exception ex) {
            transactionManager.rollback(txStatus);
            throw ex;
        }

        transactionManager.commit(txStatus);
        return true;
    }

    public boolean isUserExisting(String firstName, String lastName) throws Exception {
        User user = null;

        try {
            user = mapper.getUserByName(firstName, lastName);
        }
        catch(Exception ex) {
            throw ex;
        }

        return user == null ? false : true;
    }

    public boolean isUsernameExisting(String username) throws Exception {
        User user = null;

        try {
            user = mapper.getUserByUsername(username);
        }
        catch(Exception ex) {
            throw ex;
        }

        return user == null ? false : true;
    }

    public User getUserByName(String username) throws Exception {
        User user = null;

        try {
            user = mapper.getUserByUsername(username);
        }
        catch(Exception ex) {
            throw ex;
        }

        return user;
    }
}
