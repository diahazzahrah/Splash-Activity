package com.diah24.splashactivity.db;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.diah24.splashactivity.db.dao.PostDao;
import com.diah24.splashactivity.db.entity.PostEntity;

//nama table, scheme, version (versi database)
@Database(entities = PostEntity.class, exportSchema = false, version = 1)
public abstract class BlogDatabase extends RoomDatabase {
    private static final String DB_NAME = "post_db";
    private static BlogDatabase instance;

    public static synchronized BlogDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    BlogDatabase.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
    //Mengakses dao
    public abstract PostDao postDao();
}
