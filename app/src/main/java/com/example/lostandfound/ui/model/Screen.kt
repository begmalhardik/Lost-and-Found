package com.example.lostandfound.ui.model

sealed class Screen(val route: String) {

    object MainMenu : Screen("menu_item")
    object CreateAdvert : Screen("create_advertisement")
    object AdvertList : Screen("advertisement_list")

}