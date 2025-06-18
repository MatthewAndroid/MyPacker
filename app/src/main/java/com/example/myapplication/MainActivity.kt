package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.screens.AddItemScreen
import com.example.myapplication.ui.theme.MyPackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyPackerTheme {

                val navController = rememberNavController()

                AddItemScreen(
                    navController = navController,
                    names = mutableListOf(),
                    categories = mutableListOf(),
                    quantities = mutableListOf(),
                    comments = mutableListOf()
                )
                }
            }
        }
    }