package com.example.adpatertest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.adpatertest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        binding.root.addItemDecoration(
            DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        )
        setContentView(binding.root)

        val viewModel by viewModels<PostViewModel>()

        val adapter = PostAdapter {
            viewModel.removePostById(it.id)
        }
        binding.root.recycledViewPool.setMaxRecycledViews(R.layout.item_post, 12)
        binding.root.adapter = adapter

        viewModel.content.observe(this) {
            adapter.submitList(it)
        }
    }
}