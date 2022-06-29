package com.dragon.ft_main_home.adapter

import android.widget.ImageView
import android.widget.TextView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.dragon.ft_main_home.R
import com.dragon.ft_main_home.entity.BannerBean
import com.zhpan.bannerview.BaseBannerAdapter
import com.zhpan.bannerview.BaseViewHolder

class BannerImageAdapter : BaseBannerAdapter<BannerBean>() {

    override fun getLayoutId(viewType: Int) =
        R.layout.home_banner_image_title

    override fun bindData(
        holder: BaseViewHolder<BannerBean>?,
        data: BannerBean?,
        position: Int,
        pageSize: Int
    ) {
        if (data != null && holder!=null){
            holder.findViewById<ImageView>(R.id.image).load(data.imagePath) {
                crossfade(true)
//                transformations(RoundedCornersTransformation(8f))
            }
            holder.findViewById<TextView>(R.id.bannerTitle).text = data.title
        }
    }


}