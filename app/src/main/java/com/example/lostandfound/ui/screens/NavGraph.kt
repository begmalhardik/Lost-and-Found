package com.example.lostandfound.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lostandfound.model.toDomain
import com.example.lostandfound.ui.model.Screen
import com.example.lostandfound.viewmodel.AdvertListViewModel

@Composable
fun AppNavHost() {

    val navController = rememberNavController()
    val advertListViewModel: AdvertListViewModel = viewModel()

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
            AdvertListScreen(
                onItemClick = { item ->
                    navController.navigate(
                        Screen.RemoveAdvert.createRoute(item.id)
                    )
                }
            )
        }

        composable(Screen.RemoveAdvert.route) { backStackEntry ->

            val itemId = backStackEntry.arguments
                ?.getString("itemId")
                ?.toIntOrNull()

//            val item = advertListViewModel.filteredItems.find { it.id == itemId }

            val adverts by advertListViewModel.adverts.collectAsState()

            val item = adverts.find { it.id == itemId }

            item?.let {
                RemoveAdvertScreen(
                    item = it,
                    onRemove = {
                        advertListViewModel.deleteAdvert(it)
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}