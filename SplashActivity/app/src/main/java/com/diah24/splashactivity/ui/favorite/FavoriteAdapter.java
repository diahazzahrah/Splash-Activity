package com.diah24.splashactivity.ui.favorite;

import static com.diah24.splashactivity.ui.detail.DetailActivity.DATA_EXTRA_KEY;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.diah24.splashactivity.R;
import com.diah24.splashactivity.api.model.Post;
import com.diah24.splashactivity.db.entity.PostEntity;
import com.diah24.splashactivity.ui.detail.DetailActivity;

import java.util.ArrayList;
import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {
    private final List<PostEntity> listPost = new ArrayList<>();
    void setListPost(List<PostEntity> listPost){
        this.listPost.clear();
        this.listPost.addAll(listPost);
        this.notifyDataSetChanged();
    }
    @NonNull
    @Override
    public FavoriteAdapter.FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent,
                        false);
        return new FavoriteViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull
                                         FavoriteAdapter.FavoriteViewHolder holder, int position) {
        holder.bindItem(listPost.get(position));
    }
    @Override
    public int getItemCount() {
        return listPost.size();
    }
    public static class FavoriteViewHolder extends RecyclerView.ViewHolder {
        private final ImageView ivThumbnail;
        private final TextView tvTitle;
        private final TextView tvDate;
        private ImageView ivFavorite;
        public FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            ivThumbnail = itemView.findViewById(R.id.ivThumbnailNews);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDate = itemView.findViewById(R.id.tvDateNews);
//            ivFavorite = itemView.findViewById(R.id.ivFavorite);
        }
        public void bindItem(PostEntity postEntity) {
            Post post = new Post();
            post.setId(postEntity.getId());
            post.setTitle(postEntity.getTitle());
            post.setBody(postEntity.getBody());
            post.setCreatedAt(postEntity.getCreatedAt());
            post.setUpdatedAt(postEntity.getUpdatedAt());
            post.setThumbnailUrl(postEntity.getImagePath());
            tvTitle.setText(post.getTitle());
            tvDate.setText(post.getCreatedAt());
            Glide.with(itemView.getContext())
                    .load(post.getThumbnailUrl())
                    .into(ivThumbnail);


//            if (post.isFavorite()) {
//                ivFavorite.setImageDrawable(ContextCompat.getDrawable(itemView.getConte
//                        xt(), R.drawable.ic_baseline_favorite_24));
//            } else {
//                ivFavorite.setImageDrawable(ContextCompat.getDrawable(itemView.getConte
//                        xt(), R.drawable.ic_baseline_favorite_border_24));
//            }


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(itemView.getContext(), DetailActivity.class);
                    intent.putExtra(DATA_EXTRA_KEY, post);
                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }
}