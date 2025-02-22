package com.firestormsw.tinylist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.firestormsw.tinylist.data.AppDatabase
import com.firestormsw.tinylist.data.ShoppingRepository
import com.firestormsw.tinylist.ui.components.AddItemSheet
import com.firestormsw.tinylist.ui.components.AddListSheet
import com.firestormsw.tinylist.ui.components.ListSelector
import com.firestormsw.tinylist.ui.components.ShoppingItems
import com.firestormsw.tinylist.ui.theme.TinyListTheme
import com.firestormsw.tinylist.viewmodel.ShoppingListViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = AppDatabase.getDatabase(applicationContext)
        val repository = ShoppingRepository(database.shoppingDao())

        enableEdgeToEdge()
        setContent {
            TinyListTheme {
                val viewModel: ShoppingListViewModel = viewModel(
                    factory = ShoppingListViewModel.provideFactory(repository)
                )
                val state by viewModel.uiState.collectAsState()

                val selectedList = state.lists.find { it.id == state.selectedListId }

                Box(modifier = Modifier.fillMaxSize()) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        topBar = {
                            Surface(shadowElevation = 3.dp) {
                                Column(
                                    modifier = Modifier.statusBarsPadding()
                                ) {
                                    ListSelector(
                                        lists = state.lists,
                                        selectedListId = state.selectedListId,
                                        onListSelected = viewModel::selectList,
                                        onPromptCreateList = viewModel::openCreateListSheet,
                                        modifier = Modifier.padding(vertical = 8.dp)
                                    )
                                }
                            }
                        },
                        floatingActionButton = {
                            FloatingActionButton(
                                onClick = viewModel::openAddItemSheet
                            ) {
                                Icon(Icons.Default.Add, contentDescription = "Add item")
                            }
                        }
                    ) { innerPadding ->
                        selectedList?.let { list ->
                            Column(
                                modifier = Modifier
                                    .padding(innerPadding)
                                    .fillMaxSize()
                            ) {
                                ShoppingItems(
                                    items = list.items,
                                    onItemClick = { itemId ->
                                        viewModel.toggleItem(list.id, itemId)
                                    },
                                    onUncheckAllClick = {
                                        viewModel.uncheckAllItems(list.id)
                                    },
                                    modifier = Modifier.fillMaxSize(),
                                )
                            }
                        }
                    }

                    AddItemSheet(
                        isOpen = state.isAddItemSheetOpen,
                        onDismiss = viewModel::closeAddItemSheet,
                        onSave = {
                            // todo save item to repository
                            viewModel.closeAddItemSheet()
                        }
                    )

                    AddListSheet(
                        isOpen = state.isCreateListSheetOpen,
                        onDismiss = viewModel::closeCreateListSheet,
                        onSave = {
                            // todo save list to repository
                            viewModel.closeCreateListSheet()
                        }
                    )
                }
            }
        }
    }
}