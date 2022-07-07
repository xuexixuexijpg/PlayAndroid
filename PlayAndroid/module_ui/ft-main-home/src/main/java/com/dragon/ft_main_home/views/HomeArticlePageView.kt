package com.dragon.ft_main_home.views

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.core.view.marginBottom
import androidx.core.view.marginTop
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.Coil
import coil.ImageLoader
import coil.load
import coil.request.ImageRequest
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.dragon.ft_main_home.R
import com.dragon.ft_main_home.databinding.HomeArticlePageViewBinding
import com.dragon.ft_main_home.databinding.HomeBannerPageViewBinding
import com.dragon.ft_main_home.entity.HomeArticleEntity
import com.dragon.module_base.utils.ext.toHtml
import com.dragon.module_base.utils.ui.ColorUtils
import com.dragon.widgets.shadowLayout.ShadowLayout
import com.google.android.material.card.MaterialCardView

/**
 *
 * 文章UI
 */
@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class HomeArticlePageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding by viewBinding(HomeArticlePageViewBinding::bind)

    init {
        inflate(context, R.layout.home_article_page_view, this)
        isClickable = true
    }

    @ModelProp
    fun setContent(item: HomeArticleEntity?) {
        item?.let {
            with(binding) {
                if (!it.author.isNullOrEmpty()) {
                    homeItemArticleAuthor.text = it.author
                } else {
                    homeItemArticleAuthor.text = it.shareUser
                }
                //是否是新文章
                if (it.fresh == true) {
                    homeItemArticleNewFlag.isVisible
                } else {
                    homeItemArticleNewFlag.isGone
                }
                //二级分类
                if (!it.chapterName.isNullOrEmpty()) {
                    homeItemArticleChaptername.isVisible
                    homeItemArticleChaptername.text = it.chapterName
                    val gradientDrawable = GradientDrawable()
                    val color = ColorUtils.convertToColorInt("55aff4")
                    gradientDrawable.shape = GradientDrawable.RECTANGLE
                    gradientDrawable.setStroke(2,color )
                    ImageLoader(context).enqueue(ImageRequest.Builder(context).data(gradientDrawable).target { drawable ->
                        homeItemArticleChaptername.background = drawable
                    }.build())
                    homeItemArticleChaptername.setTextColor(color)
                } else {
                    homeItemArticleChaptername.isGone
                }
                //时间赋值
                if (!it.niceDate.isNullOrEmpty()) {
                    homeItemArticledata.text = it.niceDate
                }else{
                    homeItemArticledata.text = it.niceShareDate
                }
                //标题
                homeTvArticletitle.text = it.title.toHtml()
                //描述
                if (!it.desc.isNullOrEmpty()) {
                    homeTvArticledesc.isVisible
                    homeTvArticledesc.text = it.desc.toHtml()
                }else{
                    homeTvArticledesc.isGone
                }
                //一级分类
                if (!it.superChapterName.isNullOrEmpty()) {
                    homeTvArticleSuperchaptername.isVisible
                    homeTvArticleSuperchaptername.text = it.superChapterName
                }else{
                    homeTvArticleSuperchaptername.isGone
                }
                if (it.collect){
                    homeIconCollect.load(R.drawable.base_icon_collect){
                        crossfade(true)
                    }
                }else{
                    homeIconCollect.load(R.drawable.base_icon_collect){
                        crossfade(true)
                    }
                }
            }
        }
    }

    @CallbackProp
    fun setHomeArticleClick(listener: OnClickListener?){
        setOnClickListener(listener)
    }
}