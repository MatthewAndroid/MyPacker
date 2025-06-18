package com.example.myapplication.screens

import android.service.autofill.OnClickAction
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.screens.Functions
import kotlin.system.exitProcess

@Composable
fun AddItemScreen(
    navController: NavController,
    names: MutableList<String>,
    categories: MutableList<String>,
    quantities: MutableList<Int>,
    comments: MutableList<String>,
){
    val context = LocalContext.current
    var name by remember{ mutableStateOf(TextFieldValue()) }
    var category by remember{ mutableStateOf(TextFieldValue()) }
    var quantity by remember{ mutableStateOf(TextFieldValue()) }
    var comment by remember{ mutableStateOf(TextFieldValue()) }
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ){
        Text(
            text = "MyPacker",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Text(
            text = "Add Item",
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium
        )

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxSize(),
            placeholder = { Text("Enter item name") },
            leadingIcon = { Icon(
                imageVector = Icons.Default.Info,
                contentDescription = "Name Icon"
            ) }
        )
        OutlinedTextField(
            value = category,
            onValueChange = { category = it },
            label = { Text("Category") },
            modifier = Modifier.fillMaxSize(),
            placeholder = { Text("Enter item category") },
            leadingIcon = { Icon(
                imageVector = Icons.Default.Build,
                contentDescription = "Category Icon"
            ) }
        )
        OutlinedTextField(
            value = quantity,
            onValueChange = { quantity = it },
            label = { Text("Quantity") },
            modifier = Modifier.fillMaxSize(),
            placeholder = { Text("Enter item quantity") },
            leadingIcon = { Icon(
                imageVector = Icons.Default.ShoppingCart,
                contentDescription = "Quantity Icon"
            ) }
        )
        OutlinedTextField(
            value = comment,
            onValueChange = { comment = it },
            label = { Text("Comment") },
            modifier = Modifier.fillMaxSize(),
            placeholder = { Text("Enter item comment") },
            leadingIcon = { Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Comment Icon"
            ) }
        )

        if (errorMessage.isNotEmpty()){
            Text(
                text = errorMessage,
                color = androidx.compose.ui.graphics.Color.Red,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
        
        Button(
            onClick = {
                val result = AddItemToList(
                    name.text,
                    category.text,
                    comment.text,
                    quantity.text.toIntOrNull() ?: 0,
                    names,
                    categories,
                    quantities,
                    comments
                )

                if(result.success){
                    name = TextFieldValue()
                    category = TextFieldValue()
                    quantity = TextFieldValue()
                    comment = TextFieldValue()
                    errorMessage = ""

                    Toast.makeText( context,
                        "Item added successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else{
                    errorMessage = result.errorMessage
                }

            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "✅ Add")

        }

        Row (modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly){
            Button(
                onClick = { navController.navigate("list_screen") },
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "📝 List")
            }
            Button(
                onClick = { exitProcess(0) },
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "🚪 Exit")
            }
        }

    }

}

fun AddItemToList(
                    name: String,
                  category: String,
                  comment: String,
                  quantity: Int,
                  names: MutableList<String>,
                  categories: MutableList<String>,
                  quantities: MutableList<Int>,
                  comments: MutableList<String>): AddItemResult {
    if(name.trim().isEmpty()){
        return AddItemResult(
            success = false,
            errorMessage = "Name cannot be empty"
        )
    }

    if(category.trim().isEmpty()){
        return AddItemResult(
            success = false,
            errorMessage = "Category cannot be empty"
        )
    }

    if(quantity <= 0){
        return AddItemResult(
            success = false,
            errorMessage = "Quantity must be greater than 0"
        )
    }

    names.add(name.trim())
    categories.add(category.trim())
    quantities.add(quantity)
    comments.add(comment.trim())
    return AddItemResult(
        success = true
    )
}


@Preview
@Composable
fun AddItemScreenPreview() {
    AddItemScreen(
        navController = NavController(context = LocalContext.current),
        names = mutableListOf("Item1", "Item2"),
        categories = mutableListOf("Category1", "Category2"),
        quantities = mutableListOf(1, 2),
        comments = mutableListOf("Comment1", "Comment2")
    )
}