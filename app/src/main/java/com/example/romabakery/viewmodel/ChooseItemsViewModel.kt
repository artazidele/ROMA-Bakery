package com.example.romabakery.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.romabakery.model.ConfectioneryItem
import com.example.romabakery.model.ConfectioneryItemFirebase
import com.google.android.gms.tasks.Tasks.await
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.nio.file.Files.size

enum class NetworkStatus { LOADING, ERROR, DONE }

class ChooseItemsViewModel : ViewModel() {
//    val db = FirebaseFirestore.getInstance()
    private val _status = MutableLiveData<NetworkStatus>()
    val status: LiveData<NetworkStatus> = _status
    private val _items = MutableLiveData<List<ConfectioneryItem>>()
    val items: LiveData<List<ConfectioneryItem>> = _items
    fun getConfItems() {
        viewModelScope.launch {
            _status.value = NetworkStatus.LOADING
            try {

                var itemList: ArrayList<ConfectioneryItem> = ArrayList()
//                db.collection("ConfectioneryItem")
////            .whereEqualTo("notInProduction", false)
//                    .get()
                        ConfectioneryItemFirebase().itemQuery()
                    .addOnSuccessListener { documents ->
                        for (document in documents) {
                            val message = document.toObject<ConfectioneryItem>()
                            itemList.add(message)
                        }
                        Log.d(TAG, documents.count().toString())
                        val itemListToReturn: List<ConfectioneryItem> = itemList
                        _items.value = itemListToReturn
                        _status.value = NetworkStatus.DONE
                        Log.d(TAG, "TRY TRY TRY TRY TRY")
                    }
                    .addOnFailureListener {
                        _items.value = listOf()
                        _status.value = NetworkStatus.ERROR
                    }



//                ConfectioneryItemFirebase().getItemsAll()
//                Log.d(TAG, "TRY")
            } catch (e: Exception) {
                _status.value = NetworkStatus.ERROR
                _items.value = listOf()
                Log.d(TAG, "CATCH")
            }
        }
    }
    public fun updateItems(confectioneryItems: List<ConfectioneryItem>) {
//        viewModelScope.launch {
            _items.value = confectioneryItems
            _status.value = NetworkStatus.DONE
            Log.d(TAG, "TRY TRY TRY TRY TRY")
//        }
    }
    public fun notUpdateItems() {
        _items.value = listOf()
        _status.value = NetworkStatus.ERROR
    }
}