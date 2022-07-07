package com.dragon.common_imageloading

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import coil.ImageLoader
import coil.imageLoader
import coil.load
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation

fun ImageView.loadCircleCrop(data: Any, placeholderId: Int = 0, errorId: Int = 0) {
    this.load(data) {
        crossfade(true)
        placeholder(placeholderId)
        error(errorId)
        transformations(CircleCropTransformation())
    }
}

fun ImageView.loadRoundedCorners(data: Any, radius: Float) {
    this.loadRoundedCorners(data, radius, radius, radius, radius, 0, 0)
}

fun ImageView.loadRoundedCorners(
    data: Any,
    topLeft: Float,
    topRight: Float,
    bottomLeft: Float,
    bottomRight: Float
) {
    this.loadRoundedCorners(data, topLeft, topRight, bottomLeft, bottomRight, 0, 0)
}

fun ImageView.loadRoundedCorners(
    data: Any,
    topLeft: Float,
    topRight: Float,
    bottomLeft: Float,
    bottomRight: Float,
    placeholderId: Int = 0,
    errorId: Int = 0
) {
    this.load(data) {
        crossfade(true)
        placeholder(placeholderId)
        error(errorId)
        transformations(
            RoundedCornersTransformation(
                topLeft,
                topRight,
                bottomLeft,
                bottomRight
            )
        )
    }
}

fun Context.imageDownload(
    data: Any,
    onSuccess: (result: Drawable) -> Unit = {},
    onStart: (placeholder: Drawable?) -> Unit = {},
    onError: (error: Drawable?) -> Unit = {},
    imageLoader: ImageLoader = this.imageLoader,
    builder: ImageRequest.Builder.() -> Unit = {}
){
    val request = ImageRequest.Builder(this)
        .data(data)
        .target(onStart, onError, onSuccess)
        .apply(builder)
        .build()
    imageLoader.enqueue(request)
}