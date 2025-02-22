package com.firestormsw.tinylist.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

data class ShoppingList(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val items: List<ShoppingItem> = emptyList()
)

data class ShoppingItem(
    val id: String = UUID.randomUUID().toString(),
    val text: String,
    val isChecked: Boolean = false,
    val isDelayedAfterChecked: Boolean = false
)