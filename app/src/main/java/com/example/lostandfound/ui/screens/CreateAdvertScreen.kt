package com.example.lostandfound.ui.screens

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.lostandfound.ui.model.Category
import com.example.lostandfound.ui.model.PostType
import com.example.lostandfound.viewmodel.CreateAdvertViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateAdvertScreen(
    viewModel: CreateAdvertViewModel = viewModel(),
    onSaveClick: () -> Unit
) {

    val datePickerState = rememberDatePickerState()
    var showDatePicker by remember { mutableStateOf(false) }

    val formattedDate = viewModel.dateMillis?.let {
        SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date(it))
    } ?: ""
    val keyboardController = LocalSoftwareKeyboardController.current
    val interactionSource = remember { MutableInteractionSource() }
    val context = LocalContext.current

    // for image
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        viewModel.onImageSelected(uri?.toString())
    }

    val scrollState = rememberScrollState()


    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        Column(
            modifier = Modifier
                .weight(1f) // THIS makes only this part scrollable
                .verticalScroll(scrollState)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Text(
                text = "Create a new advert",
                style = MaterialTheme.typography.titleLarge
            )

            // Post Type
            Row(verticalAlignment = Alignment.CenterVertically) {

                Text("Post type:")

                Spacer(modifier = Modifier.width(8.dp))
                RadioButton(
                    selected = viewModel.postType == PostType.LOST,
                    onClick = { viewModel.onPostTypeChange(PostType.LOST) }
                )
                Text("Lost")

                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = viewModel.postType == PostType.FOUND,
                        onClick = { viewModel.onPostTypeChange(PostType.FOUND) }
                    )
                    Text("Found")
                }
            }

            // Name
            OutlinedTextField(
                value = viewModel.name,
                onValueChange = viewModel::onNameChange,
                label = { Text("Name") },
                isError = viewModel.showErrors && viewModel.name.isBlank(),
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        // Handle Done action here if needed
                        keyboardController?.hide() // To hide the keyboard when done
                    }
                )
            )

            // Phone
            OutlinedTextField(
                value = viewModel.phone,
                onValueChange = viewModel::onPhoneChange,
                label = { Text("Contact Number") },
                isError = viewModel.showErrors && viewModel.phone.isBlank(),
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        // Handle Done action here if needed
                        keyboardController?.hide() // To hide the keyboard when done
                    }
                )
            )

            // Description
            OutlinedTextField(
                value = viewModel.description,
                onValueChange = viewModel::onDescriptionChange,
                label = { Text("Description") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            )

            // Date Picker Trigger

            LaunchedEffect(interactionSource) {
                interactionSource.interactions.collect { interaction ->
                    if (interaction is PressInteraction.Release) {
                        showDatePicker = true
                    }
                }
            }
            OutlinedTextField(
                value = formattedDate,
                onValueChange = {},
                readOnly = true,
                label = { Text("Date") },
                interactionSource = interactionSource,
                isError = viewModel.showErrors && viewModel.dateMillis == null,
                modifier = Modifier.fillMaxWidth()
            )

            // Location
            OutlinedTextField(
                value = viewModel.location,
                onValueChange = viewModel::onLocationChange,
                label = { Text("Location") },
                isError = viewModel.showErrors && viewModel.location.isBlank(),
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        // Handle Done action here if needed
                        keyboardController?.hide() // To hide the keyboard when done
                    }
                )
            )

            // Category Dropdown
            var expanded by remember { mutableStateOf(false) }

            Box {
                OutlinedTextField(
                    value = viewModel.category.name.lowercase().replaceFirstChar { it.uppercase() },
                    onValueChange = {},
                    label = { Text("Category") },
                    modifier = Modifier.fillMaxWidth(),
                    readOnly = true,
                    trailingIcon = {
                        IconButton(onClick = { expanded = true }) {
                            Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                        }
                    }
                )

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    Category.entries.forEach { category ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = category.name.lowercase().replaceFirstChar {
                                        it.uppercase()
                                    }
                                )
                            },
                            onClick = {
                                viewModel.onCategorySelected(category)
                                Log.e("category", "Selected: $category")
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = { launcher.launch("image/*") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Upload Image")
            }

            Spacer(modifier = Modifier.weight(1f))

            viewModel.imageUri?.let { uri ->
                Image(
                    painter = rememberAsyncImagePainter(uri),
                    contentDescription = "Selected Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
        }

        // Save Button
        Button(
            onClick = {
//                viewModel.onSave {
//                    onSaveClick()
//                }
                viewModel.saveAdvert(context) {
                    onSaveClick()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("SAVE")
        }

        // Date Picker Dialog
        if (showDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(
                        onClick = {
                            viewModel.onDateChange(datePickerState.selectedDateMillis)
                            showDatePicker = false
                        }
                    ) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDatePicker = false }) {
                        Text("Cancel")
                    }
                },
            ) {
                DatePicker(state = datePickerState)
            }
        }
    }

}