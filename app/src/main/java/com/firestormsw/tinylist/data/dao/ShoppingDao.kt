package com.firestormsw.tinylist.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.firestormsw.tinylist.data.entities.ShoppingItemEntity
import com.firestormsw.tinylist.data.entities.ShoppingListEntity
import com.firestormsw.tinylist.data.entities.ShoppingListWithItems
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingDao {
    // Shopping List Operations
    @Query("SELECT * FROM shopping_lists")
    fun getAllLists(): Flow<List<ShoppingListEntity>>

    @Query("SELECT * FROM shopping_lists WHERE id = :listId")
    fun getListById(listId: String): Flow<ShoppingListEntity?>

    @Query("SELECT * FROM shopping_lists WHERE id = :listId")
    suspend fun getListByIdOnce(listId: String): ShoppingListEntity?

    @Query("SELECT * FROM shopping_lists WHERE id = :listId")
    fun getListWithItems(listId: String): Flow<ShoppingListWithItems?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(list: ShoppingListEntity)

    @Update
    suspend fun updateList(list: ShoppingListEntity)

    @Delete
    suspend fun deleteList(list: ShoppingListEntity)

    @Query("DELETE FROM shopping_lists WHERE id = :listId")
    suspend fun deleteListById(listId: String)

    // Shopping Item Operations
    @Query("SELECT * FROM shopping_items WHERE listId = :listId")
    fun getItemsForList(listId: String): Flow<List<ShoppingItemEntity>>

    @Query("SELECT * FROM shopping_items WHERE id = :itemId")
    fun getItemById(itemId: String): Flow<ShoppingItemEntity?>

    @Query("SELECT * FROM shopping_items WHERE id = :itemId")
    suspend fun getItemByIdOnce(itemId: String): ShoppingItemEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: ShoppingItemEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItems(items: List<ShoppingItemEntity>)

    @Update
    suspend fun updateItem(item: ShoppingItemEntity)

    @Delete
    suspend fun deleteItem(item: ShoppingItemEntity)

    @Query("DELETE FROM shopping_items WHERE id = :itemId")
    suspend fun deleteItemById(itemId: String)

    @Query("DELETE FROM shopping_items WHERE listId = :listId")
    suspend fun deleteAllItemsInList(listId: String)

    // Bulk Operations
    @Transaction
    suspend fun insertListWithItems(list: ShoppingListEntity, items: List<ShoppingItemEntity>) {
        insertList(list)
        insertItems(items)
    }

    // Item Checked Status Operations
    @Query("UPDATE shopping_items SET isChecked = :isChecked WHERE id = :itemId")
    suspend fun updateItemCheckedStatus(itemId: String, isChecked: Boolean)

    // Item Highlight Status Operations
    @Query("UPDATE shopping_items SET isHighlighted = :isHighlighted WHERE id = :itemId")
    suspend fun setItemHighlightedStatus(itemId: String, isHighlighted: Boolean)

    @Query("UPDATE shopping_items SET quantity = :quantity WHERE id = :itemId")
    suspend fun updateItemQuantity(itemId: String, quantity: Int?)

    @Query("UPDATE shopping_items SET unit = :unit WHERE id = :itemId")
    suspend fun updateItemUnit(itemId: String, unit: String)

    // Utility Queries
    @Query("SELECT COUNT(*) FROM shopping_items WHERE listId = :listId")
    fun getItemCount(listId: String): Flow<Int>

    @Query("SELECT COUNT(*) FROM shopping_items WHERE listId = :listId AND isChecked = 1")
    fun getCheckedItemCount(listId: String): Flow<Int>

    @Query("UPDATE shopping_items SET isChecked = 0 WHERE listId = :listId")
    suspend fun uncheckAllItems(listId: String)

    @Query("DELETE FROM shopping_items WHERE listId = :listId AND isChecked = 1")
    suspend fun deleteCheckedItems(listId: String)
}