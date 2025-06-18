package com.example.myapplication

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication.screens.AddItemScreen
import com.example.myapplication.screens.PackingListScreen
import androidx.navigation.compose.rememberNavController

@Composable
fun PackingListApp() {
    val navController = rememberNavController()

    // Parallel arrays to store packing items data
    val names = remember { mutableStateListOf<String>() }
    val categories = remember { mutableStateListOf<String>() }
    val quantities = remember { mutableStateListOf<Int>() }
    val comments = remember { mutableStateListOf<String>() }

    NavHost(
        navController = navController,
        startDestination = "add_item"
    ) {
        composable("add_item") {
            AddItemScreen(
                navController = navController,
                names = names,
                categories = categories,
                quantities = quantities,
                comments = comments
            )
        }
        composable("packing_list") {
            PackingListScreen(
                navController = navController,
                names = names,
                categories = categories,
                quantities = quantities,
                comments = comments
            )
        }
    }
}