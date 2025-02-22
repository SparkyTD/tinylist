package com.firestormsw.tinylist.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.firestormsw.tinylist.data.ShoppingItem

@Composable
fun ShoppingItemRow(
    item: ShoppingItem,
    onItemClick: (String) -> Unit,
) {
    Box(
        Modifier
            .fillMaxWidth()
            .height(48.dp)
    ) {
        Canvas(Modifier.fillMaxSize()) { }
        Row(
            modifier = Modifier.fillMaxWidth().align(Alignment.Center),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Spacer(Modifier.width(10.dp))
            AnimatedCheckbox(item.isChecked) { onItemClick(item.id) }
            Text(
                text = item.text,
                style = MaterialTheme.typography.bodyLarge,
                color = if (item.isChecked)
                    MaterialTheme.colorScheme.outline
                else MaterialTheme.colorScheme.onSurface,
                textDecoration = if (item.isChecked)
                    TextDecoration.LineThrough
                else null
            )
        }
    }
}