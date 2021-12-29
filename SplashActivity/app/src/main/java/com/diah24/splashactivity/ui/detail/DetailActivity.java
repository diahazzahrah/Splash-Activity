package com.diah24.splashactivity.ui.detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.diah24.splashactivity.R;
import com.diah24.splashactivity.api.model.Post;
import com.diah24.splashactivity.db.BlogDatabase;
import com.diah24.splashactivity.db.entity.PostEntity;
import com.diah24.splashactivity.util.AppExecutors;

public class DetailActivity extends AppCompatActivity {

    public static String DATA_EXTRA_KEY ="data_extra_key";
    private Post post;

    private TextView tvTitleNews;
    private TextView tvBody;
    private ImageView ivThumbnailNews;
    private ImageView ivBack;
    private ImageView ivFavorite;
    private TextView tvDate;

    private BlogDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvTitleNews = findViewById(R.id.tvTitleNews);
        tvBody = findViewById(R.id.tvBody);
        ivThumbnailNews = findViewById(R.id.ivThumbnailNews);
        tvDate = findViewById(R.id.tvDateNews);
        ivBack = findViewById(R.id.ivBack);
        ivFavorite = findViewById(R.id.ivFavorite);

        db = BlogDatabase.getInstance(this);

        handleIntent();

        onActionClickListener();

        fetchFavorite();
    }

    private void onActionClickListener() {
        ivFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        int count = db.postDao().checkIfPostIsFavorite(post.getId());
                        if(count >=1){
                            removeFromFavorite();
                        } else {
                            addToFavorite();
                        }
                    }
                });
            }
        });
    }

    private void removeFromFavorite() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                PostEntity postEntity = new PostEntity(
                        post.getId(),
                        post.getTitle(),
                        post.getBody(),
                        post.getCreatedAt(),
                        post.getUpdatedAt(),
                        post.getThumbnailUrl()
                );
                db.postDao().removeFromFavorite(postEntity);
                fetchFavorite();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(DetailActivity.this, "remove from favorite successfully", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
    }

    private void fetchFavorite(){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                int count =  db.postDao().checkIfPostIsFavorite(post.getId());
                if(count>=1){
                    ivFavorite.setImageDrawable(ContextCompat.getDrawable(DetailActivity.
                            this, R.drawable.ic_baseline_favorite_24));
                } else {
                    ivFavorite.setImageDrawable(ContextCompat.getDrawable(DetailActivity.
                            this, R.drawable.ic_baseline_favorite_border_24));
            }
            }
        });
    }

    private void addToFavorite(){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                PostEntity postEntity = new PostEntity(
                  post.getId(),
                  post.getTitle(),
                  post.getBody(),
                  post.getCreatedAt(),
                  post.getUpdatedAt(),
                  post.getThumbnailUrl()
                );
                db.postDao().addToFavorite(postEntity);
                fetchFavorite();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(DetailActivity.this, "add to favorite successfully", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
    }

    private void handleIntent(){
        post = getIntent().getParcelableExtra(DATA_EXTRA_KEY);
        if(post!=null){
            tvTitleNews.setText(post.getTitle());
            tvDate.setText(post.getCreatedAt());
            tvBody.setText(post.getBody());
            Glide.with(this)
                    .load(post.getThumbnailUrl())
                    .into(ivThumbnailNews);
        }
    }
}