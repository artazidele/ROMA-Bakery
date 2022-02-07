package com.example.romabakery.viewmodel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.example.romabakery.model.Allergen
import com.example.romabakery.model.AllergenFirebase
import com.example.romabakery.model.ConfectioneryItem
import com.example.romabakery.model.ConfectioneryItemFirebase
import com.example.romabakery.view.allergens.AllergenAdapter
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.launch

class AllergenViewModel : ViewModel() {
    private val _status = MutableLiveData<NetworkLoadingStatus>()
    val status: LiveData<NetworkLoadingStatus> = _status
    private val _allergens = MutableLiveData<List<Allergen>>()
    val allergens: LiveData<List<Allergen>> = _allergens
//    private val _items = MutableLiveData<List<ConfectioneryItem>>()
//    val items: LiveData<List<ConfectioneryItem>> = _items


//    var deleteStatusDone = false

    fun getAllergen(id: String) {
        viewModelScope.launch {
            _status.value = NetworkLoadingStatus.LOADING
            AllergenFirebase().getOneAllergen(id)
                .addOnSuccessListener { document ->
                    val allergen = document.toObject<Allergen>()
                    _status.value = NetworkLoadingStatus.DONE
                    Log.d(ContentValues.TAG, "GOT ONE ALLERGEN SUCCEESS")
                }
                .addOnFailureListener {
                    _status.value = NetworkLoadingStatus.ERROR
                    Log.d(ContentValues.TAG, "GOT ONE ALLERGEN FAILURE")
                }
        }
    }

    fun getAllAllergens() {
        viewModelScope.launch {
            _status.value = NetworkLoadingStatus.LOADING
            AllergenFirebase().getAllAllergens()
                .addOnSuccessListener { documents ->
                    Log.d(ContentValues.TAG, documents.count().toString())
                    val allergenListToReturn: List<Allergen> = makeAllergenList(documents)
                    _allergens.value = allergenListToReturn
                    _status.value = NetworkLoadingStatus.DONE
                    Log.d(ContentValues.TAG, "GOT ALLERGENS SUCCEESS")
                }
                .addOnFailureListener {
                    _allergens.value = listOf()
                    _status.value = NetworkLoadingStatus.ERROR
                    Log.d(ContentValues.TAG, "GOT ALLERGENS FAILURE")
                }
        }
    }

    fun makeAllergenList(documents: QuerySnapshot): ArrayList<Allergen> {
        var allergenList: ArrayList<Allergen> = ArrayList()
        for (document in documents) {
            val allergen = document.toObject<Allergen>()
            allergenList.add(allergen)
        }
        return allergenList
    }


    fun addAllergen(allergen: Allergen) {
        viewModelScope.launch {
            _status.value = NetworkLoadingStatus.LOADING
            AllergenFirebase().addAllergen(allergen)
                .addOnSuccessListener {
                    _status.value = NetworkLoadingStatus.DONE
                    Log.d(ContentValues.TAG, "ADDED ALLERGEN SUCCEESS")
                }
                .addOnFailureListener {
                    _status.value = NetworkLoadingStatus.ERROR
                    Log.d(ContentValues.TAG, "ADDED ALLERGEN FAILURE")
                }
        }
    }

    fun updateAllergen(allergen: Allergen) {
        viewModelScope.launch {
            _status.value = NetworkLoadingStatus.LOADING
            AllergenFirebase().updateAllergen(allergen)
                .addOnSuccessListener {
                    _status.value = NetworkLoadingStatus.DONE
                    Log.d(ContentValues.TAG, "UPDATED ALLERGEN SUCCEESS")
                }
                .addOnFailureListener {
                    _status.value = NetworkLoadingStatus.ERROR
                    Log.d(ContentValues.TAG, "UPDATED ALLERGEN FAILURE")
                }
        }
    }

    fun deleteAllergen(id: String): ArrayList<ConfectioneryItem> {
        var itemsWithAllergen = ArrayList<ConfectioneryItem>()
        viewModelScope.launch {
            _status.value = NetworkLoadingStatus.LOADING
            ConfectioneryItemFirebase().getAllItems()
                .addOnSuccessListener { documents ->
                    Log.d(ContentValues.TAG, "DELETE SUCCEESS")
                    itemsWithAllergen = makeItemListForAllergen(id, documents)
                }
        }
        return itemsWithAllergen
    }

    fun makeItemListForAllergen(id: String, documents: QuerySnapshot): ArrayList<ConfectioneryItem> {
        var itemList: ArrayList<ConfectioneryItem> = ArrayList()
        for (document in documents) {
            val oneItem = document.toObject<ConfectioneryItem>()
            for (allergen in oneItem.containsAllergens) {
                if (allergen == id) {
                    itemList.add(oneItem)
                    break
                }
            }
        }
        return itemList
    }

    fun completeDeleteAllergen(id: String, holder: RecyclerView.ViewHolder) {
//        var deletedSuccess = false
        viewModelScope.launch {
            _status.value = NetworkLoadingStatus.LOADING
            AllergenFirebase().deleteAllergen(id)
                .addOnSuccessListener {
                    _status.value = NetworkLoadingStatus.DONE
                    Log.d(ContentValues.TAG, "DELETED SUCCEESS")
                    AllergenAdapter().hideDeletedRow(holder)
//                    deletedSuccess = true
                }
                .addOnFailureListener {
                    _status.value = NetworkLoadingStatus.ERROR
                    Log.d(ContentValues.TAG, "DELETED FAILURE")
                }
        }
//        return  deletedSuccess
    }
}
