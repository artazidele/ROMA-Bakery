package com.example.romabakery.mvvm

import android.content.ContentValues.TAG
import android.util.Log
import com.example.romabakery.model.Allergen
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class MyFirebase {

    val db = FirebaseFirestore.getInstance()

    fun getAllAllergens(): Task<QuerySnapshot> {
        return db.collection("Allergen")
            .orderBy("title")
            .get()
    }

    fun deleteAllergen(id: String): Task<Void> {
        return db.collection("Allergen").document(id)
            .delete()
    }

    fun getOneAlllergen(id: String): Task<DocumentSnapshot> {
        return db.collection("Allergen")
            .document(id)
            .get()
    }

    fun addAllergen(allergen: Allergen): Task<Void> {
        return db.collection("Allergen").document(allergen.id)
            .set(allergen)
    }

    fun updateAllergen(allergen: Allergen): Task<Void> {
        return db.collection("Allergen").document(allergen.id)
            .update(
                mapOf(
                    "title" to allergen.title,
                    "madeBy" to allergen.madeBy,
                    "editedBy" to allergen.editedBy,
                    "editedOn" to allergen.editedOn
                )
            )
    }

    fun getAllConfectioneryItems(): Task<QuerySnapshot> {
        return db.collection("ConfectioneryItem")
            .orderBy("title")
            .get()
    }

}