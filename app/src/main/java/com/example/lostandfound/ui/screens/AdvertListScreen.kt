package com.example.lostandfound.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lostandfound.model.toDomain
import com.example.lostandfound.ui.model.AdvertItem
import com.example.lostandfound.ui.model.PostType
import com.example.lostandfound.viewmodel.AdvertListViewModel

@Composable
fun AdvertListScreen(
    viewModel: AdvertListViewModel = viewModel(),
    onItemClick: (AdvertItem) -> Unit
) {

    val adverts by viewModel.adverts.collectAsState()

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
            items(adverts) { item ->
                AdvertItemCard(item.toDomain(), onItemClick)
            }
        }
    }
}

@Composable
fun AdvertItemCard(item: AdvertItem, onClick: (AdvertItem) -> Unit) {

    val prefix = if (item.postType == PostType.LOST) "Lost" else "Found"

    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(item) }
    ) {
        Text(
            text = item.title,
            modifier = Modifier.padding(16.dp)
        )
    }
}