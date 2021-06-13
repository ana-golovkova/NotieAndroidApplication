package com.anastasia.notie.infrastructure.models;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Note {
    @Nullable
    private final Integer id;
    @NonNull
    private final String title;
    @NonNull
    private final String content;

    public Note(@Nullable Integer id, @NonNull String title, @NonNull String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    @Nullable
    public Integer getId() {
        return id;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    @NonNull
    public String getContent() {
        return content;
    }
}
