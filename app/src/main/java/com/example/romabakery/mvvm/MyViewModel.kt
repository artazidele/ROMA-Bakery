package com.example.romabakery.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.romabakery.model.Allergen
import com.example.romabakery.model.ConfectioneryItem
import com.example.romabakery.viewmodel.NetworkLoadingStatus
import com.google.firebase.firestore.ktx.toObject

class MyViewModel : ViewModel() {
    private val _status = MutableLiveData<NetworkLoadingStatus>()
    val status: LiveData<NetworkLoadingStatus> = _status
    private val _allergens = MutableLiveData<ArrayList<Allergen>>()
    val allergens: LiveData<ArrayList<Allergen>> = _allergens

    fun addNewAllergen(allergen: Allergen, onResult: (Boolean) -> Unit) {
        MyFirebase().addAllergen(allergen)
            .addOnSuccessListener { documents ->
                onResult(true)
            }
            .addOnFailureListener {
                onResult(false)
            }
    }

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

    fun deleteOneAllergen(id: String, onResult: (Boolean) -> Unit) {
        MyFirebase().deleteAllergen(id)
            .addOnSuccessListener {
                onResult(true)
            }
            .addOnFailureListener {
                onResult(false)
            }
    }

    fun updateOneAllergen(allergen: Allergen, onResult: (Boolean) -> Unit) {
        MyFirebase().updateAllergen(allergen)
            .addOnSuccessListener {
                onResult(true)
            }
            .addOnFailureListener {
                onResult(false)
            }
    }

    fun getAllergens(onResult: (ArrayList<Allergen>?) -> Unit) {
        var allergenList = ArrayList<Allergen>()
        MyFirebase().getAllAllergens()
            .addOnSuccessListener { documents ->
                _status.value = NetworkLoadingStatus.DONE
                for (document in documents) {
                    val allergen = document.toObject<Allergen>()
                    allergenList.add(allergen)
                }
                onResult(allergenList)
                _allergens.value = allergenList
            }
            .addOnFailureListener {
                onResult(null)
                _allergens.value = arrayListOf()
            }
    }
}
