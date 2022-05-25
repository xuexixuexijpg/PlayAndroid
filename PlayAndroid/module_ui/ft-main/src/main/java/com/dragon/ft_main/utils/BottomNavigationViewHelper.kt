package com.dragon.ft_main.utils

import android.view.ViewGroup.MarginLayoutParams
import android.widget.ImageView
import coil.load
import com.google.android.material.R
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView

class BottomNavigationViewHelper {
    /**
     * 设置图片尺寸
     *
     * @param view
     * @param width
     * @param height
     */
    fun setImageSize(view: BottomNavigationView, width: Int, height: Int) {
        val menuView = view.getChildAt(0) as BottomNavigationMenuView
        try {
            for (i in 0 until menuView.childCount) {
                val item = menuView.getChildAt(i) as BottomNavigationItemView
                val imageView = item.findViewById<ImageView>(R.id.icon)
                val layoutParams = imageView.layoutParams
                layoutParams.height = width
                layoutParams.width = height
                imageView.layoutParams = layoutParams
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 设置Icon距离顶部高度
     * 不需要了，直接设置design_bottom_navigation_margin属性即可
     *
     * @param view
     * @param marginTop
     */
    fun setImageMarginTop(view: BottomNavigationView, marginTop: Int) {
        val menuView = view.getChildAt(0) as BottomNavigationMenuView
        try {
            for (i in 0 until menuView.childCount) {
                val item = menuView.getChildAt(i) as BottomNavigationItemView
                val imageView = item.findViewById<ImageView>(R.id.icon)
                val layoutParams = imageView.layoutParams as MarginLayoutParams
                layoutParams.topMargin = marginTop
                imageView.layoutParams = layoutParams
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 设置Icon为网络图标
     *
     * @param view
     * @param imgUrl
     */
    fun replaceItemImage(view: BottomNavigationView, imgUrl: Array<String?>) {
        val menuView = view.getChildAt(0) as BottomNavigationMenuView
        try {
            for (i in 0 until menuView.childCount) {
                val item = menuView.getChildAt(i) as BottomNavigationItemView
                val imageView = item.findViewById<ImageView>(R.id.icon)
                imageView.load(imgUrl[i])
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 设置Icon为刷新图标
     *
     * @param view
     * @param gifUrl
     */
    fun replaceRefreshImage(view: BottomNavigationView, index: Int, gifUrl: String?) {
        val menuView = view.getChildAt(0) as BottomNavigationMenuView
        try {
            val item = menuView.getChildAt(index) as BottomNavigationItemView
            val imageView = item.findViewById<ImageView>(R.id.icon)
            imageView.load(gifUrl)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}