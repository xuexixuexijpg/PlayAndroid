package com.dragon.ft_main_home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.dragon.ft_main_home.R
import com.dragon.ft_main_home.entity.BannerBean
import com.youth.banner.adapter.BannerAdapter
import com.youth.banner.util.BannerUtils

class ImageAdapter(@Nullable imageUrls: List<BannerBean>?) :
    BannerAdapter<BannerBean, ImageAdapter.ImageHolder>(imageUrls) {


    override fun onCreateHolder(parent: ViewGroup?, viewType: Int): ImageHolder {
        return ImageHolder(
            LayoutInflater.from(parent?.context)
                .inflate(R.layout.home_banner_image_title, parent, false)
        )
    }

    override fun onBindView(holder: ImageHolder, data: BannerBean, position: Int, size: Int) {
        holder.imageView.load(data.imagePath) {
            crossfade(true)
        }
        holder.title.text = data.title
    }


    inner class ImageHolder(view: View) : RecyclerView.ViewHolder(view) {
        var imageView: ImageView = view.findViewById(R.id.image)
        val title: TextView = view.findViewById(R.id.bannerTitle)
    }

}

