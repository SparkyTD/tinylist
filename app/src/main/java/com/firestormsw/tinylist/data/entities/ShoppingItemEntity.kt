package com.firestormsw.tinylist.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "shopping_items",
    foreignKeys = [
        ForeignKey(
            entity = ShoppingListEntity::class,
            parentColumns = ["id"],
            childColumns = ["listId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ShoppingItemEntity(
    @PrimaryKey val id: String,
    val listId: String,
    val text: String,
    val quantity: Int?,
    val unit: String,
    val isChecked: Boolean,
)