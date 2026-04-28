package com.example.lostandfound.ui.model

sealed class Screen(val route: String) {

    object MainMenu : Screen("menu_item")
    object CreateAdvert : Screen("create_advertisement")
    object AdvertList : Screen("advertisement_list")
    object RemoveAdvert : Screen("remove_advert/{itemId}") {
        fun createRoute(itemId: Int) = "remove_advert/$itemId"
    }

}