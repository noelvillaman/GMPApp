package com.software.leonwebmedia.gmpapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Commits {
    @SerializedName("author")
    @Expose
    private Author author;

    @SerializedName("sha")
    @Expose
    private String hash;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("commit")
    @Expose
    private Commit commit;

    public Commits() {
    }

    public Author getAuthor() {
        return author;
    }

    public String getHash() {
        return hash;
    }

    public String getMessage() {
        return message;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setCommit(Commit commit) {
        this.commit = commit;
    }

    public Commit getCommit() {
        return commit;
    }

}
