package com.example.myapplication.data.models

// for result hanfling
data class AddItemResult(
    val isSuccess: Boolean,
    val errorMessage: String = ""
)