package com.example.romabakery.viewmodel

import android.content.ContentValues
import android.content.ContentValues.TAG
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
import com.example.romabakery.view.allergens.AllergenActivity
import com.example.romabakery.view.allergens.AllergenAdapter
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class AllergenViewModel : ViewModel() {
    private val _status = MutableLiveData<NetworkLoadingStatus>()
    val status: LiveData<NetworkLoadingStatus> = _status
    private val _allergens = MutableLiveData<List<Allergen>>()
    val allergens: LiveData<List<Allergen>> = _allergens

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

    fun getAllAllergens(): ArrayList<Allergen> {
        var allergenList: ArrayList<Allergen> = ArrayList()
        viewModelScope.launch {
            _status.value = NetworkLoadingStatus.LOADING
            AllergenFirebase().getAllAllergens()
                .addOnSuccessListener { documents ->
                    Log.d(ContentValues.TAG, documents.count().toString())
                    val allergenListToReturn: List<Allergen> = makeAllergenList(documents)
                    for (document in allergenListToReturn) {
//                        val allergen = document.toObject<Allergen>()
                        allergenList.add(document)
                    }
//                    allergenList = allergenListToReturn
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
        return allergenList
    }

    fun makeAllergenList(documents: QuerySnapshot): ArrayList<Allergen> {
        var allergenList: ArrayList<Allergen> = ArrayList()
        for (document in documents) {
            val allergen = document.toObject<Allergen>()
            allergenList.add(allergen)
        }
        return allergenList
    }


    fun addAllergen(allergen: Allergen): Boolean {
        var isSuccessfull = false
        viewModelScope.launch {
            _status.value = NetworkLoadingStatus.LOADING
            AllergenFirebase().addAllergen(allergen)
                .addOnSuccessListener {
                    isSuccessfull = true
                    _status.value = NetworkLoadingStatus.DONE
                    Log.d(ContentValues.TAG, "ADDED ALLERGEN SUCCEESS")
                }
                .addOnFailureListener {
                    _status.value = NetworkLoadingStatus.ERROR
                    Log.d(ContentValues.TAG, "ADDED ALLERGEN FAILURE")
                }
        }
        return isSuccessfull
    }



    fun getAllergenItems(id: String, onResult: (ArrayList<ConfectioneryItem>?) -> Unit) {
        var itemListWithAllergen = ArrayList<ConfectioneryItem>()
        AllergenFirebase().getItems()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    Log.d(ContentValues.TAG, "ITEM")
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


    fun isDeleted2(id: String): Boolean {
        var isDel = false
        AllergenFirebase().deleteAller(id) {
            if (it.equals("SUCCESS")) {
                isDel = true
            }
        }
        return isDel
    }

    fun isDeleted(id: String, onResult: (String) -> Unit) {
        AllergenFirebase().deleteAller(id) {
            if (it.equals("SUCCESS")) {
                onResult("SUCCESS")
            } else {
                onResult("FAILURE")
            }
        }
    }




    fun deleteAllergen(id: String, onResult: (String?) -> Unit) {
        AllergenFirebase().deleteAl("id") // getOneAl("987")//
            .addOnSuccessListener { //document ->
//                val allergen = document.toObject<Allergen>()
//                allergen?.title?.let { Log.d(TAG, it) }
                onResult(null) //("SUCCESS")
            }
            .addOnFailureListener {
                onResult(null)
                Log.d(TAG, "Failure")
            }
    }

    fun updateAllergen(allergen: Allergen, holder: RecyclerView.ViewHolder) {
        viewModelScope.launch {
            _status.value = NetworkLoadingStatus.LOADING
            AllergenFirebase().updateAllergen(allergen)
                .addOnSuccessListener {
                    _status.value = NetworkLoadingStatus.DONE
//                    AllergenAdapter().changeAllergenTitle(holder, allergen.title)
                    Log.d(ContentValues.TAG, "UPDATED ALLERGEN SUCCEESS")
                }
                .addOnFailureListener {
                    _status.value = NetworkLoadingStatus.ERROR
                    Log.d(ContentValues.TAG, "UPDATED ALLERGEN FAILURE")
                }
        }
    }

    private var itemListWithAllergenId = ArrayList<ConfectioneryItem>()
    public fun returnItemListWithAllergen(): ArrayList<ConfectioneryItem> {
        return itemListWithAllergenId
    }

    fun callMakeItemList(id: String) {
        Log.d(ContentValues.TAG, "Press delete 2")
        viewModelScope.launch {
            async {
                makeItemListWithAllergen(id)
            }.await()
        }
        Log.d(ContentValues.TAG, "Press delete 2,5")
    }

    suspend fun getItems(id: String): ArrayList<ConfectioneryItem>? {
        val documents = withContext(Dispatchers.IO) {
            ConfectioneryItemFirebase().getAllItems()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        Log.d(ContentValues.TAG, "Press delete 4")
                        val oneItem = document.toObject<ConfectioneryItem>()
                        for (allergen in oneItem.containsAllergens) {
                            if (allergen == id) {
                                itemListWithAllergenId.add(oneItem)
                                break
                            }
                        }
                    }
                }
        }
        if (!itemListWithAllergenId.isEmpty()) {
            return itemListWithAllergenId
        }
        return null
    }

//    suspend fun makeItemListWithDispatcher(id: String): ArrayList<ConfectioneryItem>? {
//        var itemListWithAllergenI = ArrayList<ConfectioneryItem>()
//        withContext(dispatcher) {
//            val allDocuments = async {
//                _status.value = NetworkLoadingStatus.LOADING
//                ConfectioneryItemFirebase().getAllItems()
//                    .addOnSuccessListener { documents ->
//                        for (document in documents) {
//                            Log.d(ContentValues.TAG, "Press delete 4")
//                            val oneItem = document.toObject<ConfectioneryItem>()
//                            for (allergen in oneItem.containsAllergens) {
//                                if (allergen == id) {
//                                    itemListWithAllergenI.add(oneItem)
//                                    break
//                                }
//                            }
//                        }
//                    }
//            }
//            allDocuments.await()
//        }
//        return itemListWithAllergenI
//    }

    suspend fun makeItemListWithAllergen(id: String) = coroutineScope {
        Log.d(ContentValues.TAG, "Press delete 3")
        val allDocuments = async {
//            _status.value = NetworkLoadingStatus.LOADING
            ConfectioneryItemFirebase().getAllItems()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        Log.d(ContentValues.TAG, "Press delete 4")
                        val oneItem = document.toObject<ConfectioneryItem>()
                        for (allergen in oneItem.containsAllergens) {
                            if (allergen == id) {
                                itemListWithAllergenId.add(oneItem)
                                break
                            }
                        }
                    }
                }
        }
        allDocuments.await()
//        _status.value = NetworkLoadingStatus.DONE
    }

