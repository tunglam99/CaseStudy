package com.codegym.service;

import com.codegym.model.NoteType;

public interface NoteTypeService {
    Iterable<NoteType> findAll();
    NoteType findById(Long id);
}
