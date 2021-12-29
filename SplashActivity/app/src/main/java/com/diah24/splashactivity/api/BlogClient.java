package com.diah24.splashactivity.api;

import com.diah24.splashactivity.api.model.CreatePostRequest;
import com.diah24.splashactivity.api.model.CreatePostResponse;
import com.diah24.splashactivity.api.model.DeletePostResponse;
import com.diah24.splashactivity.api.model.EditPostRequest;
import com.diah24.splashactivity.api.model.EditPostResponse;
import com.diah24.splashactivity.api.model.PostList;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface BlogClient {
    @GET("posts.php?function=get_posts")
    Call<PostList> getListPost();

    @POST("posts.php?function=insert_posts")
    Call<CreatePostResponse> createPostRequest(@Body CreatePostRequest request);

    @DELETE("posts.php?function=delete_posts")
    Call<DeletePostResponse> deletePost(@Query("id")String id);

    @PATCH("posts.php?function=update_posts")
    Call<EditPostResponse> editPost(@Body EditPostRequest request, @Query("id") String id);
}
