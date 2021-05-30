package com.anastasia.notie;

import java.util.List;

public class MainViewState {
    private boolean isLoading;
    private boolean isError;
    private List<Note> content;

    public MainViewState(boolean isLoading, boolean isError, List<Note> content) {
        this.isLoading = isLoading;
        this.isError = isError;
        this.content = content;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public boolean isError() {
        return isError;
    }

    public List<Note> getContent() {
        return content;
    }

    public MainViewState setLoading() {
        this.isLoading = true;
        this.isError = false;
        this.content = null;

        return this;
    }

    public MainViewState setError() {
        this.isError = true;
        this.isLoading = false;
        this.content = null;

        return this;
    }

    public MainViewState setContent(List<Note> content) {
        this.content = content;
        this.isLoading = false;
        this.isError = false;

        return this;
    }
}
