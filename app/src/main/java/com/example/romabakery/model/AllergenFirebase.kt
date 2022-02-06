package com.example.romabakery.model

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.toObject

class AllergenFirebase {
    val db = FirebaseFirestore.getInstance()

    suspend fun getOneAllergen(id: String): Task<DocumentSnapshot> {
        return db.collection("Allergen")
            .document(id)
            .get()
    }


    suspend fun getAllAllergens(): Task<QuerySnapshot> {
        return db.collection("Allergen")
            .get()
    }

    suspend fun addAllergen(allergen: Allergen): Task<Void> {
        return db.collection("Allergen").document(allergen.id)
            .set(allergen)
    }

    suspend fun updateAllergen(allergen: Allergen): Task<Void> {
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

    suspend fun deleteAllergen(id: String): Task<Void> {
        return db.collection("Allergen").document(id)
            .delete()
    }

//    suspend fun checkIfAllergenCanBeDeleted(id: String): ArrayList<ConfectioneryItem> {
//        var itemList: ArrayList<ConfectioneryItem> = ArrayList()
//        ConfectioneryItemFirebase().getAllItems()
//            .addOnSuccessListener { documents ->
//                for (document in documents) {
//                    val oneItem = document.toObject<ConfectioneryItem>()
//                    for (allergen in oneItem.containsAllergens) {
//                        if (allergen == id) {
//                            itemList.add(oneItem)
//                            break
//                        }
//                    }
//                }
//            }
//        return itemList
//    }
}