package com.dragon.common_ui.widgets.langeview

import androidx.annotation.StringRes
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import java.util.Locale

enum class CusLanguage(val type:String){
    //简中
    Chinese("zh"),
    //英文
    English("en"),
}


//多语言local 不使用LocalConfiguration
val LocalLanguage = compositionLocalOf<CusLanguage> {
    CusLanguage.Chinese
}

@Composable
fun setLanguageByString(language: String):CusLanguage{
    return when(language){
        CusLanguage.Chinese.type -> CusLanguage.Chinese
        CusLanguage.English.type -> CusLanguage.English
        else -> CusLanguage.Chinese
    }
}

@Composable
fun getLangString(@StringRes id: Int): String {
    return if (LocalLanguage.current ==  CusLanguage.Chinese) LocalContext.current.resources.getString(id)
    else LocalContext.current.resources.getString(id)
}
@Composable
fun SetLanguage() {
    val context = LocalContext.current
    val lang = LocalLanguage.current
    val locale = remember(lang) {
        if (lang ==  CusLanguage.Chinese) Locale.CHINESE
        else if (lang ==  CusLanguage.English) Locale.ENGLISH
        else Locale.getDefault()
    }
    if (Locale.getDefault().language != locale.language) {
        Locale.setDefault(locale)
        val config = context.resources.configuration
        config.setLocale(locale)
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }
}

@Composable
fun LangAutoText(
    @StringRes id: Int, modifier: Modifier = Modifier,
    suggestedFontSizes: ImmutableWrapper<List<TextUnit>> = emptyList<TextUnit>().toImmutableWrapper(),
    minTextSize: TextUnit = TextUnit.Unspecified,
    maxTextSize: TextUnit = TextUnit.Unspecified,
    stepGranularityTextSize: TextUnit = TextUnit.Unspecified,
    textAlignment: Alignment = Alignment.Center,
    color: Color = Color.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    softWrap: Boolean = true,
    maxLines: Int = 1,
    minLines: Int = 1,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    style: TextStyle = LocalTextStyle.current,
) {
    AutoSizeText(
        text = getLangString(id = id),
        modifier,
        suggestedFontSizes,
        minTextSize,
        maxTextSize,
        stepGranularityTextSize,
        textAlignment,
        color,
        fontStyle,
        fontWeight,
        fontFamily,
        letterSpacing,
        textDecoration,
        textAlign, lineHeight, softWrap, maxLines, minLines, onTextLayout, style
    )
}

@Composable
fun LangText(
    @StringRes id: Int, modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    style: TextStyle = LocalTextStyle.current
) {
    Text(
        text = getLangString(id = id),
        modifier,
        color,
        fontSize,
        fontStyle,
        fontWeight,
        fontFamily,
        letterSpacing,
        textDecoration,
        textAlign,
        lineHeight,
        overflow, softWrap, maxLines, minLines, onTextLayout, style
    )
}