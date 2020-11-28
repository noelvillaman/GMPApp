package com.software.leonwebmedia.gmpapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Commit {
    @SerializedName("author")
    @Expose
    private Author author;

    @SerializedName("message")
    @Expose
    private String message;

    public Author getAuthor() {
        return author;
    }
    public String getMessage() {
        return message;
    }
}
