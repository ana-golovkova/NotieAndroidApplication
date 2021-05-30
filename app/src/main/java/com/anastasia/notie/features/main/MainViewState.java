package com.anastasia.notie.features.main;

import com.anastasia.notie.infrastructure.models.Note;
import com.anastasia.notie.utils.LCEState;

import java.util.List;

public class MainViewState extends LCEState<MainViewState, List<Note>> {

    public MainViewState(boolean isLoading, boolean isError, List<Note> content) {
        super(isLoading, isError, content);
    }
}
