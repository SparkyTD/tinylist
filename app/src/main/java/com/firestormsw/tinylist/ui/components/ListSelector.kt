package com.firestormsw.tinylist.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.firestormsw.tinylist.R
import com.firestormsw.tinylist.data.ShoppingList
import com.firestormsw.tinylist.ui.Add

@Composable
fun ListSelector(
    lists: List<ShoppingList>,
    selectedListId: String,
    onListSelected: (String) -> Unit,
    onPromptCreateList: () -> Unit,
    onPromptDeleteList: (ShoppingList) -> Unit,
    onPromptEditList: (ShoppingList) -> Unit,
    modifier: Modifier = Modifier
) {
    var showMenu by remember { mutableStateOf(false) }
    var targetList by remember { mutableStateOf<ShoppingList?>(null) }
    var menuOffset by remember { mutableStateOf(DpOffset.Zero) }
    val chipInteractionSource = remember { MutableInteractionSource() }

    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(lists) { list ->
            val selected = list.id == selectedListId
            val backgroundColor by animateColorAsState(
                if (selected) MaterialTheme.colorScheme.primaryContainer
                else MaterialTheme.colorScheme.surface,
                label = "backgroundColor"
            )
            val haptic = LocalHapticFeedback.current

            Box {
                FilterChip(
                    selected = selected,
                    onClick = { onListSelected(list.id) },
                    label = { Text(list.name) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = backgroundColor
                    ),
                    interactionSource = chipInteractionSource,
                )
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .pointerInput(Unit) {
                            detectTapGestures(onLongPress = { offset ->
                                haptic.performHapticFeedback(HapticFeedbackType.LongPress)

                                targetList = list
                                menuOffset = DpOffset(
                                    offset.x.toDp(),
                                    offset.y.toDp()
                                )
                                showMenu = true
                            }, onTap = {
                                onListSelected(list.id)
                            })
                        }
                )
            }
        }
        item {
            FilterChip(
                selected = true,
                onClick = onPromptCreateList,
                leadingIcon = { Icon(Add, contentDescription = stringResource(R.string.create_new_list_desc)) },
                label = { Text(stringResource(R.string.create_new_list_label)) },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
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
                onPromptEditList(targetList!!)
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
                onPromptDeleteList(targetList!!)
                showMenu = false
            }
        )
    }
}