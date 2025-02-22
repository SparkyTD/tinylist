package com.firestormsw.tinylist.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.firestormsw.tinylist.data.ShoppingList

@Composable
fun ListSelector(
    lists: List<ShoppingList>,
    selectedListId: String,
    onListSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
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

            FilterChip(
                selected = selected,
                onClick = { onListSelected(list.id) },
                label = { Text(list.name) },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = backgroundColor
                )
            )
        }
        item {
            FilterChip(
                selected = true,
                onClick = { /* todo */ },
                label = { Text("+ Add") },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    }
}