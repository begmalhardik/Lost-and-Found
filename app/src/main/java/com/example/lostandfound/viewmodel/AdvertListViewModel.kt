package com.example.lostandfound.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.lostandfound.ui.model.AdvertItem
import com.example.lostandfound.ui.model.Category
import com.example.lostandfound.ui.model.PostType

class AdvertListViewModel : ViewModel() {

    // Fake data (replace with DB later)
    private val allItems = listOf(
        AdvertItem(1, "Lost Key", PostType.LOST, Category.KEYS),
        AdvertItem(2, "Found Apple AirPods", PostType.FOUND, Category.ELECTRONICS),
        AdvertItem(3, "Lost Dog", PostType.LOST, Category.PETS),
        AdvertItem(4, "Found Wallet", PostType.FOUND, Category.WALLETS)
    )

    var searchQuery by mutableStateOf("")
        private set


    val filteredItems: List<AdvertItem>
        get() = allItems.filter { item ->
            item.category.name.contains(searchQuery, ignoreCase = true)
        }


    fun onSearchChange(value: String) {
        searchQuery  = value
    }


}