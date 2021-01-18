package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.model.*;

public interface UserService {
    public User getUser(int userid) throws Exception;

    public User getUserByName(String username) throws Exception;

    public boolean add(User newUser) throws Exception;

    public boolean isUserExisting(String firstName, String lastName) throws Exception;

    public boolean isUsernameExisting(String username) throws Exception;
}
