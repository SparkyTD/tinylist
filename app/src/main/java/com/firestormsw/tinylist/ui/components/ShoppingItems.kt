package com.firestormsw.tinylist.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.firestormsw.tinylist.R
import com.firestormsw.tinylist.data.ShoppingItem
import com.firestormsw.tinylist.viewmodel.ShoppingListViewModel

@Composable
fun ShoppingItems(
    items: List<ShoppingItem>,
    viewModel: ShoppingListViewModel,
    onItemClick: (String) -> Unit,
    onUncheckAllClick: () -> Unit,
    onPromptDeleteItem: (ShoppingItem) -> Unit,
    onPromptEditItem: (ShoppingItem) -> Unit,
    onItemSetHighlight: (ShoppingItem, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    var isCompletedExpanded by remember { mutableStateOf(true) }

    val pendingCheckedItemIds by viewModel.pendingCheckedItems
    val (checkedItems, uncheckedItems) = items.sortedWith(
        compareBy(
            { !it.isHighlighted },
            { it.id })
    ).partition { item ->
        item.isChecked && item.id !in pendingCheckedItemIds
    }

    if (items.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                stringResource(R.string.no_items_label),
                color = MaterialTheme.colorScheme.secondaryContainer
            )
        }
    }

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        if (uncheckedItems.isEmpty() && checkedItems.isNotEmpty()) {
            item(key = "all-checked-header") {
                Box {
                    Text(
                        stringResource(R.string.all_items_have_been_checked_off),
                        color = MaterialTheme.colorScheme.primary,
                    )
                }
            }
        }

        items(
            items = uncheckedItems,
        ) { item ->
            ShoppingItemRow(
                item,
                onItemClick,
                onPromptEditItem = {
                    onPromptEditItem(item)
                }, onPromptDeleteItem = {
                    onPromptDeleteItem(item)
                },
                onItemSetHighlight = { state ->
                    onItemSetHighlight(item, state)
                }
            )
        }

        if (checkedItems.isNotEmpty()) {
            item(key = "completed-header") {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) { isCompletedExpanded = !isCompletedExpanded }
                        .padding(top = 16.dp, bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        stringResource(R.string.completed_count, checkedItems.size),
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.outline,
                    )
                    Icon(
                        imageVector = if (isCompletedExpanded) {
                            Icons.Default.KeyboardArrowUp
                        } else {
                            Icons.Default.KeyboardArrowDown
                        },
                        contentDescription = if (isCompletedExpanded) {
                            stringResource(R.string.collapse_completed_items)
                        } else {
                            stringResource(R.string.expand_completed_items)
                        },
                        tint = MaterialTheme.colorScheme.outline
                    )
                }
            }

            if (isCompletedExpanded) {  // Only show items if expanded
                items(
                    items = checkedItems,
                ) { item ->
                    ShoppingItemRow(
                        item,
                        onItemClick,
                        onPromptEditItem = {
                            onPromptEditItem(item)
                        }, onPromptDeleteItem = {
                            onPromptDeleteItem(item)
                        },
                        onItemSetHighlight = { state ->
                            onItemSetHighlight(item, state)
                        }
                    )
                }
                item(key = "uncheck-all") {
                    Button(
                        onClick = { onUncheckAllClick() },
                        modifier = Modifier.padding(horizontal = 16.dp)
                    ) {
                        Text(stringResource(R.string.uncheck_all))
                    }
                }
            }
        }
    }
}