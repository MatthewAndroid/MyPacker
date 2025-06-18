package com.example.myapplication.screens

class Functions {
    public fun AddItemToList(
        name: String,
        category: String,
        comment: String,
        quantity: Int,
        names: MutableList<String>,
        categories: MutableList<String>,
        quantities: MutableList<Int>,
        comments: MutableList<String>
    ): AddItemResult {
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
}

data class AddItemResult(
    val success: Boolean,
    val errorMessage: String = ""
)