package com.diah24.splashactivity.db.dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.diah24.splashactivity.db.entity.PostEntity;

import java.util.List;

@Dao
public interface PostDao {

    @Query("SELECT * FROM posts")
    List<PostEntity> getFavoritePost();

    @Insert
    void addToFavorite(PostEntity postEntity);

    @Delete
    void removeFromFavorite(PostEntity postEntity);

    @Query("SELECT COUNT(id) FROM posts WHERE id=:postId")
    int checkIfPostIsFavorite(int postId);

}
