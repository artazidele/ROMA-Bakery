package com.example.romabakery.mvvm

import android.content.ContentValues
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
//            allergenViewModel.getLiveAllergens() { result ->
//                if (result?.value != null) {
//                    binding.allAllergenRecyclerView.adapter = MySecondAdapter()
//                    binding.allAllergenRecyclerView.visibility = View.VISIBLE
//                    Log.d(ContentValues.TAG, "NOT EMPTY")
//                    allergenViewModel.allergens.observe(this, Observer { allergens ->
//                        // Update the cached copy of the words in the adapter.
//                        allergens?.let { MySecondAdapter().submitList(allergens) }
//                        Log.d(ContentValues.TAG, allergens.size.toString())
//
//                    })
//                }
//
//            }



            allergenViewModel.getAllergens() {
                if (it?.isNotEmpty() == true) {
                    binding.allAllergenRecyclerView.adapter = MyAdapter(it!!)
                    binding.allAllergenRecyclerView.visibility = View.VISIBLE
                    Log.d(ContentValues.TAG, "NOT EMPTY")
                } else {
                    Log.d(ContentValues.TAG, "EMPTY")
                }
            }






////            val data = allergenViewModel.getAllergens()
////            binding.allAllergenRecyclerView.adapter = AllergenAdapter(data)//()//(data)
////            binding.allAllergenRecyclerView.visibility = View.VISIBLE
//
//
////            showAllergens() //allergenViewModel.getAllAllergens()
////            allergenViewModel.getAllAllergens()
////            binding.allAllergenRecyclerView.visibility = View.VISIBLE
        }
//
//        setHasOptionsMenu(true)
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
//            R.id.settings -> Toast.makeText(this,"Settings Selected",Toast.LENGTH_SHORT).show()
//            R.id.exit -> Toast.makeText(this,"Exit Selected",Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }

    public fun addNewAllergen() {
//        Log.d(TAG, "ADD NEW ALLERGEN PRESSED")
//        val allergen = Allergen("155556", "Opolokj alergēns Tikko", "madeBy", ArrayList<String>(), ArrayList<String>())
//        if (NetworkViewModel().checkConnection(this) == true) {
////            binding.allAllergenRecyclerView.visibility = View.INVISIBLE
//            allergenViewModel.addAllergen(allergen)
////            showAllergens()
//        }
//        val allergen2 = Allergen("896", "gfgchalergēns Tikko", "madeBy", ArrayList<String>(), ArrayList<String>())
//        if (NetworkViewModel().checkConnection(this) == true) {
//            allergenViewModel.addAllergen(allergen2)
//        }
//        val allergen3 = Allergen("89611", "ppppppgchalergēns Tikko", "madeBy", ArrayList<String>(), ArrayList<String>())
//        if (NetworkViewModel().checkConnection(this) == true) {
//            allergenViewModel.addAllergen(allergen3)
//        }
//        val allergen4 = Allergen("893334441", "ppppppgchalergēns Tikko", "madeBy", ArrayList<String>(), ArrayList<String>())
//        if (NetworkViewModel().checkConnection(this) == true) {
//            allergenViewModel.addAllergen(allergen4)
//        }
//        val allergen5 = Allergen("23231", "ppppppgchalergēns Tikko", "madeBy", ArrayList<String>(), ArrayList<String>())
//        if (NetworkViewModel().checkConnection(this) == true) {
//            allergenViewModel.addAllergen(allergen5)
//        }
    }

//    public fun showAllergens() {
//        allergenViewModel.getAllAllergens()
//    }
}
