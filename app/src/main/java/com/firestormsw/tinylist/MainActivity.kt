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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
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
                val snackbarHostState = remember { SnackbarHostState() }

                Box(modifier = Modifier.fillMaxSize()) {
                    LaunchedEffect(state.snackbarMessage) {
                        if (state.snackbarMessage != null) {
                            val result = snackbarHostState.showSnackbar(
                                message = state.snackbarMessage!!,
                                actionLabel = "Undo",
                                duration = SnackbarDuration.Short
                            )

                            when (result) {
                                SnackbarResult.ActionPerformed -> {
                                    state.snackbarAction?.invoke()
                                }

                                SnackbarResult.Dismissed -> {
                                    viewModel.clearSnackbar()
                                }
                            }
                        }
                    }

                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        topBar = {
                            Surface(shadowElevation = 0.dp) {
                                Column(
                                    modifier = Modifier.statusBarsPadding()
                                ) {
                                    ListSelector(
                                        lists = state.lists,
                                        selectedListId = state.selectedListId,
                                        onListSelected = viewModel::selectList,
                                        onPromptCreateList = viewModel::openCreateListSheet,
                                        onPromptEditList = { list ->
                                            viewModel.openEditListSheet(list)
                                        },
                                        onPromptDeleteList = { list ->
                                            viewModel.deleteListById(list.id)
                                        },
                                        modifier = Modifier.padding(vertical = 8.dp)
                                    )
                                }
                            }
                        },
                        floatingActionButton = {
                            FloatingActionButton(
                                onClick = viewModel::openAddItemSheet,
                                modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 62.dp)
                            ) {
                                Icon(Icons.Default.Add, contentDescription = "Add item")
                            }
                        },
                        snackbarHost = {
                            SnackbarHost(
                                hostState = snackbarHostState,
                                modifier = Modifier.padding(bottom = 16.dp)
                            ) { data ->
                                Snackbar(
                                    modifier = Modifier.padding(horizontal = 16.dp),
                                    action = {
                                        TextButton(
                                            onClick = { data.performAction() },
                                            colors = ButtonDefaults.textButtonColors(
                                                contentColor = MaterialTheme.colorScheme.inversePrimary
                                            )
                                        ) {
                                            Text(data.visuals.actionLabel ?: "")
                                        }
                                    }
                                ) {
                                    Text(data.visuals.message)
                                }
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
                                    viewModel = viewModel,
                                    onItemClick = { itemId ->
                                        viewModel.toggleItem(list.id, itemId)
                                    },
                                    onUncheckAllClick = {
                                        viewModel.uncheckAllItems(list.id)
                                    },
                                    onPromptEditItem = { item ->
                                        viewModel.openEditItemSheet(list.id, item)
                                    },
                                    onPromptDeleteItem = { item ->
                                        viewModel.deleteItemById(list.id, item.id)
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
                            val editItem = viewModel.getEditItem()
                            val editItemListId = viewModel.getEditItemListId()
                            if (editItem == null) {
                                viewModel.addNewItem(
                                    state.selectedListId,
                                    it.text,
                                    it.quantity,
                                    it.unit
                                )
                            } else {
                                viewModel.updateItem(
                                    listId = editItemListId!!,
                                    itemId = editItem.id,
                                    text = it.text,
                                    quantity = it.quantity,
                                    unit = it.unit
                                )
                            }
                            viewModel.closeAddItemSheet()
                        },
                        editItem = viewModel.getEditItem()
                    )

                    AddListSheet(
                        isOpen = state.isCreateListSheetOpen,
                        onDismiss = viewModel::closeCreateListSheet,
                        onSave = {
                            val editList = viewModel.getEditList()
                            if (editList == null) {
                                viewModel.addNewList(it.name)
                                viewModel.closeCreateListSheet()
                            } else {
                                viewModel.updateList(it)
                                viewModel.closeCreateListSheet()
                            }
                        },
                        editList = viewModel.getEditList()
                    )

                    if (viewModel.getCurrentList() == null) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "Start by adding a new list",
                                color = MaterialTheme.colorScheme.secondaryContainer
                            )
                        }
                    }
                }
            }
        }
    }
}