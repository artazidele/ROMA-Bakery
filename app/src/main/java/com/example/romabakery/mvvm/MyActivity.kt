package com.example.romabakery.mvvm

import android.content.ContentValues
import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.romabakery.R
import com.example.romabakery.view.allergens.AllergenAdapter


import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.romabakery.databinding.ActivityMyBinding
import com.example.romabakery.model.Allergen
import com.example.romabakery.viewmodel.AllergenViewModel
import com.example.romabakery.viewmodel.NetworkViewModel



class MyActivity : AppCompatActivity() {
    private val allergenViewModel: MyViewModel by viewModels()
    private lateinit var binding: ActivityMyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTitle("Alergēnu saraksts")

        binding.lifecycleOwner = this
        binding.viewModel = allergenViewModel

        binding.allAllergenRecyclerView.layoutManager = LinearLayoutManager(this)

//        allergenViewModel.allergens.observe(this, Observer { words ->
//            // Update the cached copy of the words in the adapter.
//            words?.let { MySecondAdapter().submitList(it) }
//        })


        if (NetworkViewModel().checkConnection(this) == true) {
            refreshAllergenList()
        }
    }

    private fun refreshAllergenList() {
        allergenViewModel.getAllergens() {
            if (it?.isNotEmpty() == true) {
                    binding.allAllergenRecyclerView.adapter = MyAdapter(it!!)
                    binding.allAllergenRecyclerView.visibility = View.VISIBLE
                Log.d(ContentValues.TAG, "NOT EMPTY")
            } else {
                Log.d(ContentValues.TAG, "EMPTY")
            }
        }
    }

//    private fun itemRemoved(int: Int) {
//        binding.allAllergenRecyclerView.adapter?.notifyItemRemoved(int)
//    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.confectionery_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.add_new_allergen -> addNewAllergen()
            R.id.get_one_allergen -> getOneAllergenExample("12121212")
//            R.id.settings -> Toast.makeText(this,"Settings Selected",Toast.LENGTH_SHORT).show()
//            R.id.exit -> Toast.makeText(this,"Exit Selected",Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }

    public fun addNewAllergen() {
        Log.d(TAG, "ADD NEW ALLERGEN PRESSED")
        val allergen = Allergen("12121212", "1212 alergēns Tikko", "madeBy", ArrayList<String>(), ArrayList<String>())
        if (NetworkViewModel().checkConnection(this) == true) {
            allergenViewModel.addNewAllergen(allergen) { added ->
                if (added == true) {
                    refreshAllergenList()
                } else {

                }
            }
        }
    }

    public fun getOneAllergenExample(id: String) {
        Log.d(TAG, "GET ALLERGEN PRESSED")
        if (NetworkViewModel().checkConnection(this) == true) {
            allergenViewModel.getOneAllergen(id) { allergen ->
                if (allergen?.id != null) {
                    Log.d(TAG, allergen.title)
                } else {
                    Log.d(TAG, "ERROR getting one allergen")
                }
            }
        }
    }
}
