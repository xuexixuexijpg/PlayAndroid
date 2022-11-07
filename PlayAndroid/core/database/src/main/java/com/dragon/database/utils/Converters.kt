package com.dragon.database.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import java.io.ByteArrayOutputStream

/**
 * 图片转换
 */
class DrawableConverter {
    fun drawableToByte(bitmap: Drawable?): ByteArray? {
        bitmap?.let {
            if (it is BitmapDrawable) {
                val stream = ByteArrayOutputStream()
                it.bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                return stream.toByteArray()
            } else {
                return null
            }
        }
        return null
    }

    fun byteArrayToDrawable(byteArray: ByteArray?):Drawable? {
        byteArray?.let{
            val bitmap = BitmapFactory.decodeByteArray(it, 0, it.size)
            return BitmapDrawable(bitmap)
        }
        return null
    }
}