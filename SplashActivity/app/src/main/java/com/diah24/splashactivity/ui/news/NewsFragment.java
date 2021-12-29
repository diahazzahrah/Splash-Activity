package com.diah24.splashactivity.ui.news;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.diah24.splashactivity.R;
import com.diah24.splashactivity.api.BlogClient;
import com.diah24.splashactivity.api.BlogServiceGenerator;
import com.diah24.splashactivity.api.model.DeletePostResponse;
import com.diah24.splashactivity.api.model.Post;
import com.diah24.splashactivity.api.model.PostList;
import com.diah24.splashactivity.ui.createedit.CreateEditActivity;
import com.diah24.splashactivity.ui.home.PostAdapter;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NewsFragment extends Fragment implements NewsAdapterActionListener{

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView rvNews;
    private ProgressBar pbLoading;
    private NewsAdapter adapter;
    private FloatingActionButton fabCreate;
    public static int REQUEST_CODE_UPDATE_SUCCESS_KEY = 200;

    public static NewsFragment newInstance() {
        return new NewsFragment();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_news, container, false);
        rvNews = view.findViewById(R.id.rvNews);
        pbLoading = view.findViewById(R.id.pbLoading);
        fabCreate = view.findViewById(R.id.fabCreate);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        initRecyclerview();
        fetchData();
        initSwipeRefresh();
    }
    private void initSwipeRefresh(){
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        fabCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), CreateEditActivity.class);
                        startActivityForResult(intent, REQUEST_CODE_UPDATE_SUCCESS_KEY);
//                Toast.makeText(getActivity(), "Test", Toast.LENGTH_LONG).show();
            }
        });
    }
    private void fetchData()
    {
        pbLoading.setVisibility(View.VISIBLE);
        //inisialisasi retrofit
        BlogClient client = BlogServiceGenerator.createService(BlogClient.class);
        //pemanggilan api
        client.getListPost().enqueue(new Callback<PostList>() {
            @Override
            public void onResponse(Call<PostList> call, Response<PostList> response) {
                //ketika berhasil
                pbLoading.setVisibility(View.GONE);
                if(response.isSuccessful()){
                        adapter.setPostList(response.body().getData());
                }else{
                    Toast.makeText(getActivity(), "Gagal fetch data", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<PostList> call, Throwable t) {
                pbLoading.setVisibility(View.GONE);
                //error dari api
                Toast.makeText(getActivity(),t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void initRecyclerview() {
        adapter = new NewsAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvNews.setLayoutManager(linearLayoutManager);
        rvNews.setAdapter(adapter);
    }

    @Override
    public void onClickDelete(Post post, int absoluteAdapterPosition) {
        showPopupDelete(post, absoluteAdapterPosition);
    }

    @Override
    public void onClickEdit(Post post){
        Intent intent = new Intent(getActivity(), CreateEditActivity.class);
        intent.putExtra(CreateEditActivity.POST_KEY,post);
        startActivityForResult(intent, REQUEST_CODE_UPDATE_SUCCESS_KEY);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode,data);
        if(requestCode == REQUEST_CODE_UPDATE_SUCCESS_KEY && resultCode == Activity.RESULT_OK) {
            if(data != null){
                boolean success = data.getBooleanExtra(CreateEditActivity.RESULT_CREATE_UPDATE_SUCCESS_KEY, false);
                if(success){
                    fetchData();
                }
            }
        }
    }

    private void showPopupDelete(Post post, int absoluteAdapterPosition) {
        new MaterialAlertDialogBuilder(getContext())
                .setTitle("Delete Post")
                .setMessage("Are you sure to delete post\"" + post.getTitle() + "\"?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //TODO:: DELETE
                        deletePostFromServer(post, absoluteAdapterPosition);
                        dialogInterface.dismiss();
                    }

                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }
    private void deletePostFromServer(Post post, int absoluteAdapterPosition) {
        pbLoading.setVisibility(View.VISIBLE);
        BlogClient client = BlogServiceGenerator.createService(BlogClient.class);
        client.deletePost(String.valueOf(post.getId())).enqueue(new Callback<DeletePostResponse>() {
            @Override
            public void onResponse(Call<DeletePostResponse> call, Response<DeletePostResponse> response) {
                pbLoading.setVisibility(View.GONE);
                if(response.isSuccessful()){
                    adapter.removePost(post, absoluteAdapterPosition);
                    Toast.makeText(getContext(),response.body().getMessage(),Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Delete post is failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DeletePostResponse> call, Throwable t) {
                pbLoading.setVisibility(View.GONE);
                Toast.makeText(getContext(), t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }


}