package dev.muazkadan.switchycompose

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.runtime.Composable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CustomISwitch(
    modifier: Modifier = Modifier,
    buttonHeight: Dp = 40.dp,
    innerPadding: Dp = 3.5.dp,
    shape: RoundedCornerShape = RoundedCornerShape(45.dp),
    switchValue: Boolean,
    positiveColor: Color = Color(0xFF35C759),
    negativeColor: Color = Color(0xFFE9E9EA),
    positiveContent: @Composable BoxScope.() -> Unit,
    negativeContent: @Composable BoxScope.() -> Unit,
    onValueChanged: (Boolean) -> Unit,
) {
    var width by remember { (mutableStateOf(0.dp)) }

    val interactionSource = remember {
        MutableInteractionSource()
    }

    var padding by remember {
        mutableStateOf(0.dp)
    }

    padding = if (switchValue) 0.dp else width - (width / 2)

    val animateSize by animateDpAsState(
        targetValue = if (switchValue) 0.dp else padding,
        tween(
            durationMillis = 333,
            delayMillis = 0,
            easing = LinearOutSlowInEasing
        )
    )

    val animateBgColor by animateColorAsState(
        targetValue = if (switchValue) positiveColor else negativeColor,
        tween(
            durationMillis = 333,
            delayMillis = 0,
            easing = FastOutSlowInEasing
        )
    )

    val localDensity = LocalDensity.current
    Box(
        modifier = modifier
            .defaultMinSize(
                minWidth = buttonHeight * 2,
                minHeight = buttonHeight
            )
            .onGloballyPositioned {
                width = with(localDensity) {
                    it.size.width.toDp()
                }
            }
            .height(buttonHeight)
            .clip(shape = shape)
            .background(animateBgColor)
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                onValueChanged(!switchValue)
            }
    ) {
        Row {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(animateSize)
                    .background(Color.Transparent)
            )
            Box(
                modifier = Modifier
                    .size(buttonHeight)
                    .padding(innerPadding)
                    .shadow(elevation = 5.dp, shape)
                    .clip(shape = shape)
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                if (switchValue)
                    positiveContent()
                else
                    negativeContent()
            }
        }
    }
}


@Preview
@Composable
private fun CustomISwitchPreview() {
    CustomISwitch(
        switchValue = true,
        onValueChanged = {},
        positiveContent = {
            Icon(
                imageVector = Icons.Default.Done,
                contentDescription = null
            )
        },
        negativeContent = {}
    )
}