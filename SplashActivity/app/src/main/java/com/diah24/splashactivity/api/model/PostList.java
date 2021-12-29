package com.diah24.splashactivity.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PostList {
    @SerializedName("status")
    private int status;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private List<Post> data;

    public PostList(){

    }

    public int getStatus()
    {
        return status;
    }
    public void setStatus(int status)
    {
        this.status = status;
    }
    public String getMessage(){
        return message;
    }
    public void setMessage(String message)
    {
        this.message = message;
    }
    public List<Post> getData()
    {
        return data;
    }
    public void setData(List<Post>data)
    {
        this.data = data;
    }
}
