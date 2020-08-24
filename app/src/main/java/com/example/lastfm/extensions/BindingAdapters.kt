package com.example.lastfm.extensions

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso


object BindingAdapters {
    @BindingAdapter("imageUrl")
    @JvmStatic
    fun setImageUrl(view: ImageView, path: String) {
        if (path != "") Picasso.get().load(path).into(view)
    }
}
