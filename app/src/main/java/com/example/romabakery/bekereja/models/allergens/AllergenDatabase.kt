package com.example.romabakery.bekereja.models.allergens

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class AllergenDatabase {
    val db = FirebaseFirestore.getInstance()

    fun getAllergens(): Task<QuerySnapshot> {
        return db.collection("Allergen")
            .orderBy("title")
            .get()
    }

    fun deleteAllergen(id: String): Task<Void> {
        return db.collection("Allergen").document(id)
            .delete()
    }

    fun getAllergen(id: String): Task<DocumentSnapshot> {
        return db.collection("Allergen")
            .document(id)
            .get()
    }

    fun addAllergen(allergen: AllergenDataClass): Task<Void> {
        return db.collection("Allergen").document(allergen.id)
            .set(allergen)
    }

    fun updateAllergen(allergen: AllergenDataClass): Task<Void> {
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
}
