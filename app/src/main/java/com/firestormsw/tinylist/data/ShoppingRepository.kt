package com.firestormsw.tinylist.data

import com.firestormsw.tinylist.data.dao.ShoppingDao
import com.firestormsw.tinylist.data.entities.ShoppingItemEntity
import com.firestormsw.tinylist.data.entities.ShoppingListEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transformLatest

class ShoppingRepository(private val shoppingDao: ShoppingDao) {
    // Domain model to entity conversions
    private fun ShoppingList.toEntity() = ShoppingListEntity(
        id = id,
        name = name
    )

    private fun ShoppingItem.toEntity(listId: String) = ShoppingItemEntity(
        id = id,
        listId = listId,
        text = text,
        quantity = quantity,
        unit = unit,
        isChecked = isChecked
    )

    private fun ShoppingListEntity.toDomain(items: List<ShoppingItemEntity> = emptyList()) = ShoppingList(
        id = id,
        name = name,
        items = items.map { it.toDomain() }
    )

    private fun ShoppingItemEntity.toDomain() = ShoppingItem(
        id = id,
        text = text,
        quantity = quantity,
        unit = unit,
        isChecked = isChecked
    )

    // Shopping List Operations
    @OptIn(ExperimentalCoroutinesApi::class)
    fun getAllLists(): Flow<List<ShoppingList>> = shoppingDao.getAllLists()
        .transformLatest { lists ->
            val listsWithItems = lists.map { list ->
                shoppingDao.getListWithItems(list.id).first()
            }
            emit(listsWithItems.mapNotNull { it?.list?.toDomain(it.items) })
        }

    fun getListById(listId: String): Flow<ShoppingList?> = shoppingDao.getListWithItems(listId)
        .map { listWithItems ->
            listWithItems?.let {
                it.list.toDomain(it.items)
            }
        }

    suspend fun createList(list: ShoppingList) {
        shoppingDao.insertList(list.toEntity())
    }

    suspend fun updateList(list: ShoppingList) {
        shoppingDao.updateList(list.toEntity())
    }

    suspend fun deleteList(listId: String) {
        shoppingDao.deleteListById(listId)
    }

    // Shopping Item Operations
    fun getItemsForList(listId: String): Flow<List<ShoppingItem>> =
        shoppingDao.getItemsForList(listId)
            .map { items -> items.map { it.toDomain() } }

    suspend fun addItemToList(listId: String, item: ShoppingItem) {
        shoppingDao.insertItem(item.toEntity(listId))
    }

    suspend fun addItemsToList(listId: String, items: List<ShoppingItem>) {
        shoppingDao.insertItems(items.map { it.toEntity(listId) })
    }

    suspend fun updateItem(listId: String, item: ShoppingItem) {
        shoppingDao.updateItem(item.toEntity(listId))
    }

    suspend fun deleteItem(itemId: String) {
        shoppingDao.deleteItemById(itemId)
    }

    suspend fun toggleItemChecked(itemId: String) {
        val item = shoppingDao.getItemByIdOnce(itemId) ?: return
        shoppingDao.updateItemCheckedStatus(itemId, !item.isChecked)
    }

    suspend fun updateItemQuantity(itemId: String, quantity: Int?) {
        shoppingDao.updateItemQuantity(itemId, quantity)
    }

    suspend fun updateItemUnit(itemId: String, unit: String) {
        shoppingDao.updateItemUnit(itemId, unit)
    }

    // List Management Operations
    suspend fun createListWithItems(list: ShoppingList) {
        shoppingDao.insertListWithItems(
            list = list.toEntity(),
            items = list.items.map { it.toEntity(list.id) }
        )
    }

    suspend fun clearCheckedItems(listId: String) {
        shoppingDao.deleteCheckedItems(listId)
    }

    suspend fun uncheckAllItems(listId: String) {
        shoppingDao.uncheckAllItems(listId)
    }

    // Statistics
    fun getItemCount(listId: String): Flow<Int> = shoppingDao.getItemCount(listId)

    fun getCheckedItemCount(listId: String): Flow<Int> = shoppingDao.getCheckedItemCount(listId)
}