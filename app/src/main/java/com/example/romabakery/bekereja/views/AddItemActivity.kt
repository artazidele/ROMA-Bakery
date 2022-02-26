package com.example.romabakery.bekereja.views

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.romabakery.R
import com.example.romabakery.bekereja.Navigation
import com.example.romabakery.bekereja.models.items.CakeWeight
import com.example.romabakery.bekereja.viewmodels.AllergenDataViewModel
import com.example.romabakery.bekereja.viewmodels.NetworkDataViewModel
import com.example.romabakery.databinding.ActivityAddItemBinding
import java.util.*
import kotlin.collections.ArrayList

var added_allergens: ArrayList<String> = ArrayList()
var cake_weights: ArrayList<CakeWeight> = ArrayList()
class AddItemActivity : AppCompatActivity() {
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

        binding.cakeWeightRv.layoutManager = LinearLayoutManager(this)
        showCakeWeights()
        binding.addOtherWeight.setOnClickListener {
            addOtherWeight()
        }

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

    private fun showCakeWeights() {
        binding.cakeWeightRv.adapter = AddItemWeightAdapter(cake_weights)
    }

    private fun addOtherWeight() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.cake_weight_window, null)
        val builder = AlertDialog.Builder(this)
            .setView(dialogView)
        val alertDialog = builder.show()
        dialogView.findViewById<Button>(R.id.close_button).setOnClickListener {
            alertDialog.dismiss()
        }
        dialogView.findViewById<Button>(R.id.add_cake_button).setOnClickListener {
            val weight = dialogView.findViewById<EditText>(R.id.weight_et).text.toString().toInt()
            val max = dialogView.findViewById<EditText>(R.id.max_et).text.toString().toInt()
            val centi = dialogView.findViewById<EditText>(R.id.eiro_et).text.toString().toInt()
            val eiro = dialogView.findViewById<EditText>(R.id.centi_et).text.toString().toInt()
            val uuid = UUID.randomUUID()
            val weightId = uuid.toString()
            val cakeId = ""
            val newWeight = CakeWeight(weightId, cakeId, weight, eiro, centi, max)
            addWeight(newWeight)
            showCakeWeights()
            alertDialog.dismiss()
        }
    }

    public fun addWeight(weight: CakeWeight) {
        cake_weights.add(weight)
    }

    public fun editWeight(position: Int, editedWeight: CakeWeight) {
        cake_weights.set(position, editedWeight)
    }

    public fun removeWeight(weight: CakeWeight) {
        cake_weights.remove(weight)
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
