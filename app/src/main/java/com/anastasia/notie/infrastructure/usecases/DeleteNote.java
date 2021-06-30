package com.anastasia.notie.infrastructure.usecases;

import com.anastasia.notie.infrastructure.repositories.NotesRepository;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;

public class DeleteNote {
    private final NotesRepository notesRepository;

    @Inject
    public DeleteNote(NotesRepository notesRepository) {
        this.notesRepository = notesRepository;
    }

    public Completable execute(int id) {
        return notesRepository.deleteNode(id);
    }
}
