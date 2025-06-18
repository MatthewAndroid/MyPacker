package com.example.myapplication.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.data.models.AddItemResult
import kotlin.system.exitProcess

@Composable
fun AddItemScreen(
    navController: NavController,
    names: MutableList<String>,
    categories: MutableList<String>,
    quantities: MutableList<Int>,
    comments: MutableList<String>
) {
    var itemName by remember { mutableStateOf(TextFieldValue()) }
    var category by remember { mutableStateOf(TextFieldValue()) }
    var quantity by remember { mutableStateOf(TextFieldValue()) }
    var comment by remember { mutableStateOf(TextFieldValue()) }
    var errorMessage by remember { mutableStateOf("") }

    val context = LocalContext.current

    fun addItemToList(
        name: String,
        category: String,
        quantityStr: String,
        comment: String,
        names: MutableList<String>,
        categories: MutableList<String>,
        quantities: MutableList<Int>,
        comments: MutableList<String>
    ): AddItemResult {
        try {
            // Input validation
            if (name.trim().isEmpty()) {
                return AddItemResult(false, "Please enter an item name")
            }

            if (category.trim().isEmpty()) {
                return AddItemResult(false, "Please enter a category")
            }

            if (quantityStr.trim().isEmpty()) {
                return AddItemResult(false, "Please enter a quantity")
            }

            val quantity = quantityStr.trim().toIntOrNull()
            if (quantity == null || quantity <= 0) {
                return AddItemResult(false, "Please enter a valid quantity (positive number)")
            }

            // Add to parallel arrays
            names.add(name.trim())
            categories.add(category.trim())
            quantities.add(quantity)
            comments.add(comment.trim())

            return AddItemResult(true)

        } catch (e: Exception) {
            return AddItemResult(false, "Error adding item: ${e.message}")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "MyPacker",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Text(
            text = "Screen One - Add Items",
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        )

        // Input fields
        OutlinedTextField(
            value = itemName,
            onValueChange = {
                itemName = it
                errorMessage = ""
            },
            label = { Text("Item Name") },
            modifier = Modifier.fillMaxWidth(),
            isError = errorMessage.contains("name")
        )

        OutlinedTextField(
            value = category,
            onValueChange = {
                category = it
                errorMessage = ""
            },
            label = { Text("Category") },
            modifier = Modifier.fillMaxWidth(),
            isError = errorMessage.contains("category")
        )

        OutlinedTextField(
            value = quantity,
            onValueChange = {
                quantity = it
                errorMessage = ""
            },
            label = { Text("Quantity") },
            modifier = Modifier.fillMaxWidth(),
            isError = errorMessage.contains("quantity")
        )

        OutlinedTextField(
            value = comment,
            onValueChange = {
                comment = it
                errorMessage = ""
            },
            label = { Text("Comments") },
            modifier = Modifier.fillMaxWidth()
        )

        // Error message display
        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                fontSize = 14.sp
            )
        }

        // Add to Packing List Button
        Button(
            onClick = {
                val result = addItemToList(
                    itemName.text,
                    category.text,
                    quantity.text,
                    comment.text,
                    names,
                    categories,
                    quantities,
                    comments
                )

                if (result.isSuccess) {
                    // Clear fields after successful addition
                    itemName = TextFieldValue()
                    category = TextFieldValue()
                    quantity = TextFieldValue()
                    comment = TextFieldValue()
                    errorMessage = ""

                    Toast.makeText(context, "Item added successfully!", Toast.LENGTH_SHORT).show()
                } else {
                    errorMessage = result.errorMessage
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add to Packing List")
        }

        Spacer(modifier = Modifier.weight(1f))

        // Navigation buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = { navController.navigate("packing_list") },
                modifier = Modifier.weight(1f)
            ) {
                Text("View Packing List")
            }

            Button(
                onClick = { exitProcess(0) },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text("Exit App")
            }
        }
    }
}