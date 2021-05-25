package com.anastasia.notie;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    private final MutableLiveData<MainViewState> state;

    public MainViewModel() {
        this.state = new MutableLiveData<>();
        this.state.setValue(new MainViewState(true, false, null));
    }

    public LiveData<MainViewState> getState() {
        return state;
    }

    public void load() {
        state.postValue(this.state.getValue().setLoading());
    }
}
