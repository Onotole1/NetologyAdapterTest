package com.example.adpatertest

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.adpatertest.databinding.ItemPictureBinding
import com.example.adpatertest.databinding.ItemPostBinding
import com.example.adpatertest.ui.Image
import com.example.adpatertest.ui.PostAdapterItem
import com.example.adpatertest.ui.PostUiModel

private val tag = "PostAdapter"

class PostAdapter(
    val removeListener: (PostUiModel) -> Unit
) : ListAdapter<PostAdapterItem, PostAdapterViewHolder>(PostItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostAdapterViewHolder {
        Log.d(tag, "create VH view type: $viewType")

        return when (viewType) {
            R.layout.item_picture -> {
                PostAdapterViewHolder.PostImageViewHolder(
                    ItemPictureBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else -> {
                PostAdapterViewHolder.PostViewHolder(
                    ItemPostBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                ).apply {
                    binding.root.setOnLongClickListener {
                        removeListener(getItem(adapterPosition) as PostUiModel)
                        true
                    }
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int =
        when (getItem(position)) {
            is Image -> R.layout.item_picture
            else -> R.layout.item_post
        }

    override fun onBindViewHolder(holder: PostAdapterViewHolder, position: Int) {
        Log.d(tag, "bind VH position: $position")
        when (holder) {
            is PostAdapterViewHolder.PostImageViewHolder -> holder.bind(
                getItem(position) as Image
            )
            is PostAdapterViewHolder.PostViewHolder -> holder.bind(
                getItem(position) as PostUiModel
            )
        }
    }
}

sealed class PostAdapterViewHolder(root: View) : RecyclerView.ViewHolder(root) {

    class PostViewHolder(
        val binding: ItemPostBinding
    ) : PostAdapterViewHolder(binding.root) {

        fun bind(post: PostUiModel) {
            with(binding) {
                content.text = post.content
                date.text = post.date
            }
        }
    }

    class PostImageViewHolder(
        private val binding: ItemPictureBinding
    ) : PostAdapterViewHolder(binding.root) {

        fun bind(image: Image) {
            binding.root.setImageResource(image.imageRes)
        }
    }
}

class PostItemCallback: DiffUtil.ItemCallback<PostAdapterItem>() {

    override fun areItemsTheSame(oldItem: PostAdapterItem, newItem: PostAdapterItem): Boolean =
        when {
            oldItem is PostUiModel && newItem is PostUiModel -> {
                oldItem.id == newItem.id
            }
            oldItem is Image && newItem is Image -> {
                oldItem.imageRes == newItem.imageRes
            }
            else -> false
        }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: PostAdapterItem, newItem: PostAdapterItem): Boolean =
        when {
            oldItem is PostUiModel && newItem is PostUiModel -> {
                oldItem == newItem
            }
            oldItem is Image && newItem is Image -> {
                oldItem.imageRes == newItem.imageRes
            }
            else -> false
        }
}