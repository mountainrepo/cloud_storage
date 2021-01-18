package com.udacity.jwdnd.course1.cloudstorage.service;

import org.springframework.stereotype.*;
import org.springframework.transaction.*;
import org.springframework.transaction.support.*;
import java.util.*;

import com.udacity.jwdnd.course1.cloudstorage.mapper.*;
import com.udacity.jwdnd.course1.cloudstorage.model.*;

@Service
public class NoteServiceImpl implements NoteService {
    private NoteMapper mapper;
    private final PlatformTransactionManager transactionManager;

    public NoteServiceImpl(NoteMapper mapper, PlatformTransactionManager transactionManager) {
        this.mapper = mapper;
        this.transactionManager = transactionManager;
    }

    public boolean add(Note newNote) throws Exception {
        TransactionStatus txStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());

        try {
            mapper.addNote(new Note(null, newNote.getTitle(), newNote.getDescription(), newNote.getUserid()));
        }
        catch(Exception ex) {
            transactionManager.rollback(txStatus);
            throw ex;
        }

        transactionManager.commit(txStatus);
        return true;
    }

    public boolean update(Note updateNote) throws Exception {
        TransactionStatus txStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());

        try {
            mapper.deleteNote(updateNote.getId());
            mapper.addNoteWithId(new Note(updateNote.getId(), updateNote.getTitle(), updateNote.getDescription(), updateNote.getUserid()));
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
            mapper.deleteNote(id);
        }
        catch(Exception ex) {
            transactionManager.rollback(txStatus);
            throw ex;
        }

        transactionManager.commit(txStatus);
        return true;
    }

    public boolean isTitleExisting(String title, Integer userid) throws Exception {
        Note titleNote = null;

        try {
            titleNote = mapper.getNoteByTitle(title, userid);
        }
        catch(Exception ex) {
            throw ex;
        }

        return titleNote == null ? false : true;
    }

    public List<Note> getAllNotes(Integer userid) {
        List<Note> noteList = null;

        try {
            noteList = mapper.getAllNotes(userid);
        }
        catch(Exception ex) {
            throw ex;
        }

        return noteList;
    }
}
