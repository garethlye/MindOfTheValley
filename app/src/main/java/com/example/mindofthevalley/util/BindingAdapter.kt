package com.example.mindofthevalley.util

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.mindofthevalley.R

@SuppressLint("CheckResult")
@BindingAdapter(value = ["imageUrl", "default", "roundAsCircle", "centerCrop", "roundedCorner"], requireAll = false)
fun setImageViewUrl(
    imageView: ImageView,
    url: String?,
    drawable: Drawable?,
    roundAsCircle: Boolean?,
    centerCrop: Boolean?,
    roundedCorner: Boolean?
) {
    var requestBuilder = Glide.with(imageView.context)
        .asBitmap()
        .load(url)
        .placeholder(R.drawable.icon_default)
    if(NetworkUtils.isNetworkConnected(imageView.context)) {
        //cache only when internet is available
        requestBuilder
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
    } else {
        //use cache when internet is not available
        requestBuilder
            .onlyRetrieveFromCache(true)
    }
    if(centerCrop == true && roundedCorner == true) {
        requestBuilder = requestBuilder.transform(MultiTransformation(CenterCrop(), RoundedCorners(20)))
    } else {
        centerCrop?.let {
            if (it) requestBuilder = requestBuilder.apply(RequestOptions.centerCropTransform())
        }
        roundAsCircle?.let {
            if (it) requestBuilder = requestBuilder.apply(RequestOptions.circleCropTransform())
        }
        roundedCorner?.let {
            if (it) requestBuilder =
                requestBuilder.apply(RequestOptions().transform(RoundedCorners(20)))
        }
    }
    requestBuilder.into(imageView)
}

@BindingAdapter("android:visibility")
fun setVisibility(view: View, value: Boolean?) {
    value?.let {
        view.visibility = if (it) View.VISIBLE else View.GONE
    }
}

@SuppressLint("CheckResult")
@BindingAdapter("loadGif")
fun setImageViewGif(
    imageView: ImageView,
    resource: Int
) {
    Glide.with(imageView.context)
        .load(resource)
        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        .into(imageView)
}
