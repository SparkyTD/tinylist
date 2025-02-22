package com.firestormsw.tinylist.data

import ulid.ULID

data class ShoppingItem(
    val id: String = ULID.randomULID(),
    val text: String,
    val isChecked: Boolean = false,
    val quantity: Int? = null,
    val unit: String = "",
    val isDelayedAfterChecked: Boolean = false
)