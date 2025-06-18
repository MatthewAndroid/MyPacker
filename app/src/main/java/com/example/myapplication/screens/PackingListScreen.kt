package com.example.myapplication.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.data.models.PackingItem

@Composable
fun PackingListScreen(
    navController: NavController,
    names: List<String>,
    categories: List<String>,
    quantities: List<Int>,
    comments: List<String>
) {
    var showAllItems by remember { mutableStateOf(false) }
    var showFilteredItems by remember { mutableStateOf(false) }

    // Function to get all items using loops
    fun getAllItems(
        itemNames: List<String>,
        categories: List<String>,
        quantities: List<Int>,
        comments: List<String>
    ): List<PackingItem> {
        val items = mutableListOf<PackingItem>()

        // Using a for loop to iterate throug
        for (i in itemNames.indices) {
            items.add(
                PackingItem(
                    name = itemNames[i],
                    category = categories[i],
                    quantity = quantities[i],
                    comment = comments[i]
                )
            )
        }

        return items
    }

    // Function to get filtered items (quantity >= 2) using loops
    fun getFilteredItems(
        itemNames: List<String>,
        categories: List<String>,
        quantities: List<Int>,
        comments: List<String>
    ): List<PackingItem> {
        val filteredItems = mutableListOf<PackingItem>()

        // Using a for loop to filter items
        for (i in itemNames.indices) {
            if (quantities[i] >= 2) {
                filteredItems.add(
                    PackingItem(
                        name = itemNames[i],
                        category = categories[i],
                        quantity = quantities[i],
                        comment = comments[i]
                    )
                )
            }
        }

        return filteredItems
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Travel Packing List Manager",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Text(
            text = "Screen Two - View Items",
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        )

        // Display buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = {
                    showAllItems = true
                    showFilteredItems = false
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Display Packing List")
            }

            Button(
                onClick = {
                    showFilteredItems = true
                    showAllItems = false
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Items with Qty 2+")
            }
        }

        // Display items based on selection
        if (showAllItems || showFilteredItems) {
            val itemsToShow = if (showFilteredItems) {
                getFilteredItems(names, categories, quantities, comments)
            } else {
                getAllItems(names, categories, quantities, comments)
            }

            if (itemsToShow.isEmpty()) {
                Text(
                    text = if (showFilteredItems) "No items with quantity 2 or more found."
                    else "No items in packing list yet.",
                    fontSize = 16.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            } else {
                Text(
                    text = if (showFilteredItems) "Items with Quantity 2 or More:"
                    else "All Packing Items:",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )

                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    itemsIndexed(itemsToShow) { index, item ->
                        PackingItemCard(
                            itemName = item.name,
                            category = item.category,
                            quantity = item.quantity,
                            comment = item.comment,
                            index = index + 1
                        )
                    }
                }
            }
        } else {
            Text(
                text = "Select a button above to view your packing items.",
                fontSize = 16.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }

        // Back button
        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Back to Main Screen")
        }
    }


}

@Composable
fun PackingItemCard(
    itemName: String,
    category: String,
    quantity: Int,
    comment: String,
    index: Int
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "$index. $itemName",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Category: $category",
                fontSize = 14.sp
            )
            Text(
                text = "Quantity: $quantity",
                fontSize = 14.sp
            )
            if (comment.isNotEmpty()) {
                Text(
                    text = "Comments: $comment",
                    fontSize = 14.sp
                )
            }
        }
    }
}