package com.dragon.common_ui.widgets.langeview

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.BoxWithConstraintsScope
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.InternalFoundationTextApi
import androidx.compose.foundation.text.TextDelegate
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFontFamilyResolver
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.isUnspecified
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.sp
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.reflect.KProperty

/**
 * Composable function that automatically adjusts the size of text to fit within the given constraints.
 *
 * Features:
 *  1. Best performance: Utilizes a dichotomous binary search algorithm to quickly find the optimal text size without unnecessary iterations.
 *  2. Text alignment support: Supports 6 possible alignment values through the Alignment interface.
 *  3. Material Design 3 support.
 *  4. Font scaling support: Changing the font scale by the user does not affect the visual rendering result.
 *
 * Limitations:
 *  1. Does not work well when maxLine is greater than 1.
 *  2. Does not work well when changing lineHeight.
 *
 * @param text The text to be displayed.
 * @param modifier The modifier for the text composable.
 * @param suggestedFontSizes The suggested font sizes to choose from.
 * @param minTextSize The minimum text size allowed.
 * @param maxTextSize The maximum text size allowed.
 * @param stepGranularityTextSize The step size for adjusting the text size.
 * @param textAlignment The alignment of the text within its container.
 * @param color The color of the text.
 * @param fontStyle The font style of the text.
 * @param fontWeight The font weight of the text.
 * @param fontFamily The font family of the text.
 * @param letterSpacing The letter spacing of the text.
 * @param textDecoration The text decoration style.
 * @param textAlign The alignment of the text within the lines of the paragraph.
 * @param lineHeight The line height of the text.
 * @param softWrap Whether the text should break at soft line breaks.
 * @param maxLines The maximum number of lines for the text.
 * @param minLines The minimum number of lines for the text.
 * @param onTextLayout Callback invoked when the text layout is available.
 * @param style The base style to apply to the text.
 * @author Reda El Madini - For support, contact gladiatorkilo@gmail.com
 */
//TODO 如何自绘制减少重组？
//https://stackoverflow.com/questions/63971569/androidautosizetexttype-in-jetpack-compose
@Composable
fun AutoSizeText(
    text: String,
    modifier: Modifier = Modifier,
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
        text = AnnotatedString(text),
        modifier = modifier,
        suggestedFontSizes = suggestedFontSizes,
        minTextSize = minTextSize,
        maxTextSize = maxTextSize,
        stepGranularityTextSize = stepGranularityTextSize,
        textAlignment = textAlignment,
        color = color,
        fontStyle = fontStyle,
        fontWeight = fontWeight,
        fontFamily = fontFamily,
        letterSpacing = letterSpacing,
        textDecoration = textDecoration,
        textAlign = textAlign,
        lineHeight = lineHeight,
        softWrap = softWrap,
        maxLines = maxLines,
        minLines = minLines,
        onTextLayout = onTextLayout,
        style = style,
    )
}

@Composable
fun AutoSizeText(
    text: AnnotatedString,
    modifier: Modifier = Modifier,
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
    inlineContent: ImmutableWrapper<Map<String, InlineTextContent>> = mapOf<String, InlineTextContent>().toImmutableWrapper(),
    onTextLayout: (TextLayoutResult) -> Unit = {},
    style: TextStyle = LocalTextStyle.current,
) {
    val permittedTextUnitTypes = remember { listOf(TextUnitType.Unspecified, TextUnitType.Sp) }
    check(minTextSize.type in permittedTextUnitTypes)
    check(maxTextSize.type in permittedTextUnitTypes)
    check(stepGranularityTextSize.type in permittedTextUnitTypes)

    val density = LocalDensity.current.density
    // Change font scale to 1
    CompositionLocalProvider(LocalDensity provides Density(density = density, fontScale = 1F)) {
        BoxWithConstraints(
            modifier = modifier,
            contentAlignment = textAlignment,
        ) {
            // (1 / density).sp represents 1px when font scale equals 1
            val step = (1 / density).let {
                if (stepGranularityTextSize.isUnspecified)
                    it.sp
                else
                    stepGranularityTextSize.value.coerceAtLeast(it).sp
            }

            val max = min(maxWidth, maxHeight).value.let {
                if (maxTextSize.isUnspecified)
                    it.sp
                else
                    maxTextSize.value.coerceAtMost(it).sp
            }

            val min =
                if (minTextSize.isUnspecified)
                    step
                else
                    minTextSize.value.coerceIn(
                        minimumValue = step.value,
                        maximumValue = max.value
                    ).sp

            val possibleFontSizes = remember(suggestedFontSizes, min, max, step) {
                suggestedFontSizes.value.filter {
                    it.isSp && it.value in min.value..max.value
                }.sortedByDescending {
                    it.value
                }.ifEmpty {
                    val firstIndex = ceil(min.value / step.value).toInt()
                    val lastIndex = floor(max.value / step.value).toInt()
                    MutableList(size = (lastIndex - firstIndex) + 1) { index ->
                        step * (lastIndex - index)
                    }
                }
            }

            var combinedTextStyle = LocalTextStyle.current + style

            if (possibleFontSizes.isNotEmpty()) {
                // Dichotomous binary search
                var low = 0
                var high = possibleFontSizes.count() - 1
                while (low <= high) {
                    val mid = low + (high - low) / 2
                    val shouldShrink = shouldShrink(
                        text = text,
                        textStyle = combinedTextStyle.copy(fontSize = possibleFontSizes[mid]),
                        maxLines = maxLines,
                        softWrap = softWrap,
                    )

                    if (shouldShrink) low = mid + 1
                    else high = mid - 1
                }
                combinedTextStyle = combinedTextStyle.copy(fontSize = possibleFontSizes[low])
            }

            Text(
                text = text,
                modifier = Modifier,
                color = color,
                fontSize = TextUnit.Unspecified,
                fontStyle = fontStyle,
                fontWeight = fontWeight,
                fontFamily = fontFamily,
                letterSpacing = letterSpacing,
                textDecoration = textDecoration,
                textAlign = textAlign,
                lineHeight = lineHeight,
                overflow = TextOverflow.Clip,
                softWrap = softWrap,
                maxLines = maxLines,
                minLines = minLines,
                inlineContent = inlineContent.value,
                onTextLayout = onTextLayout,
                style = combinedTextStyle,
            )
        }
    }
}

@OptIn(InternalFoundationTextApi::class)
@Composable
private fun BoxWithConstraintsScope.shouldShrink(
    text: AnnotatedString,
    textStyle: TextStyle,
    maxLines: Int,
    softWrap: Boolean,
): Boolean {
    val textDelegate = TextDelegate(
        text = text,
        style = textStyle,
        maxLines = maxLines,
        softWrap = softWrap,
        overflow = TextOverflow.Clip,
        density = LocalDensity.current,
        fontFamilyResolver = LocalFontFamilyResolver.current,
    )

    val textLayoutResult = textDelegate.layout(
        constraints,
        LocalLayoutDirection.current,
    )

    return textLayoutResult.hasVisualOverflow
}

@Immutable
data class ImmutableWrapper<T>(val value: T)

fun <T> T.toImmutableWrapper() = ImmutableWrapper(this)

operator fun <T> ImmutableWrapper<T>.getValue(thisRef: Any?, property: KProperty<*>) = value
