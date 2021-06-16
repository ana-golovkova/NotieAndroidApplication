package com.anastasia.notie.features.main;

import com.anastasia.notie.infrastructure.models.Note;
import com.anastasia.notie.utils.DataLoadingState;
import com.anastasia.notie.utils.LCEState;

import java.util.Map;

public class MainViewState extends LCEState<MainViewState, MainViewState.MainViewContent> {
    public static class MainViewContent {
        private Map<Integer, Note> notes;
        private DataLoadingState deleteNotesState;

        public MainViewContent(Map<Integer, Note> content, DataLoadingState deleteNotesState) {
            this.notes = content;
            this.deleteNotesState = deleteNotesState;
        }

        public Map<Integer, Note> getNotes() {
            return notes;
        }

        public DataLoadingState getDeleteNotesState() {
            return deleteNotesState;
        }
    }

    public MainViewState(boolean isLoading, boolean isError, MainViewState.MainViewContent content) {
        super(isLoading, isError, content);
    }
}
