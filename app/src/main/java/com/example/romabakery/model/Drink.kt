package com.example.romabakery.model

data class Drink(
    val id: String = "",
    val coffee: Boolean = false,
    val tea: Boolean = false,
    val juice: Boolean = false,
    val other: Boolean = false,
    val title: String = "",
    val eiro: Int = 0,
    val centi: Int = 0,
    val capacity: Int = 0,
    val madeBy: String = "",
    val editedBy: ArrayList<String> = ArrayList(),
    val editedOn: ArrayList<String> = ArrayList(),
    val notInProduction: Boolean = false,
    val notInProductionBy: String = ""
)
