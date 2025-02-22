package com.firestormsw.tinylist.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun AnimatedCheckbox(
    checked: Boolean,
    onCheckedChanged: (Boolean) -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }

    val circleDiameter = 24.dp;
    val endPadding = 20.dp;
    val animation = tween<Color>(
        durationMillis = 300,
        delayMillis = 0,
        easing = LinearOutSlowInEasing
    );

    val fillColor: Color by animateColorAsState(
        animationSpec = animation,
        targetValue = if (!checked) Color.Transparent else Color(0xFF54AB26),
    )
    val strokeColor: Color by animateColorAsState(
        animationSpec = animation,
        targetValue = if (!checked) MaterialTheme.colorScheme.primary else Color(0xFF54AB26),
    )
    val checkMarkColor: Color by animateColorAsState(
        animationSpec = animation,
        targetValue = if (!checked) Color.Transparent else Color.White
    )

    Box(
        modifier = Modifier
            .size(circleDiameter + endPadding, circleDiameter)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = { onCheckedChanged(!checked) },
            ),
        contentAlignment = Alignment.CenterStart
    ) {
        Canvas(modifier = Modifier.size(circleDiameter)) {
            drawCircle(
                color = fillColor,
                style = Fill,
                radius = (circleDiameter / 2).toPx()
            )

            drawCircle(
                color = strokeColor,
                style = Stroke(width = 3.dp.toPx()),
                radius = (circleDiameter / 2).toPx()
            )
        }

        Icon(
            imageVector = Icons.Default.Check,
            contentDescription = if (checked) "Checked" else "Unchecked",
            tint = checkMarkColor,
            modifier = Modifier
                .size(16.dp)
                .align(Alignment.CenterStart)
                .offset(circleDiameter.div(6))
        )
    }
}