package com.anastasia.notie.utils;

import com.anastasia.notie.infrastructure.models.Note;

import java.util.List;

public abstract class LCEState<K extends LCEState , T> {
    private boolean isLoading;
    private boolean isError;
    private T content;

    public LCEState(boolean isLoading, boolean isError, T content) {
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

    public T getContent() {
        return content;
    }

    public K setLoading() {
        this.isLoading = true;
        this.isError = false;
        this.content = null;

        return (K) this;
    }

    public K setError() {
        this.isError = true;
        this.isLoading = false;
        this.content = null;

        return (K) this;
    }

    public K setContent(T content) {
        this.content = content;
        this.isLoading = false;
        this.isError = false;

        return (K) this;
    }
}
