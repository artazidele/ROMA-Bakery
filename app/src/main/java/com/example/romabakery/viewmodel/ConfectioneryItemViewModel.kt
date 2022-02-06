package com.example.romabakery.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.romabakery.model.ConfectioneryItem
import com.example.romabakery.model.ConfectioneryItemFirebase
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.launch

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
            ConfectioneryItemFirebase().getAllItems()
                .addOnSuccessListener { documents ->
                    Log.d(TAG, documents.count().toString())
                    val itemListToReturn: List<ConfectioneryItem> = filterItems(
                        bun,
                        cake,
                        cookies,
                        forVegans,
                        forVegetarians,
                        withoutFlour,
                        withoutLactose,
                        notContainsAllergens,
                        notInProduction,
                        documents
                    )
                    _items.value = itemListToReturn
                    _status.value = NetworkStatus.DONE
                }
                .addOnFailureListener {
                    _items.value = listOf()
                    _status.value = NetworkStatus.ERROR
                }
        }
    }

    fun filterItems(
        bun: Boolean,
        cake: Boolean,
        cookies: Boolean,
        forVegans: Boolean,
        forVegetarians: Boolean,
        withoutFlour: Boolean,
        withoutLactose: Boolean,
        notContainsAllergens: ArrayList<String>,
        notInProduction: Boolean,
        documents: QuerySnapshot
    ): ArrayList<ConfectioneryItem> {
        var itemList: ArrayList<ConfectioneryItem> = ArrayList()
        for (document in documents) {
            val oneItem = document.toObject<ConfectioneryItem>()
            if (oneItem.bun == bun && oneItem.forVegans == forVegans && oneItem.cake == cake && oneItem.cookies == cookies && oneItem.forVegetarians == forVegetarians && oneItem.notInProduction == false && oneItem.withoutFlour == withoutFlour && oneItem.withoutLactose == withoutLactose && oneItem.notInProduction == notInProduction) {
                var isInList = true
                for (allergen in notContainsAllergens) {
                    for (itemAllergen in oneItem.containsAllergens) {
                        if (allergen == itemAllergen) {
                            isInList = false
                            break
                        }
                    }
                }
                if (isInList == true) {
                    itemList.add(oneItem)
                }
            }
        }
        return itemList
    }


    fun addConfectioneryItem(item: ConfectioneryItem) {
        viewModelScope.launch {
            ConfectioneryItemFirebase().addItem(item)
                .addOnSuccessListener {
                    Log.d(TAG, "SUCCEESS")
                }
                .addOnFailureListener {
                    Log.d(TAG, "FAILURE")
                }
        }
    }

    fun updateConfectioneryItem(item: ConfectioneryItem) {
        viewModelScope.launch {
            ConfectioneryItemFirebase().updateItem(item)
                .addOnSuccessListener {
                    Log.d(TAG, "UPDATED SUCCEESS")
                }
                .addOnFailureListener {
                    Log.d(TAG, "UPDATED FAILURE")
                }
        }
    }

    fun deleteConfectioneryItem(id: String) {
        viewModelScope.launch {
            ConfectioneryItemFirebase().deleteItem(id)
                .addOnSuccessListener {
                    Log.d(TAG, "DELETED SUCCEESS")
                }
                .addOnFailureListener {
                    Log.d(TAG, "DELETED FAILURE")
                }
        }
    }
}
