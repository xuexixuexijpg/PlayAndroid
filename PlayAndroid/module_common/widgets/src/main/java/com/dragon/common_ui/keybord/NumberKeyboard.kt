package com.dragon.common_ui.keybord
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension

/**
 * 数字输入 lds TODO 使用TextFieldValue做选中操作 有无更简便方法
 * 自行抽取自定义参数
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NumberKeyboardWithOneEdit(
    modifier: Modifier = Modifier,
    textField: MutableState<TextFieldValue>? = null,//单状态
    itemSize: Modifier = Modifier.size(95.dp, 70.dp),
    isNumber: Boolean = true,
    isDecimal: Boolean = false,
    maxLength: Int = 5,
    onInput: ((value: String) -> Unit)? = null,
    onDelete: (() -> Unit)? = null,
    onLongDelete: (() -> Unit)? = null,
    keyShadow: Int = 1,
) {
    val green = Color(0xFF40B8A5)
    val keyBtnColor = Color(0xFFE6F0EF)
    val keyBtnShape = RoundedCornerShape(12.dp)
    val keys = listOf("1", "2", "3", "-", "4", "5", "6", "0", "7", "8", "9", ".")
    ConstraintLayout(modifier = modifier) {
        val (listNum, deleteBtn) = createRefs()
        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            modifier = Modifier
                .constrainAs(listNum) {
                    linkTo(parent.start, parent.top, deleteBtn.start, parent.bottom)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterHorizontally),
            verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically),
            userScrollEnabled = false
        ) {
            items(items = keys, key = { it }) { value ->
                val alpha = if (value == "-") {
                    if (isNumber) 0.2F
                    else 1F
                } else if (value == ".") {
                    if (isDecimal) 1F
                    else 0.2F
                } else 1F
                val enableClick = when (value) {
                    "-" -> {
                        !isNumber
                    }
                    "." -> {
                        isDecimal
                    }
                    else -> true
                }
                Box(
                    modifier = itemSize
                        .shadow(keyShadow.dp, keyBtnShape, clip = true)
                        .background(keyBtnColor, keyBtnShape)
                        .alpha(alpha)
                        .clickable(enabled = enableClick) {
                            if (onInput == null) {
                                //输入
                                if (textField != null) {
                                    if (textField.value.text.length >= maxLength) {
                                        return@clickable
                                    }
                                    val selection = textField.value.selection
                                    if (selection.collapsed || selection.length > 0) {
                                        val text = textField.value.text.replaceRange(
                                            selection.start,
                                            selection.end,
                                            value
                                        )
                                        val newSelection = TextRange(selection.start + 1)
                                        textField.value = TextFieldValue(text, newSelection)
                                    }
                                }
                            } else
                                onInput.invoke(value)
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = value,
                        fontSize = 36.sp,
                        color = if (value != "-" && value != ".") Color.Blue
                        else green
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .width(90.dp)
                .constrainAs(deleteBtn) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                    height = Dimension.fillToConstraints
                }
                .shadow(keyShadow.dp, keyBtnShape, clip = true)
                .background(keyBtnColor, keyBtnShape)
                .combinedClickable(onLongClick = {
                    if (onLongDelete == null) {
                        //长按删除
                        if (textField != null) {
                            textField.value = TextFieldValue("", TextRange(0))
                        }
                    } else
                        onLongDelete.invoke()
                }) {
                    if (onDelete == null) {
                        //删除
                        if (textField != null) {
                            val selection = textField.value.selection
                            if (selection.collapsed && selection.start > 0) {
                                //删除单个
                                val text = textField.value.text.removeRange(
                                    selection.start - 1,
                                    selection.start
                                )
                                val newSelection = TextRange(selection.start - 1)
                                textField.value = TextFieldValue(text, newSelection)
                            } else if (selection.length > 0) {
                                //删除选中的
                                val text = textField.value.text.removeRange(
                                    selection.start,
                                    selection.end
                                )
                                val newSelection = TextRange(textField.value.text.length - 1)
                                textField.value = TextFieldValue(text, newSelection)
                            }
                        }
                    } else
                        onDelete.invoke()
                },
            contentAlignment = Alignment.Center
        ) {
//            Icon(
//                painter = Icons.Outlined,
//                contentDescription = null,
//            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NumberKeyboard(
    modifier: Modifier = Modifier,
    textField: MutableState<MutableState<TextFieldValue>>? = null,//引用的引用，多状态值
    itemSize: Modifier = Modifier.size(95.dp, 70.dp),
    isNumber: Boolean = true,
    isDecimal: Boolean = false,
    maxLength: Int = 5,
    onInput: ((value: String) -> Unit)? = null,
    onDelete: (() -> Unit)? = null,
    onLongDelete: (() -> Unit)? = null,
    keyShadow: Int = 1,
) {
    val green = Color(0xFF40B8A5)
    val keyBtnColor = Color(0xFFE6F0EF)
    val keyBtnShape = RoundedCornerShape(12.dp)
    val keys = listOf("1", "2", "3", "-", "4", "5", "6", "0", "7", "8", "9", ".")
    ConstraintLayout(modifier = modifier) {
        val (listNum, deleteBtn) = createRefs()
        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            modifier = Modifier
                .constrainAs(listNum) {
                    linkTo(parent.start, parent.top, deleteBtn.start, parent.bottom)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterHorizontally),
            verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically),
            userScrollEnabled = false
        ) {
            items(items = keys, key = { it }) { value ->
                val alpha = if (value == "-") {
                    if (isNumber) 0.2F
                    else 1F
                } else if (value == ".") {
                    if (isDecimal) 1F
                    else 0.2F
                } else 1F
                val enableClick = when (value) {
                    "-" -> {
                        !isNumber
                    }
                    "." -> {
                        isDecimal
                    }
                    else -> true
                }
                Box(
                    modifier = itemSize
                        .shadow(keyShadow.dp, keyBtnShape, clip = true)
                        .background(keyBtnColor, keyBtnShape)
                        .alpha(alpha)
                        .clickable(enabled = enableClick) {
                            if (onInput == null) {
                                //输入
                                if (textField != null) {
                                    if (textField.value.value.text.length > maxLength) {
                                        return@clickable
                                    }
                                    val selection = textField.value.value.selection
                                    if (selection.collapsed || selection.length > 0) {
                                        val text = textField.value.value.text.replaceRange(
                                            selection.start,
                                            selection.end,
                                            value
                                        )
                                        val newSelection = TextRange(selection.start + 1)
                                        textField.value.value = TextFieldValue(text, newSelection)
                                    }
                                }
                            } else
                                onInput.invoke(value)
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = value,
                        fontSize = 36.sp,
                        color = if (value != "-" && value != ".") Color.Blue
                        else green
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .width(90.dp)
                .constrainAs(deleteBtn) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                    height = Dimension.fillToConstraints
                }
                .shadow(keyShadow.dp, keyBtnShape, clip = true)
                .background(keyBtnColor, keyBtnShape)
                .combinedClickable(onLongClick = {
                    if (onLongDelete == null) {
                        //长按删除
                        if (textField != null) {
                            textField.value.value = TextFieldValue("", TextRange(0))
                        }
                    } else
                        onLongDelete.invoke()
                }) {
                    if (onDelete == null) {
                        //删除
                        if (textField != null) {
                            val selection = textField.value.value.selection
                            if (selection.collapsed && selection.start > 0) {
                                //删除单个
                                val text = textField.value.value.text.removeRange(
                                    selection.start - 1,
                                    selection.start
                                )
                                val newSelection = TextRange(selection.start - 1)
                                textField.value.value = TextFieldValue(text, newSelection)
                            } else if (selection.length > 0) {
                                //删除选中的
                                val text = textField.value.value.text.removeRange(
                                    selection.start,
                                    selection.end
                                )
                                val newSelection = TextRange(textField.value.value.text.length - 1)
                                textField.value.value = TextFieldValue(text, newSelection)
                            }
                        }
                    } else
                        onDelete.invoke()
                },
            contentAlignment = Alignment.Center
        ) {
//            Icon(
//                painter = painterResource(id = R.drawable.vector_backspace_green),
//                contentDescription = null,
//            )
        }
    }
}

