package com.example.romabakery.model

import android.content.ClipData
import android.content.ContentValues.TAG
import android.util.Log
import com.example.romabakery.viewmodel.ChooseItemsViewModel
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay

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

    private fun getItemsFromDb(): List<ConfectioneryItem> {
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
//
//    suspend fun getConItems() {
//        val campaignsRef = db.collection('campaigns')
//        let activeRef = await campaignsRef.where('active', '==', true).select().get()
//        for (campaign of activeRef.docs) {
//            console.log(campaign.id)
//        }
//    }


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

//    suspend fun getAllItems() = awaitAll() for {
//        db.collection("ConfectioneryItem")
////        .whereEqualTo("notInProduction", false)
//            .get()
////        .result
//    }

    suspend fun getAllItems() = db
        .collection("ConfectioneryItem")
//        .whereEqualTo("notInProduction", false)
        .get()
//        .result

//    suspend fun getItemsAll(): List<ConfectioneryItem>? {
//        var itemList: ArrayList<ConfectioneryItem> = ArrayList()
//        db.collection("ConfectioneryItem")
////        .whereEqualTo("notInProduction", false)
//            .get()
//            .addOnSuccessListener { documents ->
//                for (document in documents) {
//                    Log.d(TAG, "TRY TRY TRY TRY TRY")
//                    val oneItem = document.toObject<ConfectioneryItem>()
//                    itemList.add(oneItem)
//                }
//            }
//        delay(5000)
//        return itemList
//    }

    suspend fun getItemsAll() {
        var itemList: ArrayList<ConfectioneryItem> = ArrayList()
        db.collection("ConfectioneryItem")
//            .whereEqualTo("notInProduction", false)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val message = document.toObject<ConfectioneryItem>()
                    itemList.add(message)
                }
                Log.d(TAG, documents.count().toString())
                val itemListToReturn: List<ConfectioneryItem> = itemList
                ChooseItemsViewModel().updateItems(itemListToReturn)
            }
            .addOnFailureListener {
                ChooseItemsViewModel().notUpdateItems()
            }
    }


//    fun getAllIt(): List<ConfectioneryItem>? {
//        var itemList: ArrayList<ConfectioneryItem> = ArrayList()
//
//        return try {
//            db.collection("ConfectioneryItem")
////        .whereEqualTo("notInProduction", false)
//                .get()
//                .addOnSuccessListener { documents ->
//                    for (document in documents) {
//                        Log.d(TAG, "TRY TRY TRY TRY TRY")
//                        val oneItem = document.toObject<ConfectioneryItem>()
//                        itemList.add(oneItem)
//                    }
//                }
//                .addOnFailureListener {
//
//                }
//        }
//        return itemList
//    }

//    suspend fun authenticate(
//        email: String,
//        password: String
//    ): FirebaseUser? {
//        firebaseAuth.signInWithEmailAndPassword(
//            email, password).await()
//        return firebaseAuth.currentUser ?:
//        throw FirebaseAuthException("", "")
//    }
}

