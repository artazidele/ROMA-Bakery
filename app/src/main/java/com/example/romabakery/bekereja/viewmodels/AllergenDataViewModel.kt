package com.example.romabakery.bekereja.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.romabakery.bekereja.models.allergens.AllergenDataClass
import com.example.romabakery.bekereja.models.allergens.AllergenDatabase
import com.example.romabakery.bekereja.models.items.ItemDataClass
import com.example.romabakery.bekereja.models.items.ItemDatabase
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.launch

class AllergenDataViewModel : ViewModel() {
    private val _status = MutableLiveData<NetworkDataStatus>()
    val status: LiveData<NetworkDataStatus> = _status

    fun addNewAllergen(allergen: AllergenDataClass, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            _status.value = NetworkDataStatus.LOADING
            AllergenDatabase().addAllergen(allergen)
                .addOnSuccessListener { documents ->
                    _status.value = NetworkDataStatus.DONE
                    onResult(true)
                }
                .addOnFailureListener {
                    _status.value = NetworkDataStatus.ERROR
                    onResult(false)
                }
        }
    }

    fun getOneAllergen(id: String, onResult: (AllergenDataClass?) -> Unit) {
        viewModelScope.launch {
            _status.value = NetworkDataStatus.LOADING
            AllergenDatabase().getAllergen(id)
                .addOnSuccessListener { document ->
                    _status.value = NetworkDataStatus.DONE
                    val allergen = document.toObject<AllergenDataClass>()
                    onResult(allergen)
                }
                .addOnFailureListener {
                    _status.value = NetworkDataStatus.ERROR
                    onResult(null)
                }
        }
    }

    fun getAllergenItems(id: String, onResult: (ArrayList<ItemDataClass>?) -> Unit) {
        viewModelScope.launch {
            _status.value = NetworkDataStatus.LOADING
            var itemListWithAllergen = ArrayList<ItemDataClass>()
            ItemDatabase().getItems()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        val oneItem = document.toObject<ItemDataClass>()
                        for (allergen in oneItem.containsAllergens) {
                            if (allergen == id) {
                                itemListWithAllergen.add(oneItem)
                                break
                            }
                        }
                    }
                    _status.value = NetworkDataStatus.DONE
                    onResult(itemListWithAllergen)
                }
                .addOnFailureListener {
                    _status.value = NetworkDataStatus.ERROR
                    onResult(null)
                }
        }
    }

    fun deleteOneAllergen(id: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            _status.value = NetworkDataStatus.LOADING
            AllergenDatabase().deleteAllergen(id)
                .addOnSuccessListener {
                    _status.value = NetworkDataStatus.DONE
                    onResult(true)
                }
                .addOnFailureListener {
                    _status.value = NetworkDataStatus.ERROR
                    onResult(false)
                }
        }
    }

    fun updateOneAllergen(allergen: AllergenDataClass, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            _status.value = NetworkDataStatus.LOADING
            AllergenDatabase().updateAllergen(allergen)
                .addOnSuccessListener {
                    _status.value = NetworkDataStatus.DONE
                    onResult(true)
                }
                .addOnFailureListener {
                    _status.value = NetworkDataStatus.ERROR
                    onResult(false)
                }
        }
    }

    fun getAllAllergens(onResult: (ArrayList<AllergenDataClass>?) -> Unit) {
        viewModelScope.launch {
            var allergenList = ArrayList<AllergenDataClass>()
            _status.value = NetworkDataStatus.LOADING
            AllergenDatabase().getAllergens()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        val allergen = document.toObject<AllergenDataClass>()
                        allergenList.add(allergen)
                    }
                    _status.value = NetworkDataStatus.DONE
                    onResult(allergenList)
                }
                .addOnFailureListener {
                    _status.value = NetworkDataStatus.ERROR
                    onResult(null)
                }
        }
    }
}
