package com.software.leonwebmedia.gmpapp.viewmodel;

import android.os.Bundle;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.software.leonwebmedia.gmpapp.model.Commits;
import com.software.leonwebmedia.gmpapp.networking.CommitsApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectedCommitViewModel extends ViewModel {
    private final MutableLiveData<Commits> selectedCommit = new MutableLiveData<>();

    private Call<Commits> commitCall;

    public LiveData<Commits> getSelectedCommit() {
        return selectedCommit;
    }

    public void setSelectedCommit(Commits commit) {
        selectedCommit.setValue(commit);
    }

    public void saveToBundle(Bundle outState) {
        if (selectedCommit.getValue() != null) {
            outState.putStringArray("items", new String[]{
                    selectedCommit.getValue().getAuthor().toString(),
                    selectedCommit.getValue().getHash().toString(),
                    selectedCommit.getValue().getMessage().toString()});
        }
    }

    public void restoreFromBundle(Bundle savedInstanceState) {
        if (selectedCommit.getValue() == null) {
            // We only care about restoring if we don't have a selected Commit set already
            if (savedInstanceState != null && savedInstanceState.containsKey("commit_details")) {
                loadCommit(savedInstanceState.getStringArray("commit_details"));
            }
        }
    }

    private void loadCommit(String[] commitDetails) {
        commitCall = CommitsApi.getInstance().getCommits(commitDetails[0], commitDetails[1]);
        commitCall.enqueue(new Callback<Commits>() {
            @Override
            public void onResponse(Call<Commits> call, Response<Commits> response) {
                selectedCommit.setValue(response.body());
                commitCall = null;
            }

            @Override
            public void onFailure(Call<Commits> call, Throwable t) {
                Log.e(getClass().getSimpleName(), "Error loading Commit", t);
                commitCall = null;
            }
        });
    }

    @Override
    protected void onCleared() {
        if (commitCall != null) {
            commitCall.cancel();
            commitCall = null;
        }
    }

}
