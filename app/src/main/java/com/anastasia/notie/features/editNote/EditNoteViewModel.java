package com.anastasia.notie.features.editNote;

import androidx.lifecycle.MutableLiveData;

public class EditNoteViewModel {
    private final MutableLiveData<EditNoteState> state;

    public EditNoteViewModel(MutableLiveData<EditNoteState> state) {
        this.state = state;
    }
}
