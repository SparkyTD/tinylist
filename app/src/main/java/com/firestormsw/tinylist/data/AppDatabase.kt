package com.firestormsw.tinylist.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.firestormsw.tinylist.data.dao.ShoppingDao
import com.firestormsw.tinylist.data.entities.ShoppingItemEntity
import com.firestormsw.tinylist.data.entities.ShoppingListEntity

@Database(
    entities = [ShoppingListEntity::class, ShoppingItemEntity::class],
    version = 3
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun shoppingDao(): ShoppingDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "shopping_app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}