package com.example.presentation_layer.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.presentation_layer.R

/**
 * This is an extension function to load an image into an [ImageView] passing the image in string format as a parameter
 *
 * @author Julio Navarro Anguita
 * @since 1.0
 */
fun ImageView.setImageFromUrlString(imageStringUrl: String?) {
    imageStringUrl?.let {
        Glide.with(context)
            .load(imageStringUrl)
            .error(R.drawable.error)
            .into(this)
    }
}




