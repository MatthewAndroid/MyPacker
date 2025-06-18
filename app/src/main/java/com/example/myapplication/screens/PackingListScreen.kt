package com.example.myapplication.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun PackingListScreen(
    navController: NavController,
    names: MutableList<String>,
    categories: MutableList<String>,
    quantities: MutableList<Int>,
    comments: MutableList<String>,){

    var allPackingItems by remember { mutableStateOf(false) }
    var filteredPackingItems by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()
        .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)) {

        Text(
            text = "MyPacker",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
        )

        Text(
            text = "Packing List",
            fontSize = 24.sp,
            fontWeight = FontWeight.Medium,
        )

        Row (modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly){
            Button(onClick = {
                allPackingItems = true
                filteredPackingItems = false
            }) {
                Text(text = "All Items")
            }

            Button(onClick = {
                allPackingItems = false
                filteredPackingItems = true
            }) {
                Text(text = "Filtered Items")
            }
        }

        val listToShow = if (allPackingItems) {
            getAllPackingItems()
        } else {
            getFilteredPackingItems()
        }

    }

    fun getAllPackingItems(): List<String> {
        return names.mapIndexed { index, name ->
            "Name: $name, Category: ${categories[index]}, Quantity: ${quantities[index]}, Comment: ${comments[index]}"
        }
    }

    fun getFilteredPackingItems(): List<String> {
        var list = getAllPackingItems()
        var resultList: List<String> = listOf()
        list.forEach() { item ->
            if (quantities[names.indexOf(item)] > 2) {

            }
        }
    }

}