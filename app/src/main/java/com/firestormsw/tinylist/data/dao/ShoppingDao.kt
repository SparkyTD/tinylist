package com.firestormsw.tinylist.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.firestormsw.tinylist.data.entities.ShoppingItemEntity
import com.firestormsw.tinylist.data.entities.ShoppingListEntity
import com.firestormsw.tinylist.data.entities.ShoppingListWithItems
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingDao {
    @Transaction
    @Query("SELECT * FROM shopping_lists")
    fun getShoppingListsWithItems(): Flow<List<ShoppingListWithItems>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShoppingList(shoppingList: ShoppingListEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShoppingItems(items: List<ShoppingItemEntity>)

    @Query("DELETE FROM shopping_items WHERE listId = :listId")
    suspend fun deleteItemsFromList(listId: String)

    @Query("UPDATE shopping_items SET isChecked = :isChecked WHERE id = :itemId")
    suspend fun updateItemCheckedStatus(itemId: String, isChecked: Boolean)

    @Query("UPDATE shopping_items SET isChecked = 0 WHERE listId = :listId")
    suspend fun uncheckAllItems(listId: String)
}