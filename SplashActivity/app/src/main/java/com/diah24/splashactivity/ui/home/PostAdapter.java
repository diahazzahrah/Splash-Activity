package com.diah24.splashactivity.ui.home;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.diah24.splashactivity.R;
import com.diah24.splashactivity.api.model.Post;
import com.diah24.splashactivity.ui.detail.DetailActivity;

import java.util.ArrayList;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private final List<Post>postList = new ArrayList<>();
    void setPostList(List<Post> postList){
        this.postList.addAll(postList);
        this.notifyDataSetChanged();
    }

    static class PostViewHolder extends RecyclerView.ViewHolder{

      private  final TextView tvTitle;
      private  final TextView tvDate;
      private  final ImageView ivThumbnail;
      private  ImageView ivFavorite;


        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDate = itemView.findViewById(R.id.tvDateNews);
            ivThumbnail = itemView.findViewById(R.id.ivThumbnailNews);
            ivFavorite = itemView.findViewById(R.id.ivFavorite);

        }

        public void bindItem(Post post) {
            tvTitle.setText(post.getTitle());
            tvDate.setText(post.getCreatedAt());
            Glide.with(itemView.getContext())
                    .load(post.getThumbnailUrl())
                    .into(ivThumbnail);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    Intent intent = new Intent(itemView.getContext(), DetailActivity.class);
                    intent.putExtra(DetailActivity.DATA_EXTRA_KEY,post);
                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }

    @NonNull
    @Override
    public PostAdapter.PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news,parent,false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.PostViewHolder holder, int position) {
        holder.bindItem(postList.get(position));
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }
}
