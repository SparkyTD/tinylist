package com.firestormsw.tinylist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import com.firestormsw.tinylist.ui.components.ListSelector
import com.firestormsw.tinylist.ui.components.ShoppingItems
import com.firestormsw.tinylist.ui.theme.TinyListTheme
import com.firestormsw.tinylist.ui.viewmodel.ShoppingListViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TinyListTheme {
                val viewModel: ShoppingListViewModel = viewModel()
                val state by viewModel.uiState.collectAsState()

                val selectedList = state.lists.find { it.id == state.selectedListId }

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
                                    modifier = Modifier.padding(vertical = 8.dp)
                                )
                            }
                        }
                    },
                    floatingActionButton = {
                        FloatingActionButton(
                            onClick = { /* TODO: Add new item */ }
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
            }
        }
    }
}