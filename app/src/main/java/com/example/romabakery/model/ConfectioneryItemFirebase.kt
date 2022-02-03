package com.example.romabakery.model

import android.content.ClipData
import com.example.romabakery.viewmodel.ChooseItemsViewModel
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject

class ConfectioneryItemFirebase {
    val db = FirebaseFirestore.getInstance()
//    suspend fun getConfectioneryItems() {
//        var itemList: ArrayList<ConfectioneryItem> = ArrayList()
//        db.collection("ConfectioneryItem")
//            .whereEqualTo("notInProduction", false)
//            .get()
//            .addOnSuccessListener { documents ->
//                for (document in documents) {
//                    val bun = document.toObject<ConfectioneryItem>()
//                    itemList.add(bun)
//                }
//                val itemListToReturn: List<ConfectioneryItem> = itemList
//                ChooseItemsViewModel().updateItems(itemListToReturn)
//            }
//    }
    suspend fun getItems(): List<ConfectioneryItem> {
//        var itemList: ArrayList<ConfectioneryItem> = ArrayList()
//        db.collection("ConfectioneryItem")
//            .whereEqualTo("notInProduction", false)
//            .get()
//            .addOnSuccessListener { documents ->
//                for (document in documents) {
//                    val bun = document.toObject<ConfectioneryItem>()
//                    itemList.add(bun)
//                }
//            }
//        val itemListToReturn: List<ConfectioneryItem> = itemList
        return getItemsFromDb()//itemListToReturn
    }

    private fun getItemsFromDb(): List<ConfectioneryItem>{
        var itemList: ArrayList<ConfectioneryItem> = ArrayList()
        db.collection("ConfectioneryItem")
            .whereEqualTo("notInProduction", false)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val bun = document.toObject<ConfectioneryItem>()
                    itemList.add(bun)
                }
            }
        val itemListToReturn: List<ConfectioneryItem> = itemList
        return itemListToReturn
    }
    suspend fun getAllItems() = db
        .collection("ConfectioneryItem")
//        .whereEqualTo("notInProduction", false)
        .get()
//        .result

//    suspend fun getDataFromFireStore(childName : String)
//            : DocumentSnapshot?{
//        return try{
//            val data = db
//                .collection("ConfectioneryItem")
//                .document(childName)
//                .get()
//                .await()
//            data
//        }catch (e : Exception){
//            null
//        }
//    }
}

