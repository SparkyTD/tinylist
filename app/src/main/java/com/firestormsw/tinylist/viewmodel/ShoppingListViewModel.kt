package com.firestormsw.tinylist.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.firestormsw.tinylist.data.ShoppingItem
import com.firestormsw.tinylist.data.ShoppingList
import com.firestormsw.tinylist.data.ShoppingRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ShoppingListViewModel(private val repository: ShoppingRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(ShoppingListState())
    val uiState: StateFlow<ShoppingListState> = _uiState.asStateFlow()

    private var pendingMoveJob: Job? = null
    private val _pendingCheckedItems = mutableStateOf<Set<String>>(emptySet())
    val pendingCheckedItems: State<Set<String>> = _pendingCheckedItems

    init {
        // Sample data
        val sampleLists = listOf(
            ShoppingList(
                id = "groceries",
                name = "Groceries",
                items = listOf(
                    ShoppingItem("1", "Ramen"),
                    ShoppingItem("2", "Chips"),
                    ShoppingItem("3", "Gummies"),
                    ShoppingItem("4", "Nutella"),
                    ShoppingItem("5", "Pop corn"),
                    ShoppingItem("6", "Strongbow"),
                    ShoppingItem("7", "Peanut Butter"),
                    ShoppingItem("8", "Bacon"),
                    ShoppingItem("9", "Ham"),
                    ShoppingItem("10", "Spicy Sausage"),
                    ShoppingItem("11", "Alpro"),
                    ShoppingItem("12", "Coke"),
                    ShoppingItem("13", "Water"),
                    ShoppingItem("14", "Banana"),
                    ShoppingItem("15", "Lemon"),
                )
            ),
            ShoppingList(
                id = "hardware",
                name = "Hardware",
                items = listOf(
                    ShoppingItem("4", "Screws"),
                    ShoppingItem("5", "Paint"),
                    ShoppingItem("6", "Brushes")
                )
            ),
        )
        _uiState.value = ShoppingListState(
            lists = sampleLists,
            selectedListId = sampleLists.first().id
        )
    }

    fun selectList(id: String) {
        _uiState.update { it.copy(selectedListId = id) }
    }

    fun toggleItem(listId: String, itemId: String) {
        viewModelScope.launch {
            val currentItem = _uiState.value.lists
                .find { it.id == listId }
                ?.items
                ?.find { it.id == itemId }

            if (currentItem?.isChecked == false) {
                _pendingCheckedItems.apply { _pendingCheckedItems.value += itemId }
                setItemCheckedState(listId, itemId, true)

                pendingMoveJob?.cancel()
                pendingMoveJob = viewModelScope.launch {
                    delay(800)
                    setItemCheckedState(listId, itemId, true)
                    _pendingCheckedItems.apply { _pendingCheckedItems.value = emptySet() }
                }
            } else {
                setItemCheckedState(listId, itemId, false)
            }
        }
    }

    private fun setItemCheckedState(listId: String, itemId: String, checked: Boolean) {
        _uiState.update { state ->
            val updatedLists = state.lists.map { list ->
                if (list.id == listId) {
                    list.copy(
                        items = list.items.map { item ->
                            if (item.id == itemId) {
                                item.copy(isChecked = checked)
                            } else item
                        }
                    )
                } else list
            }
            state.copy(
                lists = updatedLists,
            )
        }
    }

    fun uncheckAllItems(listId: String) {
        _uiState.update { state ->
            val updatedLists = state.lists.map { list ->
                if (list.id == listId) {
                    list.copy(
                        items = list.items.map { item ->
                            item.copy(isChecked = false)
                        }
                    )
                } else list
            }
            state.copy(lists = updatedLists)
        }
    }

    fun openAddItemSheet() {
        _uiState.update { it.copy(isAddItemSheetOpen = true) }
    }

    fun closeAddItemSheet() {
        _uiState.update { it.copy(isAddItemSheetOpen = false) }
    }

    fun openCreateListSheet() {
        _uiState.update { it.copy(isCreateListSheetOpen = true) }
    }

    fun closeCreateListSheet() {
        _uiState.update { it.copy(isCreateListSheetOpen = false) }
    }

    companion object {
        fun provideFactory(
            repository: ShoppingRepository
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ShoppingListViewModel(repository) as T
            }
        }
    }
}

data class ShoppingListState(
    val lists: List<ShoppingList> = emptyList(),
    val selectedListId: String = "",
    val isAddItemSheetOpen: Boolean = false,
    val isCreateListSheetOpen: Boolean = false,
)