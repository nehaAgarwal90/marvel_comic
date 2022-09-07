package com.example.marvel_comic.utils

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object AppBindingAdapter {
    @JvmStatic
    @BindingAdapter("image_from_url")
    fun AppCompatImageView.imageFromUrl(url: String) {
        Glide.with(this.context).load(url).into(this)
    }
}