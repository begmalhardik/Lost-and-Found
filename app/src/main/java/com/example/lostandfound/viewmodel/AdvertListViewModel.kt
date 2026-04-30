package com.example.lostandfound.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lostandfound.model.AdvertEntity
import com.example.lostandfound.model.AdvertRepository
import com.example.lostandfound.model.AppDatabase
import com.example.lostandfound.ui.model.Category
import com.example.lostandfound.ui.model.PostType
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AdvertListViewModel(application: Application) : AndroidViewModel(application) {

    // Fake data (replace with DB later)
//    private val allItems = listOf(
//        AdvertItem(1, "Lost Key", PostType.LOST, Category.KEYS),
//        AdvertItem(2, "Found Apple AirPods", PostType.FOUND, Category.ELECTRONICS),
//        AdvertItem(3, "Lost Dog", PostType.LOST, Category.PETS),
//        AdvertItem(4, "Found Wallet", PostType.FOUND, Category.WALLETS)
//    )

    private val dao = AppDatabase.getDatabase(application).advertDao()
    private val repository = AdvertRepository(dao)

    var searchQuery by mutableStateOf("")
        private set


//    val filteredItems: List<AdvertItem>
//        get() = allItems.filter { item ->
//            item.category.name.contains(searchQuery, ignoreCase = true)
//        }

    val adverts = repository.getAllAdverts().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        emptyList()
    )


    fun onSearchChange(value: String) {
        searchQuery  = value
    }

    fun deleteAdvert(item: AdvertEntity) {
        viewModelScope.launch {
            repository.delete(item)
        }
    }

}