package com.example.romabakery.bekereja.models.items

data class ItemDataClass(
    val id: String = "",
    val title: String = "",
    val bun: Boolean = false,
    val cake: Boolean = false,
    val cookies: Boolean = false,
    val maxADay: Int = 0,
    val eiro: Int = 0,
    val centi: Int = 0,
    val withoutFlour: Boolean = false,
    val withoutLactose: Boolean = false,
    val forVegetarians: Boolean = false,
    val forVegans: Boolean = false,
    val canBeOrderedFrom: String = "",
    val canBeOrderedUntil: String = "",
    val ingredients: String = "",
    val description: String = "",
    val weights: ArrayList<Int> = ArrayList(),
    val madeBy: String = "",
    val editedBy: ArrayList<String> = ArrayList(),
    val editedOn: ArrayList<String> = ArrayList(),
    val notInProduction: Boolean = false,
    val notInProductionBy: String = "",
    val containsAllergens: ArrayList<String> = ArrayList()
)
