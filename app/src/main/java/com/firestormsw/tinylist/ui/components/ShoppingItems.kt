package com.firestormsw.tinylist.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
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

    // Instead of partitioning, create a single sorted list with a secondary sort key
    // for position within section (unchecked first, then checked)
    val sortedItems = items.sortedWith(
        compareBy(
            // First sort key: is the item checked (and not pending)
            { item -> item.isChecked && item.id !in pendingCheckedItemIds },
            // Second sort key: within each section, prioritize highlighted items
            { item -> !item.isHighlighted },
            // Third sort key: original item id for stability
            { item -> item.id }
        )
    )

    // For headers and other UI elements, we still need to know which items are checked
    val checkedItems = sortedItems.filter {
        it.isChecked && it.id !in pendingCheckedItemIds
    }
    val uncheckedItems = sortedItems.filter {
        !it.isChecked || it.id in pendingCheckedItemIds
    }

    // Animation specs
    val itemAnimationSpec = tween<IntOffset>(
        durationMillis = 300,
        easing = FastOutSlowInEasing
    )

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
        return
    }

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        if (uncheckedItems.isEmpty() && checkedItems.isNotEmpty()) {
            item(key = "all-checked-header") {
                Box(
                    modifier = Modifier.animateItem(
                        placementSpec = itemAnimationSpec
                    )
                ) {
                    Text(
                        stringResource(R.string.all_items_have_been_checked_off),
                        color = MaterialTheme.colorScheme.primary,
                    )
                }
            }
        }

        // Instead of two separate sections, render all items in sequence
        // using the sorting to group them visually
        itemsIndexed(
            items = sortedItems,
            key = { index, item -> if (index == 0) 0 else item.id } // Explicit key for animation
        ) { index, item ->
            // Check if this is the first checked item (for section header)
            val isFirstCheckedItem = item.isChecked &&
                    item.id !in pendingCheckedItemIds &&
                    sortedItems.indexOf(item) > 0 &&
                    !(sortedItems[sortedItems.indexOf(item) - 1].isChecked &&
                            sortedItems[sortedItems.indexOf(item) - 1].id !in pendingCheckedItemIds)

            // Insert header before the first checked item
            if (isFirstCheckedItem && checkedItems.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) { isCompletedExpanded = !isCompletedExpanded }
                        .padding(vertical = 8.dp),
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

            // Show the item if it's unchecked or if checked section is expanded
            if (!item.isChecked ||
                item.id in pendingCheckedItemIds ||
                isCompletedExpanded) {
                ShoppingItemRow(
                    item = item,
                    onItemClick = onItemClick,
                    onPromptEditItem = { onPromptEditItem(item) },
                    onPromptDeleteItem = { onPromptDeleteItem(item) },
                    onItemSetHighlight = { state -> onItemSetHighlight(item, state) },
                    modifier = Modifier.animateItem(placementSpec = itemAnimationSpec)
                )
            }
        }

        // Uncheck all button only if there are checked items and the section is expanded
        if (checkedItems.isNotEmpty() && isCompletedExpanded) {
            item(key = "uncheck-all") {
                Button(
                    onClick = { onUncheckAllClick() },
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .animateItem(placementSpec = itemAnimationSpec)
                ) {
                    Text(stringResource(R.string.uncheck_all))
                }
            }
        }
    }
}