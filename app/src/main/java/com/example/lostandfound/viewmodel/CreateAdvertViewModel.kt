package com.example.lostandfound.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lostandfound.model.AdvertEntity
import com.example.lostandfound.model.AdvertRepository
import com.example.lostandfound.model.AppDatabase
import com.example.lostandfound.ui.model.PostType
import kotlinx.coroutines.launch

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

    var imageUri by mutableStateOf<String?>(null)
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

    fun onImageSelected(uri: String?) {
        imageUri = uri
    }

    fun saveAdvert(context: Context, onSuccess: () -> Unit) {
        if (!isValid()) {
            showErrors = true
            return
        }

        val dao = AppDatabase.getDatabase(context).advertDao()
        val repo = AdvertRepository(dao)

        viewModelScope.launch {
            repo.insert(
                AdvertEntity(
                    title = "$postType - $name",
                    postType = postType.name,
                    category = "OTHER", // improve later
                    name = name,
                    phone = phone,
                    description = description,
                    date = dateMillis!!,
                    location = location,
                    imageUri = imageUri
                )
            )
            onSuccess()
        }
    }

}