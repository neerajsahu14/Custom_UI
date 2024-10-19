package com.example.customui
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

@Composable
fun CustomComponent(
    canvasSize: Dp = 300.dp,
    indicatorValue: Int = 0,
    maxIndicatorValue: Int = 100,
    backgroundIndicatorColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
    backgroundIndicatorStrokeWidth: Float = 100f,
    foregroundIndicatorColor: Color = MaterialTheme.colorScheme.primary,
    foregroundIndicatorStrokeWidth: Float = 100f,
    bigTextFontSize: TextUnit = MaterialTheme.typography.displaySmall.fontSize,
    bigTextColor: Color = MaterialTheme.colorScheme.onSurface,
    bigTextSuffix: String = "GB",
    smallText: String = "Remaining",
    smallTextFontSize: TextUnit = MaterialTheme.typography.headlineSmall.fontSize,
    smallTextColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
) {
    var allowedIndicatorValue by remember {
        mutableStateOf(maxIndicatorValue)
    }
    allowedIndicatorValue = if (indicatorValue <= maxIndicatorValue) {
        indicatorValue
    } else {
        maxIndicatorValue
    }

    var animatedIndicatorValue by remember { mutableStateOf(0f) }
    LaunchedEffect(key1 = allowedIndicatorValue) {
        animatedIndicatorValue = allowedIndicatorValue.toFloat()
    }

    val percentage =
        (animatedIndicatorValue / maxIndicatorValue) * 100

    val sweepAngle by animateFloatAsState(
        targetValue = (2.4 * percentage).toFloat(),
        animationSpec = tween(1000)
    )

    val receivedValue by animateIntAsState(
        targetValue = allowedIndicatorValue,
        animationSpec = tween(1000)
    )

    val animatedBigTextColor by animateColorAsState(
        targetValue = if (allowedIndicatorValue == 0)
            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
        else
            bigTextColor,
        animationSpec = tween(1000)
    )

    Column(
        modifier = Modifier
            .size(canvasSize)
            .drawBehind {
                val componentSize = size / 1.25f
                backgroundIndicator(
                    componentSize = componentSize,
                    indicatorColor = backgroundIndicatorColor,
                    indicatorStrokeWidth = backgroundIndicatorStrokeWidth
                )
                foregroundIndicator(
                    sweepAngle = sweepAngle,
                    componentSize = componentSize,
                    indicatorColor = foregroundIndicatorColor,
                    indicatorStrokeWidth = foregroundIndicatorStrokeWidth
                )
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EmbeddedElements(
            bigText = receivedValue,
            bigTextFontSize = bigTextFontSize,
            bigTextColor = animatedBigTextColor,
            bigTextSuffix = bigTextSuffix,
            smallText = smallText,
            smallTextColor = smallTextColor,
            smallTextFontSize = smallTextFontSize
        )
    }
}

fun DrawScope.backgroundIndicator(
    componentSize: Size,
    indicatorColor: Color,
    indicatorStrokeWidth: Float
) {
    drawArc(
        size = componentSize,
        color = indicatorColor,
        startAngle = 150f,
        sweepAngle = 240f,
        useCenter = false,
        style = Stroke(
            width = indicatorStrokeWidth,
            cap = StrokeCap.Round
        ),
        topLeft = Offset(
            x = (size.width - componentSize.width) / 2f,
            y = (size.height - componentSize.height) / 2f
        )
    )
}

fun DrawScope.foregroundIndicator(
    sweepAngle: Float,
    componentSize: Size,
    indicatorColor: Color,
    indicatorStrokeWidth: Float
) {
    drawArc(
        size = componentSize,
        color = indicatorColor,
        startAngle = 150f,
        sweepAngle = sweepAngle,
        useCenter = false,
        style = Stroke(
            width = indicatorStrokeWidth,
            cap = StrokeCap.Round
        ),
        topLeft = Offset(
            x = (size.width - componentSize.width) / 2f,
            y = (size.height - componentSize.height) / 2f
        )
    )
}

@Composable
fun EmbeddedElements(
    bigText: Int,
    bigTextFontSize: TextUnit,
    bigTextColor: Color,
    bigTextSuffix: String,
    smallText: String,
    smallTextColor: Color,
    smallTextFontSize: TextUnit
) {
    Text(
        text = smallText,
        color = smallTextColor,
        fontSize = smallTextFontSize,
        textAlign = TextAlign.Center
    )
    Text(
        text = "$bigText ${bigTextSuffix.take(2)}",
        color = bigTextColor,
        fontSize = bigTextFontSize,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold
    )
}

@Composable
@Preview(showBackground = true)
fun CustomComponentPreview() {
    CustomComponent()
}
//
//import androidx.compose.animation.Animatable
//import androidx.compose.animation.animateColorAsState
//import androidx.compose.animation.core.animateFloatAsState
//import androidx.compose.animation.core.animateIntAsState
//import androidx.compose.animation.core.tween
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.*
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.setValue
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.drawBehind
//import androidx.compose.ui.geometry.Offset
//import androidx.compose.ui.geometry.Size
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.StrokeCap
//import androidx.compose.ui.graphics.drawscope.DrawScope
//import androidx.compose.ui.graphics.drawscope.Stroke
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.Dp
//import androidx.compose.ui.unit.TextUnit
//import androidx.compose.ui.unit.dp
//
//
//@Composable
//fun CustomComponent (
//    canvasSize: Dp = 300.dp,
//    indicatorValue: Int = 0,
//    maxIndicatorValue: Int = 100,
//    backgroundColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
//    backgroundIndicatorStrokeWidth: Float = 100f,
//    foregroundIndicatorStrokeWidth: Float = 100f,
//    foregroundIndicatorColor: Color = MaterialTheme.colorScheme.primary,
//    bigTextSize: TextUnit = MaterialTheme.typography.displaySmall.fontSize,
//    bigTextColor: Color = MaterialTheme.colorScheme.onSurface,
//    bigTextSuffix: String = "GB",
//    smallText: String = "Remaining",
//    smallTextColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
//    smallTextSize: TextUnit = MaterialTheme.typography.headlineSmall.fontSize
//
//) {
//    var allowedIndicatorValue by remember {
//        mutableStateOf(maxIndicatorValue)
//    }
//    allowedIndicatorValue = if (indicatorValue > maxIndicatorValue) maxIndicatorValue else indicatorValue
//    var animatedIndicatorValue by remember { mutableStateOf(0f) }
//    LaunchedEffect(key1 = allowedIndicatorValue) {
//        animatedIndicatorValue = allowedIndicatorValue.toFloat()
//    }
//        val percentage = if (maxIndicatorValue != 0) (allowedIndicatorValue / maxIndicatorValue) * 100f else 0f
//        val sweepAngle by animateFloatAsState(
//            targetValue = (2.4 * percentage).toFloat(),
//            animationSpec = tween(1000)
//        )
//    val receivedValue by animateIntAsState(
//        targetValue = allowedIndicatorValue,
//        animationSpec = tween(1000)
//    )
//    val animatedBigTextColor by animateColorAsState(
//            targetValue = if (allowedIndicatorValue == 0)
//                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
//            else
//                bigTextColor,
//    animationSpec = tween(1000)
//    )
//
//    Column(
//        modifier = Modifier
//            .size(canvasSize)
//            .drawBehind {
//                val componentSize = size / 1.25f
//                backgroundIndicator(
//                    componentSize = componentSize,
//                    indicatorColor = backgroundColor,
//                    indicatorStokeWidth = backgroundIndicatorStrokeWidth
//                )
//                foregroundIndicator(
//                    componentSize = componentSize,
//                    indicatorColor = foregroundIndicatorColor,
//                    indicatorStokeWidth = foregroundIndicatorStrokeWidth,
//                    sweepAngle = sweepAngle
//                )
//            },
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        // Content can be added here
//        EmbbededElements(
//            bigText = receivedValue,
//            bigTextSize = bigTextSize,
//            bigTextColor = bigTextColor,
//            bigTextSuffix = bigTextSuffix,
//            smallText = smallText,
//            smallTextColor = smallTextColor,
//            smallTextSize = smallTextSize
//        )
//    }
//}
//
////@Composable
////fun CustomComponent (
////    canvasSize : Dp = 300.dp,
////    indicatorValue : Int = 0,
////    maxIndicatorValue : Int = 0,
////    backgroundColor : Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
////    backgroundIndicatorStrokeWidth : Float = 100f,
////    foregroundIndicatorStrokeWidth : Float = 100f,
////    foregroundIndicatorColor : Color = MaterialTheme.colorScheme.primary
////){
////    val animatedIndicatorValue = remember {
////        androidx.compose.animation.core.Animatable(initialValue = 0f)
////        }
////    LaunchedEffect(key1 = indicatorValue){
////        animatedIndicatorValue.animateTo(indicatorValue.toFloat())
////    }
////
////    val percentage = (animatedIndicatorValue.value / maxIndicatorValue) * 100
////    val sweepAngle by animateFloatAsState(
////        targetValue = (2.4 * percentage).toFloat(),
////        animationSpec = tween(1000)
////    )
////
////    Column(
////        modifier = Modifier
////            .size(canvasSize)
////            .drawBehind {
////                val componentSize = size / 1.25f
////                backgroundIndicator(
////                    componentSize = componentSize,
////                    indicatorColor = backgroundColor,
////                    indicatorStokeWidth = backgroundIndicatorStrokeWidth
////                )
////                foregroundIndicator(
////                    componentSize = componentSize,
////                    indicatorColor = foregroundIndicatorColor,
////                    indicatorStokeWidth = foregroundIndicatorStrokeWidth,
////                    sweepAngle = sweepAngle
////                )
////            }
////    ){
////
////    }
////
////}
//
//fun DrawScope.foregroundIndicator(
//    sweepAngle : Float,
//    componentSize : Size,
//    indicatorColor : Color,
//    indicatorStokeWidth : Float,
//){
//    drawArc(
//
//        color = indicatorColor,
//        size = componentSize,
//        startAngle = 150f,
//        sweepAngle = sweepAngle,
//        useCenter = false,
//        style = Stroke(width = indicatorStokeWidth,
//            cap = StrokeCap.Round),
//        topLeft = Offset(
//            x = (size.width - componentSize.width) / 2f,
//            y = (size.height - componentSize.height) / 2f
//        )
//    )
//}
// fun DrawScope.backgroundIndicator(
//     componentSize : Size,
//     indicatorColor : Color,
//     indicatorStokeWidth : Float,
// ){
//    drawArc(
//        color = indicatorColor,
//        size = componentSize,
//        startAngle = 150f,
//        sweepAngle = 240f,
//        useCenter = false,
//        style = Stroke(width = indicatorStokeWidth,
//        cap = StrokeCap.Round),
//        topLeft = Offset(
//            x = (size.width - componentSize.width) / 2f,
//            y = (size.height - componentSize.height) / 2f
//        )
//    )
// }
//
//@Composable
//fun EmbbededElements(
//    bigText: Int,
//    bigTextSize: TextUnit,
//    bigTextColor: Color,
//    bigTextSuffix: String,
//    smallText: String,
//    smallTextColor: Color,
//    smallTextSize: TextUnit
//) {
//    Text(
//        text = smallText,
//        color = smallTextColor,
//        fontSize = smallTextSize,
//        textAlign = TextAlign.Center
//    )
//    Text(
//        text = "$bigText ${bigTextSuffix.take(2)}",
//        color = bigTextColor,
//        fontSize = bigTextSize,
//        textAlign = TextAlign.Center,
//        fontWeight = FontWeight.Bold
//    )
//}
//@Composable
//@Preview(showBackground = true)
//fun CustomComponentPreview(){
//    CustomComponent()
//}
//// The CustomComponent function is a composable function that takes three parameters: canvasSize, indicatorValue, and maxIndicatorValue. The canvasSize parameter is the size of the canvas, and the indicatorValue and maxIndicatorValue parameters are used to draw the indicator.
//// The CustomComponentPreview function is a composable function that previews the CustomComponent function.
//// Step 4: Add the CustomComponent to the MainActivity
//// In this step, you will add the CustomComponent to the MainActivity.
//// Open the MainActivity.kt file and add the following code: