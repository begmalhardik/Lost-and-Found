package com.example.lostandfound.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "adverts")
data class AdvertEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val title: String,
    val postType: String,
    val category: String,
    val name: String,
    val phone: String,
    val description: String,
    val date: Long,
    val location: String,

    val imageUri: String? // IMPORTANT (for image)
)