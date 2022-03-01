package com.example.romabakery.bekereja.models.allergens

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class AllergenDatabase {
    val db = FirebaseFirestore.getInstance()

    suspend fun getAllergens(): Task<QuerySnapshot> {
        return db.collection("Allergen")
            .orderBy("title")
            .get()
    }

    suspend fun deleteAllergen(id: String): Task<Void> {
        return db.collection("Allergen").document(id)
            .delete()
    }

    suspend fun getAllergen(id: String): Task<DocumentSnapshot> {
        return db.collection("Allergen")
            .document(id)
            .get()
    }

    suspend fun addAllergen(allergen: AllergenDataClass): Task<Void> {
        return db.collection("Allergen").document(allergen.id)
            .set(allergen)
    }

    suspend fun updateAllergen(allergen: AllergenDataClass): Task<Void> {
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
