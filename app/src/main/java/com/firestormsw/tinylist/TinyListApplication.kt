package com.firestormsw.tinylist

import android.app.Application
import com.firestormsw.tinylist.data.AppDatabase
import com.firestormsw.tinylist.data.ShoppingRepository

class TinyListApplication : Application() {
    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { ShoppingRepository(database.shoppingDao()) }
}