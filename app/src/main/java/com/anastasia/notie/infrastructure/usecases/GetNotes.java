package com.anastasia.notie.infrastructure.usecases;

import com.anastasia.notie.infrastructure.models.Note;
import com.anastasia.notie.infrastructure.repositories.NotesRepository;

import java.util.List;

import io.reactivex.Observable;

public class GetNotes {
    private final NotesRepository notesRepository;

    public GetNotes() {
        this.notesRepository = new NotesRepository();
    }

    public Observable<List<Note>> execute() {
        return notesRepository.getNotes();
    }
}
