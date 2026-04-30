package com.example.lostandfound.ui.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
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

    Column(
        modifier = Modifier
            .fillMaxSize()
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

        Button(
            onClick = { launcher.launch("image/*") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Upload Image")
        }

        Spacer(modifier = Modifier.weight(1f))

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