package com.example.adpatertest.ui

import com.example.adpatertest.domain.Post

data class PostUiModel(
    val id: Long,
    val content: String,
    val date: String,
) : PostAdapterItem {

    companion object {
        fun fromPost(post: Post): PostUiModel = PostUiModel(
            post.id,
            post.content,
            post.date
        )
    }

    fun fromPost(post: Post) = PostUiModel(
        id = id,
        content = content,
        date = date
    )
}
