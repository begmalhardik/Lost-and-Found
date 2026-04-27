package com.example.lostandfound.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lostandfound.ui.model.AdvertItem
import com.example.lostandfound.ui.model.Category
import com.example.lostandfound.ui.model.PostType
import com.example.lostandfound.viewmodel.AdvertListViewModel

@Composable
fun AdvertListScreen(
    viewModel: AdvertListViewModel = viewModel()
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "LOST & FOUND ITEMS",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Search ONLY (by category)
        OutlinedTextField(
            value = viewModel.searchQuery,
            onValueChange = viewModel::onSearchChange,
            label = { Text("Search by category (e.g. Electronics)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // List
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(viewModel.filteredItems) { item ->
                AdvertItemCard(item)
            }
        }
    }
}

@Composable
fun AdvertItemCard(item: AdvertItem) {

    val prefix = if (item.postType == PostType.LOST) "Lost" else "Found"

    OutlinedCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = item.title,
            modifier = Modifier.padding(16.dp)
        )
    }
}