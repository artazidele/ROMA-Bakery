package com.example.romabakery.bekereja.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.romabakery.bekereja.models.allergens.AllergenDataClass
import com.example.romabakery.bekereja.models.allergens.AllergenDatabase
import com.example.romabakery.bekereja.models.items.ItemDataClass
import com.example.romabakery.bekereja.models.items.ItemDatabase
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.launch

class ItemDataViewModel : ViewModel() {
    private val _status = MutableLiveData<NetworkDataStatus>()
    val status: LiveData<NetworkDataStatus> = _status

    fun addNewItem(item: ItemDataClass, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            _status.value = NetworkDataStatus.LOADING
            ItemDatabase().addItem(item)
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

    fun getOneItem(id: String, onResult: (ItemDataClass?) -> Unit) {
        viewModelScope.launch {
            _status.value = NetworkDataStatus.LOADING
            ItemDatabase().getItem(id)
                .addOnSuccessListener { document ->
                    _status.value = NetworkDataStatus.DONE
                    val item = document.toObject<ItemDataClass>()
                    onResult(item)
                }
                .addOnFailureListener {
                    _status.value = NetworkDataStatus.ERROR
                    onResult(null)
                }
        }
    }

    fun getItemAllergens(
        item: ItemDataClass,
        id: String,
        onResult: (ArrayList<AllergenDataClass>?) -> Unit
    ) {
        viewModelScope.launch {
            _status.value = NetworkDataStatus.LOADING
            var itemAllergenList = ArrayList<AllergenDataClass>()
            AllergenDatabase().getAllergens()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        val oneAllergen = document.toObject<AllergenDataClass>()
                        for (allergen in item.containsAllergens) {
                            if (allergen == oneAllergen.id) {
                                itemAllergenList.add(oneAllergen)
                                break
                            }
                        }
                    }
                    _status.value = NetworkDataStatus.DONE
                    onResult(itemAllergenList)
                }
                .addOnFailureListener {
                    _status.value = NetworkDataStatus.ERROR
                    onResult(null)
                }
        }
    }

    fun deleteOneItem(id: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            _status.value = NetworkDataStatus.LOADING
            ItemDatabase().deleteItem(id)
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

    fun updateOneItem(item: ItemDataClass, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            _status.value = NetworkDataStatus.LOADING
            ItemDatabase().updateItem(item)
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

    fun getAllItems(
        bun: Boolean,
        cake: Boolean,
        cookies: Boolean,
        forVegans: Boolean,
        forVegetarians: Boolean,
        withoutFlour: Boolean,
        withoutLactose: Boolean,
        notContainsAllergens: ArrayList<String>,
        notInProduction: Boolean,
        onResult: (ArrayList<ItemDataClass>?) -> Unit
    ) {
        viewModelScope.launch {
            _status.value = NetworkDataStatus.LOADING
            ItemDatabase().getItems()
                .addOnSuccessListener { documents ->
                    var itemList = filterItems(
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
                    _status.value = NetworkDataStatus.DONE
                    onResult(itemList)
                }
                .addOnFailureListener {
                    _status.value = NetworkDataStatus.ERROR
                    onResult(null)
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
    ): ArrayList<ItemDataClass> {
        var itemList: ArrayList<ItemDataClass> = ArrayList()
        for (document in documents) {
            val oneItem = document.toObject<ItemDataClass>()
            var isInList = true
//            if (oneItem.bun == bun && oneItem.forVegans == forVegans && oneItem.cake == cake && oneItem.cookies == cookies && oneItem.forVegetarians == forVegetarians && oneItem.notInProduction == false && oneItem.withoutFlour == withoutFlour && oneItem.withoutLactose == withoutLactose && oneItem.notInProduction == notInProduction) {
            // Kad nevar būt?
            if ((oneItem.bun == true && bun == false)
                || (oneItem.cookies == true && cookies == false)
                || (oneItem.cake == true && cake == false)
                || (oneItem.forVegans == false && forVegans == true)
                || (oneItem.forVegetarians == false && forVegetarians == true)
                || (oneItem.withoutFlour == false && withoutFlour == true)
                || (oneItem.withoutLactose == false && withoutLactose == true)
                  && oneItem.notInProduction == notInProduction) {
                isInList = false
            }
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
        return itemList
    }
}
