package com.example.romabakery.bekereja.models.allergens

data class AllergenDataClass(
    val id: String = "",
    val title: String = "",
    val madeBy: String = "",
    val editedBy: ArrayList<String> = ArrayList(),
    val editedOn: ArrayList<String> = ArrayList()
)
