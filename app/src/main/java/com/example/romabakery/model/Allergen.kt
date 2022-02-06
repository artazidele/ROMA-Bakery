package com.example.romabakery.model

data class Allergen(
    val id: String = "",
    val title: String = "",
    val madeBy: String = "",
    val editedBy: ArrayList<String> = ArrayList(),
    val editedOn: ArrayList<String> = ArrayList()
)
