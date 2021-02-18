package com.example.adpatertest.ui

import androidx.annotation.DrawableRes

data class Image(
    @DrawableRes
    val imageRes: Int,
) : PostAdapterItem
