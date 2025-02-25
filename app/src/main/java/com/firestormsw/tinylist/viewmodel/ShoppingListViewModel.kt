package com.firestormsw.tinylist.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.firestormsw.tinylist.R
import com.firestormsw.tinylist.ResourceProvider
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
import ulid.ULID

class ShoppingListViewModel(
    private val repository: ShoppingRepository,
    private val resourceProvider: ResourceProvider
) : ViewModel() {
    private val _uiState = MutableStateFlow(ShoppingListState())
    val uiState: StateFlow<ShoppingListState> = _uiState.asStateFlow()

    private var pendingMoveJob: Job? = null
    private val _pendingCheckedItems = mutableStateOf<Set<String>>(emptySet())
    val pendingCheckedItems: State<Set<String>> = _pendingCheckedItems

    init {
        // Load lists from database
        viewModelScope.launch {
            repository.getAllLists().collect { lists ->
                if (lists.isNotEmpty()) {
                    _uiState.update { state ->
                        state.copy(
                            lists = lists,
                            selectedListId = state.selectedListId.ifEmpty { lists.first().id }
                        )
                    }
                }
            }
        }
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
                // Update the database immediately
                repository.toggleItemChecked(itemId)

                // If item is highlighted, then unhighlight when checked
                if (currentItem.isHighlighted) {
                    repository.setItemHighlighted(itemId, false)
                }

                // Add to pending items first for immediate UI feedback
                _pendingCheckedItems.value += itemId

                // Update UI state immediately
                updateItemCheckedState(listId, itemId, checked = true, highlighted = false)

                // Schedule the delayed confirmation
                pendingMoveJob?.cancel()
                pendingMoveJob = viewModelScope.launch {
                    delay(800)
                    _pendingCheckedItems.value = emptySet()
                }
            } else {
                // For unchecking, update immediately without delay
                updateItemCheckedState(listId, itemId, false)
                repository.toggleItemChecked(itemId)
            }
        }
    }

    fun setItemHighlighted(listId: String, itemId: String, isHighlighted: Boolean) {
        viewModelScope.launch {
            repository.setItemHighlighted(itemId, isHighlighted)

            _uiState.update { state ->
                val updatedLists = state.lists.map { list ->
                    if (list.id == listId) {
                        list.copy(
                            items = list.items.map { item ->
                                if (item.id == itemId) {
                                    item.copy(isHighlighted = isHighlighted)
                                } else item
                            }
                        )
                    } else list
                }
                state.copy(lists = updatedLists)
            }
        }
    }

    private fun updateItemCheckedState(listId: String, itemId: String, checked: Boolean, highlighted: Boolean? = null) {
        _uiState.update { state ->
            val updatedLists = state.lists.map { list ->
                if (list.id == listId) {
                    list.copy(
                        items = list.items.map { item ->
                            if (item.id == itemId) {
                                if (highlighted == null) {
                                    item.copy(isChecked = checked)
                                } else {
                                    item.copy(isChecked = checked, isHighlighted = highlighted)
                                }
                            } else item
                        }
                    )
                } else list
            }
            state.copy(lists = updatedLists)
        }
    }

    fun uncheckAllItems(listId: String) {
        viewModelScope.launch {
            repository.uncheckAllItems(listId)
            // Update UI state immediately
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
    }

    fun openAddItemSheet() {
        _uiState.update {
            it.copy(
                isAddItemSheetOpen = true,
                editItem = null,
                editItemListId = null
            )
        }
    }

    fun openEditItemSheet(listId: String, editItem: ShoppingItem) {
        _uiState.update {
            it.copy(
                isAddItemSheetOpen = true,
                editItem = editItem,
                editItemListId = listId
            )
        }
    }

    fun closeAddItemSheet() {
        _uiState.update { it.copy(isAddItemSheetOpen = false) }
    }

    fun openCreateListSheet() {
        _uiState.update { it.copy(isCreateListSheetOpen = true) }
    }

    fun openEditListSheet(list: ShoppingList) {
        _uiState.update { it.copy(isCreateListSheetOpen = true, editList = list) }
    }

    fun closeCreateListSheet() {
        _uiState.update { it.copy(isCreateListSheetOpen = false) }
    }

    fun addNewList(name: String): ShoppingList {
        val newList = ShoppingList(name = name)
        viewModelScope.launch {
            repository.createList(newList)
            closeCreateListSheet()
        }

        _uiState.update {
            it.copy(
                selectedListId = newList.id,
                lists = it.lists + newList,
                editList = null,
            )
        }

        return newList
    }

    fun addNewItem(listId: String, text: String, quantity: Int? = null, unit: String = "", id: String? = null) {
        viewModelScope.launch {
            var targetList = _uiState.value.lists.find {
                it.id == listId
            }

            var actualListId = listId

            if (targetList == null) {
                targetList =
                    ShoppingList(name = resourceProvider.getString(R.string.default_list_name))
                repository.createList(targetList)

                actualListId = targetList.id
                _uiState.update {
                    it.copy(
                        selectedListId = targetList.id,
                        lists = it.lists + targetList,
                        editList = null
                    )
                }
            }

            val existingItem = targetList.items.find {
                it.text == text
            }

            if (existingItem == null) {
                val newItem = ShoppingItem(
                    id = id ?: ULID.randomULID(),
                    text = text,
                    quantity = quantity,
                    unit = unit
                )
                repository.addItemToList(actualListId, newItem)

                // Update UI state immediately
                _uiState.update { state ->
                    val updatedLists = state.lists.map { list ->
                        if (list.id == actualListId) {
                            list.copy(items = list.items + newItem)
                        } else list
                    }
                    state.copy(
                        lists = updatedLists,
                        isAddItemSheetOpen = false,
                        editItem = null,
                    )
                }
            } else {
                updateItem(actualListId, existingItem.id, text, quantity, unit)
            }
        }
    }

    fun updateItem(listId: String, itemId: String, text: String, quantity: Int? = null, unit: String = "") {
        viewModelScope.launch {
            repository.updateItem(
                listId, ShoppingItem(
                    id = itemId,
                    text = text,
                    quantity = quantity,
                    unit = unit
                )
            )

            // Update UI state immediately
            _uiState.update { state ->
                val updatedLists = state.lists.map { list ->
                    if (list.id == listId) {
                        list.copy(
                            items = list.items.map { item ->
                                if (item.id == itemId) {
                                    item.copy(
                                        text = text,
                                        quantity = quantity,
                                        unit = unit
                                    )
                                } else {
                                    item
                                }
                            }
                        )
                    } else list
                }
                state.copy(lists = updatedLists, editItem = null)
            }
        }
    }

    fun deleteItemById(listId: String, itemId: String) {
        viewModelScope.launch {
            repository.deleteItem(itemId)

            val currentItem = _uiState.value.lists
                .find { it.id == listId }
                ?.items
                ?.find { it.id == itemId }
            val itemName = currentItem?.text

            _uiState.update { state ->
                val updatedLists = state.lists.map { list ->
                    if (list.id == listId) {
                        list.copy(items = list.items.filter { it.id != itemId })
                    } else list
                }
                state.copy(
                    lists = updatedLists,
                    isAddItemSheetOpen = false,
                    snackbarMessage = resourceProvider.getString(R.string.entry_deleted, itemName),
                    snackbarAction = {
                        val item = currentItem!!
                        addNewItem(listId, item.text, item.quantity, item.unit, item.id)
                    },
                    lastDeletedItemForUndo = currentItem
                )
            }
        }
    }

    fun updateList(editList: ShoppingList) {
        viewModelScope.launch {
            repository.updateList(editList)

            // Update UI state immediately
            _uiState.update { state ->
                val updatedLists = state.lists.map { list ->
                    if (list.id == editList.id) {
                        list.copy(
                            name = editList.name
                        )
                    } else list
                }
                state.copy(lists = updatedLists, editList = null)
            }
        }
    }

    fun deleteListById(listId: String) {
        viewModelScope.launch {
            repository.deleteList(listId)

            val currentList = _uiState.value.lists
                .find { it.id == listId }!!
            val firstList = _uiState.value.lists.firstOrNull { it.id != listId }
            val itemName = currentList.name

            _uiState.update { state ->
                val updatedLists = state.lists.filter { list ->
                    list.id != listId
                }
                state.copy(
                    selectedListId = firstList?.id ?: "",
                    snackbarAction = {
                        val newList = addNewList(itemName)
                        for (item in currentList.items) {
                            addNewItem(newList.id, item.text, item.quantity, item.unit)
                        }
                    },
                    lists = updatedLists,
                    snackbarMessage = resourceProvider.getString(R.string.entry_deleted, itemName),
                    lastDeletedListForUndo = currentList
                )
            }
        }
    }

    fun clearSnackbar() {
        _uiState.update {
            it.copy(
                snackbarMessage = null,
                snackbarAction = null
            )
        }
    }

    fun getEditItem(): ShoppingItem? {
        return _uiState.value.editItem
    }

    fun getEditItemListId(): String? {
        return _uiState.value.editItemListId
    }

    fun getEditList(): ShoppingList? {
        return _uiState.value.editList
    }

    fun getCurrentList(): ShoppingList? {
        return _uiState.value.lists.find {
            it.id == _uiState.value.selectedListId
        }
    }

    companion object {
        fun provideFactory(
            repository: ShoppingRepository,
            resourceProvider: ResourceProvider
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ShoppingListViewModel(repository, resourceProvider) as T
            }
        }
    }
}

data class ShoppingListState(
    val lists: List<ShoppingList> = emptyList(),
    val selectedListId: String = "",
    val isAddItemSheetOpen: Boolean = false,
    val isCreateListSheetOpen: Boolean = false,
    val snackbarAction: (() -> Unit)? = null,
    val snackbarMessage: String? = null,
    val lastDeletedItemForUndo: ShoppingItem? = null,
    val lastDeletedListForUndo: ShoppingList? = null,
    val editItem: ShoppingItem? = null,
    val editItemListId: String? = null,
    val editList: ShoppingList? = null,
)