package com.dragon.ft_setting

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Offset
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.unit.dp



@Composable
fun SettingRoute() {
    UnlockAnimation()
}

@Composable
fun UnlockAnimation() {
    // 定义一个可变状态，用于存储是否已经解锁
    var unlocked by remember { mutableStateOf(false) }
    // 定义一个无限重复的过渡动画，用于控制解锁动画的进度
    val transition = rememberInfiniteTransition(label = "")
    // 定义一个动画值，用于表示解锁图标的旋转角度，从 0 到 360 度，每秒一圈
    val angle by transition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = ""
    )
    // 定义一个动画值，用于表示解锁图标的缩放比例，从 1 到 1.2，每秒来回一次
    val scale by transition.animateFloat(
        initialValue = 1f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )
    // 定义一个动画值，用于表示解锁图标的颜色，从灰色到绿色，根据是否已经解锁来切换
    val color by animateColorAsState(
        targetValue = if (unlocked) Color.Green else Color.Gray,
        animationSpec = tween(durationMillis = 500), label = ""
    )
    // 使用 Column 布局来显示解锁图标和按钮
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // 使用 Canvas 组件来绘制解锁图标，根据动画值来设置旋转角度、缩放比例和颜色
        Canvas(modifier = Modifier.size(100.dp)) {
            // 计算圆形的半径和中心点坐标
            val radius = size.minDimension / 4
            val center = Offset(size.width / 2, size.height / 2)
            // 创建一个 Path 对象，用于绘制锁的形状
            val lockPath = Path().apply {
                // 移动到圆形的顶部中心点
                moveTo(center.x, center.y - radius)
                // 向下绘制一条直线到圆形的底部中心点
                lineTo(center.x, center.y + radius)
                // 向左绘制一条直线到圆形的左边缘中点
                lineTo(center.x - radius, center.y + radius)
                // 向上绘制一条直线到圆形的上方一半高度处
                lineTo(center.x - radius, center.y - radius / 2)
                // 向右绘制一条直线到圆形的右边缘中点
                lineTo(center.x + radius, center.y - radius / 2)
                // 向下绘制一条直线到圆形的底部中心点，闭合路径
                lineTo(center.x + radius, center.y + radius)
                close()
            }
            // 根据是否已经解锁来计算锁的偏移量，如果已经解锁，则向上移动半个圆形的高度
            val lockOffset = if (unlocked) -radius else 0f
            // 保存画布的状态
//            save()
            // 根据动画值来旋转和缩放画布
            rotate(angle, pivot = center){}
            scale(scale.dec(), center){}
            // 绘制圆形，使用动画值来设置颜色
            drawCircle(color, radius, center)
            // 绘制锁的形状，使用动画值来设置颜色，并根据偏移量来移动位置
            drawPath(path = lockPath,color = color, style = Stroke(10f), alpha = 0.8f)
            Offset(0f, lockOffset)
            // 恢复画布的状态
//            restore()
        }
        // 使用 Button 组件来显示一个按钮，点击后切换解锁状态，并触发重组和动画
        Button(onClick = { unlocked = !unlocked }) {
            // 使用 Text 组件来显示按钮的文本，根据是否已经解锁来显示不同的文本
            Text(if (unlocked) "Lock" else "Unlock")
        }
    }
}