//    fun deleteAllergen(id: String): ArrayList<ConfectioneryItem> {
//        var itemsWithAllergen = ArrayList<ConfectioneryItem>()
//        viewModelScope.launch {
//            _status.value = NetworkLoadingStatus.LOADING
//            ConfectioneryItemFirebase().getAllItems()
//                .addOnSuccessListener { documents ->
//                    Log.d(ContentValues.TAG, "DELETE SUCCEESS BEFORE")
//                    itemsWithAllergen = makeItemListForAllergen(id, documents)
//                    _status.value = NetworkLoadingStatus.DONE
//                }
//                .addOnFailureListener {
//                    _status.value = NetworkLoadingStatus.ERROR
//                }
//        }
//        return itemsWithAllergen
//    }

//    suspend fun makeItemListForAllergen(
//        id: String,
//        documents: Deferred<Task<QuerySnapshot>>
//    ): ArrayList<ConfectioneryItem> {
//        var itemList: ArrayList<ConfectioneryItem> = ArrayList()
//        for (document in documents) {
//            val oneItem = document.toObject<ConfectioneryItem>()
//            for (allergen in oneItem.containsAllergens) {
//                if (allergen == id) {
//                    itemList.add(oneItem)
//                    break
//                }
//            }
//        }
//        return itemList
//    }

    fun completeDeleteAllergen(id: String, position: Int) {
        viewModelScope.launch {
            _status.value = NetworkLoadingStatus.LOADING
//            async {
//            val data = getAllAllergens()
            AllergenFirebase().deleteAllergen(id)
                .addOnSuccessListener {
                    _status.value = NetworkLoadingStatus.DONE
                    Log.d(ContentValues.TAG, "DELETED SUCCEESS")
//                        val data = getAllAllergens()
//                        AllergenAdapter(data).removeData(position)
//                        AllergenAdapter().notifyItemRemoved(position)
//                        AllergenAdapter().notifyItemRangeChanged(position, AllergenAdapter().itemCount - 1)


//                        holder.itemView.
//                        AllergenAdapter().notifyDataSetChanged()
                    Log.d(ContentValues.TAG, "NOTIFY SUCCEESS")
//                    AllergenAdapter().hideDeletedRow(holder)
                }
                .addOnFailureListener {
                    _status.value = NetworkLoadingStatus.ERROR
                    Log.d(ContentValues.TAG, "DELETED FAILURE")
                }
//            }.await()
        }
    }
}
