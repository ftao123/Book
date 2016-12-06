package com.jx.retrofitlearn;

import com.jx.retrofitlearn.model.Contributor;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


/**
 * Created by jiang.xu on 2015/11/7.
 */
public interface GitHubClient {
   @GET("/repos/{owner}/{repo}/contributors")
   Call<List<Contributor>> contributors(
           @Path("owner") String owner,
           @Path("repo") String repo
   );

}
