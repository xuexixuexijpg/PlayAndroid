package com.dragon.widgets.shadowLayout


import android.content.Context
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import coil.Coil.imageLoader
import coil.request.ImageRequest
import coil.transform.RoundedCornersTransformation
import com.dragon.widgets.R
import kotlin.math.abs

/**
 * https://github.com/lihangleo2/ShadowLayout
 *
 * 阴影库 使用kotlin重写以及使用  coil来实现圆角
 *
 */

class ShadowLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var clickAbleFalseDrawable: Drawable? = null
    private var clickAbleFalseColor = -101

    private var layoutBackground: Drawable? = null
    private var layoutBackground_true: Drawable? = null
    private var firstView: View? = null

    private var mBackGroundColor = 0
    private var mBackGroundColor_true = -101
    private var mShadowColor = 0
    private var mShadowLimit = 0f
    private var mCornerRadius = 0f
    private var mDx = 0f
    private var mDy = 0f
    private var leftShow = false
    private var rightShow = false
    private var topShow = false
    private var bottomShow = false
    private var shadowPaint: Paint? = null
    private var paint: Paint? = null

    private var leftPadding = 0f
    private var topPadding = 0f
    private var rightPadding = 0f
    private var bottomPadding = 0f

    //阴影布局子空间区域
    private var rectf = RectF()

    //ShadowLayout的样式，是只需要pressed还是selected。默认是pressed.
    private var selectorType = 1
    private var isShowShadow = true
    private var isSym = false

    //增加各个圆角的属性
    private var mCornerRadiusLeftTop = 0f
    private var mCornerRadiusRightTop = 0f
    private var mCornerRadiusLeftBottom = 0f
    private var mCornerRadiusRightBottom = 0f

    //边框画笔
    private var paintStroke: Paint? = null
    private var strokeWith = 0f
    private var stroke_color = 0
    private var stroke_color_true = 0

    private var isClickables = false


    //关于控件填充渐变色
    private var startColor = 0
    private var centerColor = 0
    private var endColor = 0
    private var angle = 0


    //在普遍情况，在点击或选中按钮时，很可能伴随着textView的字体颜色变化
    private var mTextViewResId = -1
    private var mTextView: TextView? = null
    private var textColor = 0
    private var textColor_true = 0
    private var text: String? = null
    private var text_true: String? = null

    init {
        initAttributes(attrs)
        shadowPaint = Paint()
        shadowPaint!!.isAntiAlias = true
        shadowPaint!!.style = Paint.Style.FILL


        paintStroke = Paint()
        paintStroke!!.isAntiAlias = true
        paintStroke!!.style = Paint.Style.STROKE
        paintStroke!!.strokeWidth = strokeWith
        if (stroke_color != -101) {
            paintStroke!!.color = stroke_color
        }

        //矩形画笔
        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint!!.style = Paint.Style.FILL
        //打个标记
        //打个标记
        paint!!.color = mBackGroundColor

        setPadding()
    }

    private fun initAttributes(attrs: AttributeSet?) {
        val attr = context.obtainStyledAttributes(attrs, R.styleable.ShadowLayout)
            ?: return
        try {
            //默认是显示
            isShowShadow = !attr.getBoolean(R.styleable.ShadowLayout_hl_shadowHidden, false)
            leftShow = !attr.getBoolean(R.styleable.ShadowLayout_hl_shadowHiddenLeft, false)
            rightShow = !attr.getBoolean(R.styleable.ShadowLayout_hl_shadowHiddenRight, false)
            bottomShow = !attr.getBoolean(R.styleable.ShadowLayout_hl_shadowHiddenBottom, false)
            topShow = !attr.getBoolean(R.styleable.ShadowLayout_hl_shadowHiddenTop, false)
            mCornerRadius = attr.getDimension(
                R.styleable.ShadowLayout_hl_cornerRadius,
                resources.getDimension(R.dimen.dp_0)
            )
            mCornerRadiusLeftTop =
                attr.getDimension(R.styleable.ShadowLayout_hl_cornerRadius_leftTop, -1f)
            mCornerRadiusLeftBottom =
                attr.getDimension(R.styleable.ShadowLayout_hl_cornerRadius_leftBottom, -1f)
            mCornerRadiusRightTop =
                attr.getDimension(R.styleable.ShadowLayout_hl_cornerRadius_rightTop, -1f)
            mCornerRadiusRightBottom =
                attr.getDimension(R.styleable.ShadowLayout_hl_cornerRadius_rightBottom, -1f)

            //默认扩散区域宽度
            mShadowLimit = attr.getDimension(R.styleable.ShadowLayout_hl_shadowLimit, 0f)
            if (mShadowLimit == 0f) {
                //如果阴影没有设置阴影扩散区域，那么默认隐藏阴影
                isShowShadow = false
            }

            //x轴偏移量
            mDx = attr.getDimension(R.styleable.ShadowLayout_hl_shadowOffsetX, 0f)
            //y轴偏移量
            mDy = attr.getDimension(R.styleable.ShadowLayout_hl_shadowOffsetY, 0f)
            mShadowColor = attr.getColor(
                R.styleable.ShadowLayout_hl_shadowColor,
                resources.getColor(R.color.default_shadow_color)
            )
            selectorType = attr.getInt(R.styleable.ShadowLayout_hl_shapeMode, 1)
            isSym = attr.getBoolean(R.styleable.ShadowLayout_hl_shadowSymmetry, true)

            //背景颜色的点击(默认颜色为白色)
            mBackGroundColor = ContextCompat.getColor(context,R.color.default_shadowback_color)
            val background = attr.getDrawable(R.styleable.ShadowLayout_hl_layoutBackground)
            if (background != null) {
                if (background is ColorDrawable) {
                    mBackGroundColor = background.color
                } else {
                    layoutBackground = background
                }
            }
            val trueBackground = attr.getDrawable(R.styleable.ShadowLayout_hl_layoutBackground_true)
            if (trueBackground != null) {
                if (trueBackground is ColorDrawable) {
                    mBackGroundColor_true = trueBackground.color
                } else {
                    layoutBackground_true = trueBackground
                }
            }
            if (mBackGroundColor_true != -101 && layoutBackground != null) {
                throw UnsupportedOperationException("使用了ShadowLayout_hl_layoutBackground_true属性，必须先设置ShadowLayout_hl_layoutBackground属性。且设置颜色时，必须保持都为颜色")
            }
            if (layoutBackground == null && layoutBackground_true != null) {
                throw UnsupportedOperationException("使用了ShadowLayout_hl_layoutBackground_true属性，必须先设置ShadowLayout_hl_layoutBackground属性。且设置图片时，必须保持都为图片")
            }


            //边框颜色的点击
            stroke_color = attr.getColor(R.styleable.ShadowLayout_hl_strokeColor, -101)
            stroke_color_true = attr.getColor(R.styleable.ShadowLayout_hl_strokeColor_true, -101)
            if (stroke_color == -101 && stroke_color_true != -101) {
                throw UnsupportedOperationException("使用了ShadowLayout_hl_strokeColor_true属性，必须先设置ShadowLayout_hl_strokeColor属性")
            }
            strokeWith =
                attr.getDimension(R.styleable.ShadowLayout_hl_strokeWith, dip2px(1f).toFloat())
            //规定边框长度最大不错过7dp
            if (strokeWith > dip2px(7f)) {
                strokeWith = dip2px(5f).toFloat()
            }
            val clickAbleFalseBackground =
                attr.getDrawable(R.styleable.ShadowLayout_hl_layoutBackground_clickFalse)
            if (clickAbleFalseBackground != null) {
                if (clickAbleFalseBackground is ColorDrawable) {
                    clickAbleFalseColor = clickAbleFalseBackground.color
                } else {
                    clickAbleFalseDrawable = clickAbleFalseBackground
                }
            }
            startColor = attr.getColor(R.styleable.ShadowLayout_hl_startColor, -101)
            centerColor = attr.getColor(R.styleable.ShadowLayout_hl_centerColor, -101)
            endColor = attr.getColor(R.styleable.ShadowLayout_hl_endColor, -101)
            if (startColor != -101) {
                //说明设置了渐变色的起始色
                if (endColor == -101) {
                    throw UnsupportedOperationException("使用了ShadowLayout_hl_startColor渐变起始色，必须搭配终止色ShadowLayout_hl_endColor")
                }
            }
            angle = attr.getInt(R.styleable.ShadowLayout_hl_angle, 0)
            require(angle % 45 == 0) { "Linear gradient requires 'angle' attribute to be a multiple of 45" }
            if (selectorType == 3) {
                //如果是ripple的话
                if (mBackGroundColor == -101 || mBackGroundColor_true == -101) {
                    throw NullPointerException("使用了ShadowLayout的水波纹，必须设置使用了ShadowLayout_hl_layoutBackground和使用了ShadowLayout_hl_layoutBackground_true属性，且为颜色值")
                }

                //如果是设置了图片的话，那么也不支持水波纹
                if (layoutBackground != null) {
                    selectorType = 1
                }
            }
            mTextViewResId = attr.getResourceId(R.styleable.ShadowLayout_hl_bindTextView, -1)
            textColor = attr.getColor(R.styleable.ShadowLayout_hl_textColor, -101)
            textColor_true = attr.getColor(R.styleable.ShadowLayout_hl_textColor_true, -101)
            text = attr.getString(R.styleable.ShadowLayout_hl_text)
            text_true = attr.getString(R.styleable.ShadowLayout_hl_text_true)
            isClickables = attr.getBoolean(R.styleable.ShadowLayout_clickable, true)
            setClickable(isClickables)
        } finally {
            attr.recycle()
        }
    }

    fun dip2px(dipValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dipValue * scale + 0.5f).toInt()
    }

    fun setPadding() {
        if (isShowShadow && mShadowLimit > 0) {
            //控件区域是否对称，默认是对称。不对称的话，那么控件区域随着阴影区域走
            if (isSym) {
                val xPadding = (mShadowLimit + abs(mDx)).toInt()
                val yPadding = (mShadowLimit + abs(mDy)).toInt()
                leftPadding = if (leftShow) {
                    xPadding.toFloat()
                } else {
                    0F
                }
                topPadding = if (topShow) {
                    yPadding.toFloat()
                } else {
                    0F
                }
                rightPadding = if (rightShow) {
                    xPadding.toFloat()
                } else {
                    0F
                }
                bottomPadding = if (bottomShow) {
                    yPadding.toFloat()
                } else {
                    0F
                }
            } else {
                if (abs(mDy) > mShadowLimit) {
                    mDy = if (mDy > 0) {
                        mShadowLimit
                    } else {
                        0 - mShadowLimit
                    }
                }
                if (abs(mDx) > mShadowLimit) {
                    mDx = if (mDx > 0) {
                        mShadowLimit
                    } else {
                        0 - mShadowLimit
                    }
                }
                topPadding = if (topShow) {
                    (mShadowLimit - mDy).toInt().toFloat()
                } else {
                    0F
                }
                bottomPadding = if (bottomShow) {
                    (mShadowLimit + mDy).toInt().toFloat()
                } else {
                    0F
                }
                rightPadding = if (rightShow) {
                    (mShadowLimit - mDx).toInt().toFloat()
                } else {
                    0F
                }
                leftPadding = if (leftShow) {
                    (mShadowLimit + mDx).toInt().toFloat()
                } else {
                    0F
                }
            }
            setPadding(leftPadding.toInt(), topPadding.toInt(), rightPadding.toInt(),
                bottomPadding.toInt()
            )
        }
    }

    override fun setClickable(clickable: Boolean) {
        super.setClickable(clickable)
        isClickables = clickable
        changeSwitchClickable()
        if (isClickables) {
            super.setOnClickListener(onClickListener)
        }

        if (paint != null) {
            if (startColor != -101 && endColor != -101) {
                gradient(paint!!)
            }
        }
    }

    //解决xml设置clickable = false时。代码设置true时，点击事件无效的bug
    private var onClickListener: OnClickListener? = null
    override fun setOnClickListener(l: OnClickListener?) {
        this.onClickListener = l
        if (isClickables) {
            super.setOnClickListener(l)
        }
    }

    fun changeSwitchClickable() {
        //不可点击的状态只在press mode的模式下生效
        if (selectorType == 1 && firstView != null) {

            //press mode
            if (!isClickables) {
                //不可点击的状态。
                if (clickAbleFalseColor != -101) {
                    //说明设置了颜色
                    if (layoutBackground != null) {
                        //说明此时是设置了图片的模式
                        firstView!!.background.alpha = 0
                    }
                    paint!!.color = clickAbleFalseColor
                    postInvalidate()
                } else if (clickAbleFalseDrawable != null) {
                    //说明设置了背景图
                    setmBackGround(clickAbleFalseDrawable, "changeSwitchClickable")
                    paint!!.color = Color.parseColor("#00000000")
                    postInvalidate()
                }
            } else {
                //可点击的状态
                if (layoutBackground != null) {
                    setmBackGround(layoutBackground, "changeSwitchClickable")
                } else {
                    if (firstView!!.background != null) {
                        firstView!!.background.alpha = 0
                    }
                }
                paint!!.color = mBackGroundColor
                postInvalidate()
            }
        }
    }

    //将画笔附上 渐变色
    fun gradient(paint: Paint) {
        if (!isClickables) {
            paint.shader = null
            return
        }
        //左上 x,y   leftPadding, topPadding,
        //右下 x,y   getWidth() - rightPadding, getHeight() - bottomPadding
        val colors: IntArray = if (centerColor == -101) {
            intArrayOf(startColor, endColor)
        } else {
            intArrayOf(startColor, centerColor, endColor)
        }
        if (angle < 0) {
            val trueAngle = angle % 360
            angle = trueAngle + 360
        }
        //当设置的角度大于0的时候
        //这个要算出每隔45度
        val trueAngle = angle % 360
        val angleFlag = trueAngle / 45
        var linearGradient: LinearGradient? = null
        when (angleFlag) {
            0 -> {
                linearGradient = LinearGradient(
                    leftPadding,
                    topPadding,
                    width - rightPadding,
                    topPadding,
                    colors,
                    null,
                    Shader.TileMode.CLAMP
                )
                paint.shader = linearGradient
            }
            1 -> {
                linearGradient = LinearGradient(
                    leftPadding,
                    height - bottomPadding,
                    width - rightPadding,
                    topPadding,
                    colors,
                    null,
                    Shader.TileMode.CLAMP
                )
                paint.shader = linearGradient
            }
            2 -> {
                val x = (width - rightPadding - leftPadding) / 2 + leftPadding
                linearGradient = LinearGradient(
                    x,
                    height - bottomPadding,
                    x,
                    topPadding,
                    colors,
                    null,
                    Shader.TileMode.CLAMP
                )
                paint.shader = linearGradient
            }
            3 -> {
                linearGradient = LinearGradient(
                    width - rightPadding,
                    height - bottomPadding,
                    leftPadding,
                    topPadding,
                    colors,
                    null,
                    Shader.TileMode.CLAMP
                )
                paint.shader = linearGradient
            }
            4 -> {
                linearGradient = LinearGradient(
                    width - rightPadding,
                    topPadding,
                    leftPadding,
                    topPadding,
                    colors,
                    null,
                    Shader.TileMode.CLAMP
                )
                paint.shader = linearGradient
            }
            5 -> {
                linearGradient = LinearGradient(
                    width - rightPadding,
                    topPadding,
                    leftPadding,
                    height - bottomPadding,
                    colors,
                    null,
                    Shader.TileMode.CLAMP
                )
                paint.shader = linearGradient
            }
            6 -> {
                val x = (width - rightPadding - leftPadding) / 2 + leftPadding
                linearGradient = LinearGradient(
                    x,
                    topPadding,
                    x,
                    height - bottomPadding,
                    colors,
                    null,
                    Shader.TileMode.CLAMP
                )
                paint.shader = linearGradient
            }
            7 -> {
                linearGradient = LinearGradient(
                    leftPadding,
                    topPadding,
                    width - rightPadding,
                    height - bottomPadding,
                    colors,
                    null,
                    Shader.TileMode.CLAMP
                )
                paint.shader = linearGradient
            }
        }
    }

    /**
     * 利用coil来裁切drawbel
     *
     */
    fun setmBackGround(drawable: Drawable?, currentTag: String) {
        Log.e("测试", "setmBackGround:阴影库的 ${imageLoader(context)}")
        firstView!!.setTag(R.id.action_container, currentTag)
        if (firstView != null && drawable != null) {
            if (mCornerRadiusLeftTop == -1f && mCornerRadiusLeftBottom == -1f && mCornerRadiusRightTop == -1f && mCornerRadiusRightBottom == -1f) {
                val request = ImageRequest.Builder(context)
                    .data(drawable)
                    .transformations(RoundedCornersTransformation(mCornerRadius))
                    .target{
                        val lastTag = firstView!!.getTag(R.id.action_container) as String
                        if (lastTag == currentTag) {
                            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) {
                                firstView!!.setBackgroundDrawable(drawable)
                            } else {
                                firstView!!.background = drawable
                            }
                        }
                    }
                    .build()
                imageLoader(context).enqueue(request)
            } else {
                val leftTop: Int = if (mCornerRadiusLeftTop == -1f) {
                    mCornerRadius.toInt()
                } else {
                    mCornerRadiusLeftTop.toInt()
                }
                val leftBottom: Int = if (mCornerRadiusLeftBottom == -1f) {
                    mCornerRadius.toInt()
                } else {
                    mCornerRadiusLeftBottom.toInt()
                }
                val rightTop: Int = if (mCornerRadiusRightTop == -1f) {
                    mCornerRadius.toInt()
                } else {
                    mCornerRadiusRightTop.toInt()
                }
                val rightBottom: Int = if (mCornerRadiusRightBottom == -1f) {
                    mCornerRadius.toInt()
                } else {
                    mCornerRadiusRightBottom.toInt()
                }
                val request = ImageRequest.Builder(context)
                    .data(drawable)
                    .transformations(
                        RoundedCornersTransformation(
                            leftTop.toFloat(),
                            leftBottom.toFloat(), rightTop.toFloat(), rightBottom.toFloat()
                        )
                    )
                    .target{
                        val lastTag = firstView!!.getTag(R.id.action_container) as String
                        if (lastTag == currentTag) {
                            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) {
                                firstView!!.setBackgroundDrawable(drawable)
                            } else {
                                firstView!!.background = drawable
                            }
                        }
                    }
                    .build()
                imageLoader(context).enqueue(request)
            }
        }
    }
}