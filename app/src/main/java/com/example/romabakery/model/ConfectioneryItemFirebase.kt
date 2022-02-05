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


    suspend fun returnItemsInProduction(): List<ConfectioneryItem> {

        var itemList: ArrayList<ConfectioneryItem> = ArrayList()
        coroutineScope {
            async {
                db.collection("ConfectioneryItem")
                    .whereEqualTo("notInProduction", false)
                    .get()
                    .addOnSuccessListener { documents ->
                        for (document in documents) {
                            Log.d(TAG, "TRY TRY TRY TRY TRY")
                            val oneItem = document.toObject<ConfectioneryItem>()
                            itemList.add(oneItem)
                        }
                    }
            }
                .await()
        }

        return itemList




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

//    suspend fun addItem(item: ConfectioneryItem): Task<Void> {
//        return db.collection("ConfectioneryItem").document(item.id)
//            .set(item)
//    }

//    suspend fun getOwner() = firestore
//        .collection("Chat")
//        .document("cF7DrENgQ4noWjr3SxKX")
//        .get()
//        .await()
//        .result


    suspend fun addItem(item: ConfectioneryItem): Task<Void> {
//        coroutineScope {
//            async {
//                db.collection("ConfectioneryItem").document(item.id)
//                    .set(item)
//            }
//                .await()
//        }
        return db.collection("ConfectioneryItem").document(item.id)
            .set(item)


    }

//    suspend fun addItemsAsync(item: ConfectioneryItem): Task<Void> {
//        var task: Task<Void>
//        coroutineScope {
//            task = async {
//                db.collection("ConfectioneryItem").document(item.id)
//                    .set(item)
//            }
//            deferredOne.await()
//        }
//        return
//    }

    suspend fun addItemAsynfffffc(item: ConfectioneryItem) =
        coroutineScope {
            val deferredOne = async {
                db.collection("ConfectioneryItem").document(item.id)
                    .set(item)
            }
            val deferredTwo = async { }
            deferredOne.await()
            deferredTwo.await()
        }


    suspend fun updateItem(item: ConfectioneryItem) {

    }

    suspend fun deleteItem(item: ConfectioneryItem) {

    }

}

