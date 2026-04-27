package com.example.lostandfound.ui.screens

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lostandfound.ui.model.Screen

@Composable
fun AppNavHost() {

    val navController = rememberNavController()

    NavHost(navController, startDestination = Screen.MainMenu.route) {

        composable(Screen.MainMenu.route) {
            MainMenuItems(navController)
        }

        composable(Screen.CreateAdvert.route) {

            CreateAdvertScreen(onSaveClick = {
                navController.popBackStack()
            })
        }

        composable(Screen.AdvertList.route) {
            AdvertListScreen()
        }
    }
}