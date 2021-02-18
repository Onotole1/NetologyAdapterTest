package com.example.adpatertest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.adpatertest.domain.Post
import com.example.adpatertest.ui.Image
import com.example.adpatertest.ui.PostAdapterItem
import com.example.adpatertest.ui.PostUiModel
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

private val formatter = DateTimeFormatter.ofPattern("HH:mm : dd MM yyyy")

private val posts = List(10) {
    val currentDate = Instant.now().atZone(ZoneId.systemDefault())
    Post(it.toLong(), "Test content $it", formatter.format(currentDate))
}.map(PostUiModel.Companion::fromPost) + List(10) {
    if (it % 2 == 0) {
        Image(R.drawable.ic_baseline_bolt_24)
    } else {
        Image(R.drawable.ic_android_black_24dp)
    }
} + List(10) {
    val currentDate = Instant.now().atZone(ZoneId.systemDefault())
    Post(it.toLong(), "Test content $it", formatter.format(currentDate))
}.map(PostUiModel.Companion::fromPost)

class PostViewModel : ViewModel() {

    private val _content = MutableLiveData(posts)
    val content: LiveData<List<PostAdapterItem>>
        get() = _content

    fun removePostById(postId: Long) {
        _content.value = _content.value?.filterNot {
            if (it !is PostUiModel) {
                return@filterNot false
            }

            it.id == postId
        }
    }
}
