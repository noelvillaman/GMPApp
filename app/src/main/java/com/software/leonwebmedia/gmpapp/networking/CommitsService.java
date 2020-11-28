package com.software.leonwebmedia.gmpapp.networking;

import com.software.leonwebmedia.gmpapp.model.Commits;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CommitsService {
    @GET("repos/twitter/bootstrap/commits?per_page=100&sha=d335adf644b213a5ebc9cee3f37f781ad55194ef")
    Call<List<Commits>> getLatestCommits();

    @GET("repos/{owner}/{name}")
    Call<Commits> getCommits(@Path("owner") String repoOwner, @Path("name") String repoName);

}
