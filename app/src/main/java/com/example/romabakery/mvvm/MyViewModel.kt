package com.example.romabakery.mvvm

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.romabakery.model.Allergen
import com.example.romabakery.model.AllergenFirebase
import com.example.romabakery.model.ConfectioneryItem
import com.example.romabakery.viewmodel.NetworkLoadingStatus
import com.google.firebase.firestore.ktx.toObject

class MyViewModel {
    private val _status = MutableLiveData<NetworkLoadingStatus>()
    val status: LiveData<NetworkLoadingStatus> = _status
    private val _allergens = MutableLiveData<ArrayList<Allergen>>()
    val allergens: LiveData<ArrayList<Allergen>> = _allergens



    fun getAllergenItems(id: String, onResult: (ArrayList<ConfectioneryItem>?) -> Unit) {
        var itemListWithAllergen = ArrayList<ConfectioneryItem>()
        MyFirebase().getAllConfectioneryItems()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val oneItem = document.toObject<ConfectioneryItem>()
                    for (allergen in oneItem.containsAllergens) {
                        if (allergen == id) {
                            itemListWithAllergen.add(oneItem)
                            break
                        }
                    }
                }
                onResult(itemListWithAllergen)
            }
            .addOnFailureListener {
                onResult(null)
            }
    }

    fun getAllergens(onResult: (ArrayList<Allergen>?) -> Unit) {
        var allergenList = ArrayList<Allergen>()
        MyFirebase().getAllAllergens()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val allergen = document.toObject<Allergen>()
                    allergenList.add(allergen)
                }
                onResult(allergenList)
            }
            .addOnFailureListener {
                onResult(null)
            }
    }


}