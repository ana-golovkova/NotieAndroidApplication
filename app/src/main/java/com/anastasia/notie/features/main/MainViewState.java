package com.anastasia.notie.features.main;

import com.anastasia.notie.infrastructure.models.Note;
import com.anastasia.notie.utils.LCEState;

import java.util.List;
import java.util.Map;

public class MainViewState extends LCEState<MainViewState, Map<Integer,Note>> {

    public MainViewState(boolean isLoading, boolean isError, Map<Integer,Note> content) {
        super(isLoading, isError, content);
    }
}
