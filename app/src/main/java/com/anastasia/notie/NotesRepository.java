package com.anastasia.notie;

import java.util.ArrayList;
import java.util.List;

public class NotesRepository {
    public List<Note> getNotes() {
        List<Note> notes = new ArrayList<Note>();
        notes.add(new Note(1, "title", "content"));
        return notes;
    }
}
