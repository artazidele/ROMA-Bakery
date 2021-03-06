package com.example.romabakery.view.allergens

import android.content.ContentValues.TAG
import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.romabakery.R
import com.example.romabakery.databinding.ActivityAllergenBinding
import com.example.romabakery.model.Allergen
import com.example.romabakery.model.ConfectioneryItem
import com.example.romabakery.view.ChooseItemsAdapter
import com.example.romabakery.viewmodel.AllergenViewModel
import com.example.romabakery.viewmodel.ChooseItemsViewModel
import com.example.romabakery.viewmodel.NetworkViewModel

class AllergenActivity : AppCompatActivity() {
    private val allergenViewModel: AllergenViewModel by viewModels()
    private lateinit var binding: ActivityAllergenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllergenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.lifecycleOwner = this
        binding.viewModel = allergenViewModel

        binding.allAllergenRecyclerView.layoutManager = LinearLayoutManager(this)


        if (NetworkViewModel().checkConnection(this) == true) {
            val data = allergenViewModel.getAllAllergens()
            binding.allAllergenRecyclerView.adapter = AllergenAdapter(data)//()//(data)
            binding.allAllergenRecyclerView.visibility = View.VISIBLE


//            showAllergens() //allergenViewModel.getAllAllergens()
//            allergenViewModel.getAllAllergens()
//            binding.allAllergenRecyclerView.visibility = View.VISIBLE
        }

//        setHasOptionsMenu(true)
    }

    private fun itemRemoved(int: Int) {
        binding.allAllergenRecyclerView.adapter?.notifyItemRemoved(int)
    }

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
        Log.d(TAG, "ADD NEW ALLERGEN PRESSED")
        val allergen = Allergen("155556", "Opolokj alerg??ns Tikko", "madeBy", ArrayList<String>(), ArrayList<String>())
        if (NetworkViewModel().checkConnection(this) == true) {
//            binding.allAllergenRecyclerView.visibility = View.INVISIBLE
            allergenViewModel.addAllergen(allergen)
//            showAllergens()
        }
        val allergen2 = Allergen("896", "gfgchalerg??ns Tikko", "madeBy", ArrayList<String>(), ArrayList<String>())
        if (NetworkViewModel().checkConnection(this) == true) {
            allergenViewModel.addAllergen(allergen2)
        }
        val allergen3 = Allergen("89611", "ppppppgchalerg??ns Tikko", "madeBy", ArrayList<String>(), ArrayList<String>())
        if (NetworkViewModel().checkConnection(this) == true) {
            allergenViewModel.addAllergen(allergen3)
        }
        val allergen4 = Allergen("893334441", "ppppppgchalerg??ns Tikko", "madeBy", ArrayList<String>(), ArrayList<String>())
        if (NetworkViewModel().checkConnection(this) == true) {
            allergenViewModel.addAllergen(allergen4)
        }
        val allergen5 = Allergen("23231", "ppppppgchalerg??ns Tikko", "madeBy", ArrayList<String>(), ArrayList<String>())
        if (NetworkViewModel().checkConnection(this) == true) {
            allergenViewModel.addAllergen(allergen5)
        }
    }

    public fun showAllergens() {
        allergenViewModel.getAllAllergens()
    }
}
