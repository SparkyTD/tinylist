package com.firestormsw.tinylist.ui.components

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import com.firestormsw.tinylist.R
import com.firestormsw.tinylist.data.ShoppingList
import ulid.ULID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddListSheet(
    isOpen: Boolean,
    onDismiss: () -> Unit,
    onSave: (ShoppingList) -> Unit,
    editList: ShoppingList? = null,
) {
    var itemText by remember { mutableStateOf("") }
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

                    val dragHandleColor = MaterialTheme.colorScheme.tertiaryContainer

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
                        label = { Text(stringResource(R.string.list_name)) },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions.Default.copy(capitalization = KeyboardCapitalization.Words),
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(focusRequester),
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = {
                            onSave(
                                ShoppingList(
                                    id = editList?.id ?: ULID.randomULID(),
                                    name = itemText,
                                )
                            )
                        },
                        enabled = itemText.isNotEmpty(),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            if (editList == null) {
                                stringResource(R.string.create_list)
                            } else {
                                stringResource(R.string.save_list)
                            }
                        )
                    }
                }
            }

            LaunchedEffect(Unit) {
                focusRequester.requestFocus()
                itemText = editList?.name ?: ""
            }
        }
    }
}