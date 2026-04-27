package com.example.lostandfound.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.lostandfound.ui.model.PostType

class CreateAdvertViewModel : ViewModel() {

    var postType by mutableStateOf(PostType.LOST)
        private set
    // private set -> means
    // Anyone can read (get) the value
    // Only this class (viewModel) can modify (set) it

    var name by mutableStateOf("")
        private set

    var phone by mutableStateOf("")
        private set

    var description by mutableStateOf("")
        private set

    var dateMillis by mutableStateOf<Long?>(null)
        private set

    var location by mutableStateOf("")
        private set

    var showErrors by mutableStateOf(false)
        private set

    fun onPostTypeChange(type: PostType) {
        postType = type
    }

    fun onNameChange(value: String) {
        name = value
    }

    fun onPhoneChange(value: String) {
        phone = value
    }

    fun onDescriptionChange(value: String) {
        description = value
    }

    fun onDateChange(millis: Long?) {
        dateMillis = millis
    }

    fun onLocationChange(value: String) {
        location = value
    }

    fun isValid(): Boolean {
        return name.isNotBlank() &&
                phone.isNotBlank() &&
                dateMillis != null &&
                location.isNotBlank()
    }

    fun onSave(onSuccess: () -> Unit) {
        showErrors = true

        if (isValid()) {
            onSuccess()
        }
    }

}