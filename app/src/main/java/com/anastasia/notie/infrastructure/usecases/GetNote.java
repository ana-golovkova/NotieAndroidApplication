package com.anastasia.notie.infrastructure.usecases;

import com.anastasia.notie.infrastructure.models.Note;
import com.anastasia.notie.infrastructure.repositories.NotesRepository;

import javax.inject.Inject;

import io.reactivex.Observable;

public class GetNote {
    private final NotesRepository notesRepository;

    @Inject
    public  GetNote(NotesRepository notesRepository) {
        this.notesRepository = notesRepository;
    }

    public Observable<Note> execute(int id) {
        return notesRepository.getNote(id);
    }
}
