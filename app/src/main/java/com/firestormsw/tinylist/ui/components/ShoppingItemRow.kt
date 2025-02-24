package com.firestormsw.tinylist.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.firestormsw.tinylist.R
import com.firestormsw.tinylist.data.ShoppingItem

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ShoppingItemRow(
    item: ShoppingItem,
    onItemClick: (String) -> Unit,
    onPromptDeleteItem: () -> Unit,
    onPromptEditItem: () -> Unit,
    modifier: Modifier = Modifier
) {
    val haptic = LocalHapticFeedback.current

    var showMenu by remember { mutableStateOf(false) }
    var menuOffset by remember { mutableStateOf(DpOffset.Zero) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 0.dp, vertical = 2.dp)
    ) {
        // Background
        Surface(
            modifier = Modifier.fillMaxSize(),
            shape = RoundedCornerShape(12.dp),
            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
            tonalElevation = 1.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp)
                    .combinedClickable(
                        interactionSource = null,
                        indication = null,
                        onClick = {
                            onItemClick(item.id)
                        },
                        onLongClick = {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)

                            menuOffset = DpOffset(
                                100.dp,// offset.x.toDp(),
                                0.dp,// offset.y.toDp()
                            )
                            showMenu = true
                        },
                    ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                AnimatedCheckbox(item.isChecked) {
                    onItemClick(item.id)
                }

                // Main content
                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Item name
                    Text(
                        text = item.text,
                        style = MaterialTheme.typography.bodyLarge,
                        color = if (item.isChecked)
                            MaterialTheme.colorScheme.outline
                        else MaterialTheme.colorScheme.onSurface,
                        textDecoration = if (item.isChecked)
                            TextDecoration.LineThrough
                        else null,
                        modifier = Modifier.weight(1f)
                    )

                    // Quantity and unit
                    if (item.quantity != null && item.quantity != 0 || item.unit.isNotEmpty()) {
                        Spacer(Modifier.width(8.dp))
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.secondaryContainer
                        ) {
                            Text(
                                text = buildString {
                                    item.quantity?.let { append(it) }
                                    if (item.quantity != null && item.unit.isNotEmpty()) {
                                        append(" ")
                                    }
                                    append(item.unit)
                                },
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSecondaryContainer,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }
                }
            }
        }

        DropdownMenu(
            expanded = showMenu,
            onDismissRequest = { showMenu = false },
            offset = menuOffset,
        ) {
            DropdownMenuItem(
                text = {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                        Text(stringResource(R.string.edit))
                    }
                },
                onClick = {
                    onPromptEditItem()
                    showMenu = false
                }
            )
            DropdownMenuItem(
                text = {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.error
                        )
                        Text(stringResource(R.string.delete), color = MaterialTheme.colorScheme.error)
                    }
                },
                onClick = {
                    onPromptDeleteItem()
                    showMenu = false
                }
            )
        }
    }
}