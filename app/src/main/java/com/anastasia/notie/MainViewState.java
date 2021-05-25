package com.anastasia.notie;

public class MainViewState {
    private boolean isLoading;
    private boolean isError;
    private String content;

    public MainViewState(boolean isLoading, boolean isError, String content) {
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

    public String getContent() {
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

    public MainViewState setContent(String content) {
        this.content = content;
        this.isLoading = false;
        this.isError = false;

        return this;
    }
}
