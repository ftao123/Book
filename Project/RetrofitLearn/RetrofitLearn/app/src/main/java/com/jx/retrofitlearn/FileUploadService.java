package com.jx.retrofitlearn;

import com.jx.retrofitlearn.model.Msg;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by jiang.xu on 2016/2/29.
 */
public interface FileUploadService {
    @Multipart
    @POST("index.php?c=Upload&m=doUpload")
  //  @POST("index.php/Upload/doUpload")
    Call<Msg> upload(
            @Part("file\"; filename=\"image.png\" ") RequestBody file,
            @Part("description") RequestBody description);
}
