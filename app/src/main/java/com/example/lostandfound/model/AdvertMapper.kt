package com.example.lostandfound.model

import com.example.lostandfound.ui.model.AdvertItem
import com.example.lostandfound.ui.model.Category
import com.example.lostandfound.ui.model.PostType

fun AdvertEntity.toDomain(): AdvertItem {
    return AdvertItem(
        id = id,
        title = title,
        postType = PostType.valueOf(postType),
        category = Category.valueOf(category)
    )
}

fun AdvertItem.toEntity(): AdvertEntity {
    return AdvertEntity(
        id = id,
        title = title,
        postType = postType.name,
        category = category.name,
        name = "",
        phone = "",
        description = "",
        date = System.currentTimeMillis(),
        location = "",
        imageUri = null
    )
}