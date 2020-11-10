package com.appbundles.images

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide

class ImageUtility {

    companion object{
        fun loadImage(image:Int,imageView: ImageView){
            Glide.with(imageView.context)
                .load(image)
                .into(imageView)
        }
    }
}