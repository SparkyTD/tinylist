package com.firestormsw.tinylist.data

import ulid.ULID

data class ShoppingList(
    val id: String = ULID.randomULID(),
    val name: String,
    val items: List<ShoppingItem> = emptyList()
)