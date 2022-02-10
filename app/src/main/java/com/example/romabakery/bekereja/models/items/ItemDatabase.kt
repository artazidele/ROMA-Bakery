package com.example.romabakery.bekereja.models.items

import com.example.romabakery.model.Allergen
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class ItemDatabase {
    val db = FirebaseFirestore.getInstance()

    fun getAllItems(): Task<QuerySnapshot> {
        return db.collection("Item")
            .orderBy("title")
            .get()
    }

    fun deleteItem(id: String): Task<Void> {
        return db.collection("Item").document(id)
            .delete()
    }

    fun getItem(id: String): Task<DocumentSnapshot> {
        return db.collection("Item")
            .document(id)
            .get()
    }

    fun addItem(item: ItemDataClass): Task<Void> {
        return db.collection("Item").document(item.id)
            .set(item)
    }

    fun updateItem(item: ItemDataClass): Task<Void> {
        return db.collection("Item").document(item.id)
            .update(
                mapOf(
                    "title" to item.title,
                    "bun" to item.bun,
                    "cake" to item.cake,
                    "cookies" to item.cookies,
                    "maxADay" to item.maxADay,
                    "eiro" to item.eiro,
                    "centi" to item.centi,
                    "withoutFlour" to item.withoutFlour,
                    "withoutLactose" to item.withoutLactose,
                    "forVegetarians" to item.withoutLactose,
                    "forVegans" to item.forVegans,
                    "canBeOrderedFrom" to item.canBeOrderedFrom,
                    "canBeOrderedUntil" to item.canBeOrderedUntil,
                    "ingredients" to item.ingredients,
                    "description" to item.description,
                    "weights" to item.weights,
                    "editedBy" to item.editedBy,
                    "editedOn" to item.editedOn,
                    "notInProduction" to item.notInProduction,
                    "notInProductionBy" to item.notInProductionBy,
                    "containsAllergens" to item.containsAllergens
                )
            )
    }
}
