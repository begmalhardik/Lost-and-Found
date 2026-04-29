package com.example.lostandfound.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AdvertDao {

    @Insert
    suspend fun insert(advert: AdvertEntity)

    @Delete
    suspend fun delete(advert: AdvertEntity)

    @Query("SELECT * FROM adverts ORDER BY date DESC")
    fun getAllAdverts(): Flow<List<AdvertEntity>>

    @Query("SELECT * FROM adverts WHERE category LIKE '%' || :query || '%'")
    fun searchByCategory(query: String): Flow<List<AdvertEntity>>

}