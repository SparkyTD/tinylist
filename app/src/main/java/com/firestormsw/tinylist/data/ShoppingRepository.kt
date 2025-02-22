package com.firestormsw.tinylist.data

import com.firestormsw.tinylist.data.dao.ShoppingDao
import com.firestormsw.tinylist.data.entities.ShoppingItemEntity
import com.firestormsw.tinylist.data.entities.ShoppingListEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ShoppingRepository(private val shoppingDao: ShoppingDao) {
    val allShoppingLists: Flow<List<ShoppingList>> = shoppingDao.getShoppingListsWithItems()
        .map { lists ->
            lists.map { listWithItems ->
                ShoppingList(
                    id = listWithItems.list.id,
                    name = listWithItems.list.name,
                    items = listWithItems.items.map { item ->
                        ShoppingItem(
                            id = item.id,
                            text = item.text,
                            isChecked = item.isChecked,
                            quantity = item.quantity,
                            unit = item.unit,
                            isDelayedAfterChecked = false
                        )
                    }
                )
            }
        }

    suspend fun insertShoppingList(shoppingList: ShoppingList) {
        shoppingDao.insertShoppingList(
            ShoppingListEntity(
                id = shoppingList.id,
                name = shoppingList.name
            )
        )
        shoppingDao.insertShoppingItems(
            shoppingList.items.map { item ->
                ShoppingItemEntity(
                    id = item.id,
                    listId = shoppingList.id,
                    text = item.text,
                    quantity = item.quantity,
                    unit = item.unit,
                    isChecked = item.isChecked
                )
            }
        )
    }

    suspend fun updateItemCheckedStatus(itemId: String, isChecked: Boolean) {
        shoppingDao.updateItemCheckedStatus(itemId, isChecked)
    }

    suspend fun uncheckAllItems(listId: String) {
        shoppingDao.uncheckAllItems(listId)
    }
}