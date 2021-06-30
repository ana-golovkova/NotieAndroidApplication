package com.anastasia.notie.infrastructure.usecases;

import com.anastasia.notie.infrastructure.models.Note;
import com.anastasia.notie.infrastructure.repositories.NotesRepository;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;

public class GetNotes {
    private final NotesRepository notesRepository;

    @Inject
    public GetNotes(NotesRepository notesRepository) {
        this.notesRepository = notesRepository;
    }

    public Observable<Map<Integer,Note>> execute() {
        return notesRepository.getNotes();
    }
}
