package com.udacity.jwdnd.course1.cloudstorage.service;

import java.util.*;

import com.udacity.jwdnd.course1.cloudstorage.model.*;

public interface NoteService {

    public boolean add(Note newNote) throws Exception;

    public boolean update(Note updateNote) throws Exception;

    public boolean delete(int id) throws Exception;

    public boolean isTitleExisting(String title, Integer userid) throws Exception;

    public List<Note> getAllNotes(Integer userid) throws Exception;
}
