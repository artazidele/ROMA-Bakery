package com.example.romabakery.viewmodel

import android.content.ContentValues.TAG
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
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
    private val _status = MutableLiveData<NetworkStatus>()
    val status: LiveData<NetworkStatus> = _status
    private val _items = MutableLiveData<List<ConfectioneryItem>>()
    val items: LiveData<List<ConfectioneryItem>> = _items

    fun getConfectioneryItems(
        bun: Boolean,
        cake: Boolean,
        cookies: Boolean,
        forVegans: Boolean,
        forVegetarians: Boolean,
        withoutFlour: Boolean,
        withoutLactose: Boolean,
        notContainsAllergens: ArrayList<String>,
        notInProduction: Boolean
    ) {
        viewModelScope.launch {
            _status.value = NetworkStatus.LOADING
            try {
                _items.value = ConfectioneryItemFirebase().returnItemsInProduction()
                _status.value = NetworkStatus.DONE
                Log.d(TAG, "TRY TRY TRY TRY TRY")

//                var itemList: ArrayList<ConfectioneryItem> = ArrayList()
//                ConfectioneryItemFirebase().getItemsInProduction()
//                    .addOnSuccessListener { documents ->
//                        for (document in documents) {
//                            val oneItem = document.toObject<ConfectioneryItem>()
//                            if (oneItem.bun == bun && oneItem.forVegans == forVegans && oneItem.cake == cake && oneItem.cookies == cookies && oneItem.forVegetarians == forVegetarians && oneItem.notInProduction == false && oneItem.withoutFlour == withoutFlour && oneItem.withoutLactose == withoutLactose) {
//                                var isInList = true
//                                for (allergen in notContainsAllergens) {
//                                    for (itemAllergen in oneItem.containsAllergens) {
//                                        if (allergen == itemAllergen) {
//                                            isInList = false
//                                            break
//                                        }
//                                    }
//                                }
//                                if (isInList == true) {
//                                    itemList.add(oneItem)
//                                }
//                            }
//                        }
//                        Log.d(TAG, documents.count().toString())
//                        val itemListToReturn: List<ConfectioneryItem> = itemList
//                        _items.value = itemListToReturn
//                        _status.value = NetworkStatus.DONE
//                        Log.d(TAG, "TRY TRY TRY TRY TRY")
//                    }
//                    .addOnFailureListener {
//                        _items.value = listOf()
//                        _status.value = NetworkStatus.ERROR
//                    }
            } catch (e: Exception) {
                _status.value = NetworkStatus.ERROR
                _items.value = listOf()
                Log.d(TAG, "CATCH")
            }
        }
    }


    fun addConfectioneryItem(item: ConfectioneryItem) {


        viewModelScope.launch {
            _status.value = NetworkStatus.LOADING
            try {
//                async {
                ConfectioneryItemFirebase().addItem(item)
                    .addOnCompleteListener {
                        Log.d(TAG, "SUCCEESS")
                    }
                    .addOnCanceledListener {
                        Log.d(TAG, "FAILURE")
                    }
                Log.d(TAG, "TRY")
//                }
//                    .await()
            } catch (e: Exception) {
                Log.d(TAG, "CATCH")
            }
        }
    }
}