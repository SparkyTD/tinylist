package com.firestormsw.tinylist.ui.components

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.firestormsw.tinylist.data.ShoppingItem
import com.firestormsw.tinylist.ui.Add
import com.firestormsw.tinylist.ui.Remove
import ulid.ULID
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddItemSheet(
    isOpen: Boolean,
    onDismiss: () -> Unit,
    onSave: (ShoppingItem) -> Unit,
    editItem: ShoppingItem? = null
) {
    var itemText by remember { mutableStateOf("") }
    var itemQuantity by remember { mutableIntStateOf(1) }
    var itemUnit by remember { mutableStateOf("") }

    val focusRequester = remember { FocusRequester() }

    if (isOpen) {
        ModalBottomSheet(
            onDismissRequest = onDismiss,
            dragHandle = {
                Column(
                    modifier = Modifier
                        .width(50.dp)
                        .height(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Surface(
                        modifier = Modifier
                            .width(32.dp)
                            .height(4.dp)
                            .padding(vertical = 8.dp)
                    ) {}

                    val dragHandleColor = MaterialTheme.colorScheme.tertiaryContainer;

                    Canvas(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                    ) {
                        drawRoundRect(
                            color = dragHandleColor,
                            cornerRadius = CornerRadius(16.0f, 16.0f)
                        )
                    }
                }
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .navigationBarsPadding()
                    .imePadding(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = itemText,
                        onValueChange = { itemText = it },
                        label = { Text("Item name") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions.Default.copy(capitalization = KeyboardCapitalization.Words),
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(focusRequester),
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = if (itemQuantity > 0) {
                            itemQuantity.toString()
                        } else {
                            ""
                        },
                        onValueChange = {
                            itemQuantity = try {
                                it.toInt()
                            } catch (e: NumberFormatException) {
                                0
                            }
                        },
                        label = { Text("Quantity") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(0.3333f)
                    )
                    OutlinedTextField(
                        value = itemUnit,
                        onValueChange = { itemUnit = it },
                        label = { Text("Unit") },
                        singleLine = true,
                        modifier = Modifier.weight(0.3333f)
                    )

                    IconButton(
                        onClick = {
                            if (itemQuantity > 1) {
                                itemQuantity -= 1
                            }
                        },
                        modifier = Modifier.weight(0.1666f)
                    ) {
                        Icon(Remove, contentDescription = "Decrease quantity")
                    }
                    IconButton(
                        onClick = { itemQuantity += 1 },
                        modifier = Modifier.weight(0.1666f)
                    ) {
                        Icon(Add, contentDescription = "Increase quantity")
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = {
                            onSave(
                                ShoppingItem(
                                    id = ULID.randomULID(),
                                    text = itemText,
                                    isChecked = false,
                                    quantity = itemQuantity,
                                    unit = itemUnit
                                )
                            )
                        },
                        enabled = itemText.isNotEmpty(),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            if (editItem == null) {
                                "Add item to list"
                            } else {
                                "Save item"
                            }
                        )
                    }
                }
            }

            LaunchedEffect(Unit) {
                focusRequester.requestFocus()
                itemText = editItem?.text ?: ""
                itemQuantity = editItem?.quantity ?: 1
                itemUnit = editItem?.unit ?: ""
            }
        }
    }
}