package com.anastasia.notie.features.editNote;

import com.anastasia.notie.infrastructure.models.Note;
import com.anastasia.notie.utils.LCEState;

public class EditNoteState extends LCEState<EditNoteState, EditNoteState.EditNoteContent> {
    public EditNoteState(boolean isLoading, boolean isError, EditNoteContent content) {
        super(isLoading, isError, content);
    }

    public static class EditNoteContent {
        private final Note note;
        private final EditNoteActionState editNoteActionState;

        public EditNoteContent(Note note, EditNoteActionState editNoteActionState) {
            this.note = note;
            this.editNoteActionState = editNoteActionState;
        }

        public Note getNote() {
            return note;
        }

        public EditNoteActionState getEditNoteActionState() {
            return editNoteActionState;
        }
    }

    enum DataLoadingState {
        IDLE,
        SUCCESS,
        ERROR,
        LOADING
    }
    enum ActionType{
        ADD,
        EDIT,
        DELETE
    }

    public static class EditNoteActionState {
        private DataLoadingState dataLoadingState;
        private ActionType actionType;

        public EditNoteActionState(DataLoadingState dataLoadingState, ActionType actionType) {
            this.dataLoadingState = dataLoadingState;
            this.actionType = actionType;
        }

        public DataLoadingState getDataLoadingState() {
            return dataLoadingState;
        }

        public ActionType getActionType() {
            return actionType;
        }
    }
}
