package com.example.romabakery.bekereja.views

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.romabakery.R
import com.example.romabakery.bekereja.Navigation
import com.example.romabakery.bekereja.viewmodels.AllergenDataViewModel
import com.example.romabakery.bekereja.viewmodels.ItemDataViewModel
import com.example.romabakery.bekereja.viewmodels.NetworkDataViewModel
import com.example.romabakery.databinding.ActivityAddItemBinding
import com.example.romabakery.databinding.ActivityItemListBinding

var added_allergens: ArrayList<String> = ArrayList()
class AddItemActivity : AppCompatActivity() {
//    private var addedAllergens: ArrayList<String> = ArrayList()
    private val viewModel: ItemDataViewModel by viewModels()
    private val allergenViewModel: AllergenDataViewModel by viewModels()
    private lateinit var binding: ActivityAddItemBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTitle("Jauns izstrādājums")
        actionBar?.setHomeButtonEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        binding.lifecycleOwner = this
        binding.viewModel = allergenViewModel

        binding.allAllergenRecyclerView.layoutManager = LinearLayoutManager(this)
        if (NetworkDataViewModel().checkConnection(this) == true) {
            showAllergenList()
        }

        binding.addItemButton.setOnClickListener {
            if (NetworkDataViewModel().checkConnection(this) == true) {
                showAddedAllergens()
            }
        }
    }

    private fun showAllergenList() {
        allergenViewModel.getAllAllergens() {
            if (it?.isNotEmpty() == true) {
                binding.allAllergenRecyclerView.adapter = AddItemAllergenAdapter(it!!)
                binding.allAllergenRecyclerView.visibility = View.VISIBLE
                Log.d(ContentValues.TAG, "NOT EMPTY")
            } else {
                Log.d(ContentValues.TAG, "EMPTY")
            }
        }
    }

    public fun addAllergen(allergenId: String) {
        added_allergens.add(allergenId)
        Log.d(ContentValues.TAG, allergenId)
    }

    public fun removeAllergen(allergenId: String) {
        added_allergens.remove(allergenId)
    }

    private fun showAddedAllergens() {
        Log.d(ContentValues.TAG, added_allergens.count().toString())
        for (allergen in added_allergens) {
            Log.d(ContentValues.TAG, allergen)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Navigation().fromTo(this, ItemListActivity())
        return true
    }
}
