package com.example.romabakery.viewmodel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.romabakery.model.Allergen
import com.example.romabakery.model.AllergenFirebase
import com.example.romabakery.model.ConfectioneryItem
import com.example.romabakery.model.ConfectioneryItemFirebase
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.launch

enum class AllergenStatus { LOADING, ERROR, DONE }

class AllergenViewModel: ViewModel() {
    private val _status = MutableLiveData<AllergenStatus>()
    val status: LiveData<AllergenStatus> = _status
    private val _allergens = MutableLiveData<List<Allergen>>()
    val allergens: LiveData<List<Allergen>> = _allergens

    fun getAllergens() {
        viewModelScope.launch {
            _status.value = AllergenStatus.LOADING
            AllergenFirebase().getAllAllergens()
                .addOnSuccessListener { documents ->
                    Log.d(ContentValues.TAG, documents.count().toString())
                    val allergenListToReturn: List<Allergen> = makeAllergenList(documents)
                    _allergens.value = allergenListToReturn
                    _status.value = AllergenStatus.DONE
                }
                .addOnFailureListener {
                    _allergens.value = listOf()
                    _status.value = AllergenStatus.ERROR
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
            AllergenFirebase().addAllergen(allergen)
                .addOnSuccessListener {
                    Log.d(ContentValues.TAG, "ADDED ALLERGEN SUCCEESS")
                }
                .addOnFailureListener {
                    Log.d(ContentValues.TAG, "ADDED ALLERGEN FAILURE")
                }
        }
    }

    fun updateAllergen(allergen: Allergen) {
        viewModelScope.launch {
            AllergenFirebase().addAllergen(allergen)
                .addOnSuccessListener {
                    Log.d(ContentValues.TAG, "UPDATED ALLERGEN SUCCEESS")
                }
                .addOnFailureListener {
                    Log.d(ContentValues.TAG, "UPDATED ALLERGEN FAILURE")
                }
        }
    }

    fun deleteAllergen(id: String) {
        viewModelScope.launch {
            val itemsWhereIsAllergen = AllergenFirebase().checkIfAllergenCanBeDeleted(id)
            if (itemsWhereIsAllergen == ArrayList<ConfectioneryItem>()) {
                Log.d(ContentValues.TAG, "CAN BE DELETED")
                AllergenFirebase().deleteAllergen(id)
                    .addOnSuccessListener {
                        Log.d(ContentValues.TAG, "DELETED ALLERGEN SUCCEESS")
                    }
                    .addOnFailureListener {
                        Log.d(ContentValues.TAG, "DELETED ALLERGEN FAILURE")
                    }
            } else {
                Log.d(ContentValues.TAG, "CANNOT BE DELETED")
            }

        }
    }
}
