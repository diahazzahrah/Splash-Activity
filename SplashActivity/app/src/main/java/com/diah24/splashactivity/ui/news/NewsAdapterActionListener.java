package com.diah24.splashactivity.ui.news;

import com.diah24.splashactivity.api.model.Post;

public interface NewsAdapterActionListener {
    void onClickDelete(Post post, int absoluteAdapterPosition);
    void onClickEdit(Post post); 
}
