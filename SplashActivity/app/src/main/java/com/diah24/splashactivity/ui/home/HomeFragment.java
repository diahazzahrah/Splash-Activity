package com.diah24.splashactivity.ui.home;

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
import com.diah24.splashactivity.api.model.Post;
import com.diah24.splashactivity.api.model.PostList;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView rvNews;
    private ProgressBar pbLoading;
    private PostAdapter postAdapter;


    public static HomeFragment newInstance() {
    HomeFragment fragment = new HomeFragment();
        return new HomeFragment();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        rvNews = view.findViewById(R.id.rvNews);
        pbLoading = view.findViewById(R.id.pbLoading);


        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        //inisialisasi recyclerview
        postAdapter = new PostAdapter();
        rvNews.setAdapter(postAdapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        rvNews.setLayoutManager(layoutManager);

        fetchData();
    }
    private void fetchData()
    {
        //inisialisasi retrofit
        BlogClient client = BlogServiceGenerator.createService(BlogClient.class);

        //pemanggilan api
        client.getListPost().enqueue(new Callback<PostList>() {
            @Override
            public void onResponse(Call<PostList> call, Response<PostList> response) {
                //ketika berhasil
                if(response.isSuccessful()){
                    //ketika berhasil (200..300) => status code
//                    response.body().getStatus();
                    PostList postList = response.body();
                    List<Post> listPost = response.body().getData();
                    postAdapter.setPostList(response.body().getData());
                }else{
                        Toast.makeText(getActivity(), "Gagal fetch data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PostList> call, Throwable t) {
                //error dari api
                Toast.makeText(getActivity(),t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}