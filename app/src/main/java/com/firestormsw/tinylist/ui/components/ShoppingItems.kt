package com.firestormsw.tinylist.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.OverscrollEffect
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.firestormsw.tinylist.data.ShoppingItem

@Composable
fun ShoppingItems(
    items: List<ShoppingItem>,
    onItemClick: (String) -> Unit,
    onUncheckAllClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val (checkedItems, uncheckedItems) = items.partition { item ->
        item.isChecked && !item.isDelayedAfterChecked
    }

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(
            items = uncheckedItems,
        ) { item ->
            ShoppingItemRow(item, onItemClick)
        }

        if (checkedItems.isNotEmpty()) {
            item(key = "completed-header") {
                Text(
                    "Completed",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.outline,
                    modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                )
            }
            items(
                items = checkedItems,
            ) { item ->
                ShoppingItemRow(item, onItemClick)
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