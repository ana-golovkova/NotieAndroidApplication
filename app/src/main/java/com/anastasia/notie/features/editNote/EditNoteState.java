package com.anastasia.notie.features.editNote;

import com.anastasia.notie.infrastructure.models.Note;
import com.anastasia.notie.utils.LCEState;

public class EditNoteState extends LCEState<EditNoteState.EditNoteContent> {
    public EditNoteState(boolean isLoading, boolean isError, EditNoteContent content) {
        super(isLoading, isError, content);
    }

    public static class EditNoteContent {
        private final Note note;
        private final DataLoadingState noteUpdateState;

        public EditNoteContent(Note note, DataLoadingState noteUpdateState) {
            this.note = note;
            this.noteUpdateState = noteUpdateState;
        }
    }

    enum DataLoadingState {
        IDLE,
        SUCCESS,
        ERROR,
        LOADING
    }
}
