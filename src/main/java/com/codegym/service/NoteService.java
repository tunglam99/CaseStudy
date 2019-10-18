package com.codegym.service;

import com.codegym.model.Note;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.File;
import java.io.IOException;

public interface NoteService {
    Page<Note> findAll(Pageable pageable);
    Note findById(Long id);
    void save(Note note);
    void remove(Long id);
    Page<Note> findAllByTitleContaining(String title, Pageable pageable);
    File exportExcel() throws IOException;
    void importExcel() throws IOException;
}
