package com.diah24.splashactivity.db.entity;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

//untuk table mana
@Entity(tableName = "posts")
public class PostEntity {
    //menandai field ini
    @PrimaryKey(autoGenerate = false)
    private int id;
    //namafield
    @ColumnInfo(name = "title")
    //variabel title
    private String title;
    @ColumnInfo(name = "body")
    private String body;
    @ColumnInfo(name = "created_at")
    private String createdAt;
    @ColumnInfo(name = "updated_at")
    private String updatedAt;
    @ColumnInfo(name = "image_path")
    private String imagePath;

    //berguna untuk entitybaru
    public PostEntity(int id, String title, String body, String createdAt, String
            updatedAt, String imagePath) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.imagePath = imagePath;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getBody() {
        return body;
    }
    public void setBody(String body) {
        this.body = body;
    }
    public String getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
    public String getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
    public String getImagePath() {
        return imagePath;
    }
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

}
