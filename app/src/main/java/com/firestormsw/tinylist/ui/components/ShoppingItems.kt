package com.firestormsw.tinylist.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
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
import androidx.compose.ui.unit.dp
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
    modifier: Modifier = Modifier
) {
    var isCompletedExpanded by remember { mutableStateOf(true) }

    val pendingCheckedItemIds by viewModel.pendingCheckedItems
    val (checkedItems, uncheckedItems) = items.partition { item ->
        item.isChecked && item.id !in pendingCheckedItemIds
    }

    if (items.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("This list is empty", color = MaterialTheme.colorScheme.secondaryContainer)
        }
    }

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
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
                        "Completed (${checkedItems.size})",
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
                            "Collapse completed items"
                        } else {
                            "Expand completed items"
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
                        }
                    )
                }
                item(key = "uncheck-all") {
                    Button(
                        onClick = { onUncheckAllClick() },
                        modifier = Modifier.padding(horizontal = 16.dp)
                    ) {
                        Text("Uncheck All")
                    }
                }
            }
        }
    }
}