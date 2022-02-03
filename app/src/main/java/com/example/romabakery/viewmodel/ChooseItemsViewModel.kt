package com.example.romabakery.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.romabakery.model.ConfectioneryItem
import com.example.romabakery.model.ConfectioneryItemFirebase
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.nio.file.Files.size

enum class NetworkStatus { LOADING, ERROR, DONE }

class ChooseItemsViewModel : ViewModel() {
    private val _status = MutableLiveData<NetworkStatus>()
    val status: LiveData<NetworkStatus> = _status
    private val _items = MutableLiveData<List<ConfectioneryItem>>()
    val items: LiveData<List<ConfectioneryItem>> = _items
    fun getConfItems() {
        viewModelScope.launch {
            _status.value = NetworkStatus.LOADING
            try {
                Log.d(TAG, "TRY TRY TRY TRY TRY")
                var itemList: ArrayList<ConfectioneryItem> = ArrayList()
                ConfectioneryItemFirebase().getAllItems().addOnSuccessListener { documents ->
                    for (document in documents) {
                        val oneItem = document.toObject<ConfectioneryItem>()
                        itemList.add(oneItem)
                    }
                }
////                delay(10000)
//                for (document in 0..count(querySnapshot)) {
//                    val oneItem = document.toObject<ConfectioneryItem>()
//                    itemList.add(oneItem)
//                }
                _items.value = itemList//getConfectioneryItems()
                _status.value = NetworkStatus.DONE
                Log.d(TAG, "TRY")
            } catch (e: Exception) {
                _status.value = NetworkStatus.ERROR
                _items.value = listOf()
                Log.d(TAG, "CATCH")
            }
        }
    }
    public fun updateItems(confectioneryItems: List<ConfectioneryItem>) {
        _items.value = confectioneryItems
    }
}