package com.example.romabakery.model

import android.content.ClipData
import android.content.ContentValues.TAG
import android.util.Log
import com.example.romabakery.viewmodel.ChooseItemsViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay

class ConfectioneryItemFirebase {
    val db = FirebaseFirestore.getInstance()

    suspend fun getOneItems(id: String): Task<DocumentSnapshot> {
        return db.collection("ConfectioneryItem")
            .document(id)
            .get()
    }

    suspend fun getAllItems(): Task<QuerySnapshot> {
        return db.collection("ConfectioneryItem")
            .get()
    }

    suspend fun getItemsInProduction(): Task<QuerySnapshot> {
        return db.collection("ConfectioneryItem")
            .whereEqualTo("notInProduction", false)
            .get()
    }

    suspend fun getItemsNotInProduction(): Task<QuerySnapshot> {
        return db.collection("ConfectioneryItem")
            .whereEqualTo("notInProduction", true)
            .get()
    }

    suspend fun addItem(item: ConfectioneryItem): Task<Void> {
        return db.collection("ConfectioneryItem").document(item.id)
            .set(item)
    }

    suspend fun updateItem(item: ConfectioneryItem): Task<Void> {
        return db.collection("ConfectioneryItem").document(item.id)
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

    suspend fun deleteItem(id: String): Task<Void> {
        return db.collection("ConfectioneryItem").document(id)
            .delete()
    }

}
