package com.example.weatherapp.util

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadWithGlide(url: String?) {
    Glide.with(context).load(url).centerCrop().into(this)
}