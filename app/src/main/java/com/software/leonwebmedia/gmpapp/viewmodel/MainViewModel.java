package com.software.leonwebmedia.gmpapp.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.software.leonwebmedia.gmpapp.model.Commits;
import com.software.leonwebmedia.gmpapp.networking.CommitsApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends ViewModel {
    private final MutableLiveData<List<Commits>> commits = new MutableLiveData<>();
    private final MutableLiveData<Boolean> commitsLoadError = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();

    private Call<List<Commits>> commitsCall;

    public MainViewModel() {
        fetchCommits();
    }

    public LiveData<List<Commits>> getCommits() {
        return commits;
    }

    public LiveData<Boolean> getError() {
        return commitsLoadError;
    }

    public LiveData<Boolean> getLoading() {
        return loading;
    }

    private void fetchCommits() {
        loading.setValue(true);
        commitsCall = CommitsApi.getInstance().getLatestCommits();
        commitsCall.enqueue(new Callback<List<Commits>>() {
            @Override
            public void onResponse(Call<List<Commits>> call, Response<List<Commits>> response) {
                commitsLoadError.setValue(false);
                commits.setValue(response.body());
                loading.setValue(false);
                commitsCall = null;
            }

            @Override
            public void onFailure(Call<List<Commits>> call, Throwable t) {
                Log.e(getClass().getSimpleName(), "Error loading Commits", t);
                commitsLoadError.setValue(true);
                loading.setValue(false);
                commitsCall = null;
            }
        });
    }

    @Override
    protected void onCleared() {
        if (commitsCall != null) {
            commitsCall.cancel();
            commitsCall = null;
        }
    }

}