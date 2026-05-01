package com.example.lostandfound.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lostandfound.model.toDomain
import com.example.lostandfound.ui.model.AdvertItem
import com.example.lostandfound.ui.model.Category
import com.example.lostandfound.ui.model.PostType
import com.example.lostandfound.viewmodel.AdvertListViewModel

@Composable
fun AdvertListScreen(
    viewModel: AdvertListViewModel = viewModel(),
    onItemClick: (AdvertItem) -> Unit
) {

    val adverts by viewModel.adverts.collectAsState()
    var expanded by remember { mutableStateOf(false) }

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

        Box {
            OutlinedTextField(
                value = viewModel.searchQuery,
                onValueChange = {},
                readOnly = true,
                label = { Text("Filter by Category") },
                modifier = Modifier.fillMaxWidth(),
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
                            Text(category.name.lowercase().replaceFirstChar { it.uppercase() })
                        },
                        onClick = {
                            viewModel.onSearchChange(category.name)
                            expanded = false
                        }
                    )
                }
            }
        }

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

    // val prefix = if (item.postType == PostType.LOST) "Lost" else "Found"

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