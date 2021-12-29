package com.diah24.splashactivity.ui.news;

import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.diah24.splashactivity.R;
import com.diah24.splashactivity.api.model.Post;
import com.diah24.splashactivity.api.BlogServiceGenerator;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.PostViewHolder> {

    private final List<Post> postList = new ArrayList<>();
    private final NewsAdapterActionListener actionListener;
    public NewsAdapter(NewsAdapterActionListener actionListener){
        this.actionListener = actionListener;
    }

    void setPostList(List<Post> postList){
        this.postList.clear();
        this.postList.addAll(postList);
        this.notifyDataSetChanged();
    }

    public void removePost(Post post, int adapterPosition) {
        this.postList.remove(post);
        this.notifyItemRemoved(adapterPosition);
    }

    static class PostViewHolder extends RecyclerView.ViewHolder{
        private final NewsAdapterActionListener actionListener;
        private final ImageView ivThumbnail = itemView.findViewById(R.id.ivThumbnailNews);
        private final TextView tvTitle = itemView.findViewById(R.id.tvTitle);
        private final TextView tvDate = itemView.findViewById(R.id.tvDateNews);
        private final TextView tvBody  = itemView.findViewById(R.id.tvBody);
        private final ImageView ivManage = itemView.findViewById(R.id.ivManage);

        public PostViewHolder(@NonNull View itemView, NewsAdapterActionListener actionListener) {
            super(itemView);
            this.actionListener = actionListener;
        }
        public void bindItem(Post post) {
            tvTitle.setText(post.getTitle());
            tvBody.setText(post.getBody());
            tvDate.setText(post.getCreatedAt());
            new BlogServiceGenerator();
            Glide.with(itemView.getContext())
                    .load(post.getThumbnailUrl()
                            .replace("localhost", BlogServiceGenerator.IP))
                    .into(ivThumbnail);

            ivManage.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(View view) {
                    PopupMenu popupMenu = new PopupMenu(ivManage.getContext(), ivManage);
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem Item) {
                            switch (Item.getItemId()) {
                                case R.id.action_edit:
                                    //TODO:: Navigate to Create Delete Activity
                                    actionListener.onClickEdit(post);
                                    return  true;
                                case R.id.action_delete:
                                    //TODO:: SHOW POPUP TO DELETE
                                    actionListener.onClickDelete(post, getAbsoluteAdapterPosition());
                                    return true;
                                default:
                                    return false;
                            }
                        }
                    });

                    //Inflate your menu
                    popupMenu.inflate(R.menu.my_news_list_menu);
                    popupMenu.setGravity(Gravity.RIGHT);

                    popupMenu.show();
                }
            });
        }
    }

    @NonNull
    @Override
    public NewsAdapter.PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_news,parent,false);
        return new PostViewHolder(view, actionListener);
    }
    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.PostViewHolder holder, int position){
    //        Post post = postList.get(position);
    //        holder.bindItem(post);
        holder.bindItem(postList.get(position));
    }
    @Override
    public int getItemCount(){

        return postList.size();
    }
}
