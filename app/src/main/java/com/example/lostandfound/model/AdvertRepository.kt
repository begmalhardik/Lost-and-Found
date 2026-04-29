package com.example.lostandfound.model

class AdvertRepository(private val dao: AdvertDao) {

    fun getAllAdverts() = dao.getAllAdverts()

    fun search(query: String) = dao.searchByCategory(query)

    suspend fun insert(advert: AdvertEntity) {
        dao.insert(advert)
    }

    suspend fun delete(advert: AdvertEntity) {
        dao.delete(advert)
    }
}