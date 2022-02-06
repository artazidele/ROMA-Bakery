package com.example.romabakery.model

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class DrinkFirebase {
    val db = FirebaseFirestore.getInstance()

    suspend fun getOneDrink(id: String): Task<DocumentSnapshot> {
        return db.collection("Drink")
            .document(id)
            .get()
    }

    suspend fun getAllDrinks(): Task<QuerySnapshot> {
        return db.collection("Drink")
            .get()
    }

    suspend fun addDrink(drink: Drink): Task<Void> {
        return db.collection("Drink").document(drink.id)
            .set(drink)
    }

    suspend fun updateDrink(drink: Drink): Task<Void> {
        return db.collection("Drink").document(drink.id)
            .update(
                mapOf(
                    "title" to drink.title,
                    "coffee" to drink.coffee,
                    "tea" to drink.tea,
                    "juice" to drink.juice,
                    "other" to drink.other,
                    "eiro" to drink.eiro,
                    "centi" to drink.centi,
                    "capacity" to drink.capacity,
                    "editedBy" to drink.editedBy,
                    "editedOn" to drink.editedOn,
                    "notInProduction" to drink.notInProduction,
                    "notInProductionBy" to drink.notInProductionBy
                )
            )
    }

    suspend fun deleteDrink(id: String): Task<Void> {
        return db.collection("Drink").document(id)
            .delete()
    }
}