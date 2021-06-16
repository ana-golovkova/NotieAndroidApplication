package com.anastasia.notie.infrastructure.usecases;

import com.anastasia.notie.infrastructure.models.Note;
import com.anastasia.notie.infrastructure.repositories.NotesRepository;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

public class GetNotes {
    private final NotesRepository notesRepository;

    public GetNotes() {
        this.notesRepository = new NotesRepository();
    }

    public Observable<Map<Integer,Note>> execute() {
        return notesRepository.getNotes();
    }
}
