package com.example.romabakery.bekereja.views

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.romabakery.R
import com.example.romabakery.bekereja.models.allergens.AllergenDataClass
import com.example.romabakery.bekereja.viewmodels.AllergenDataViewModel
import com.example.romabakery.bekereja.viewmodels.NetworkDataViewModel
import com.example.romabakery.databinding.ActivityAllergenListBinding
import com.example.romabakery.mvvm.MyAdapter
import com.example.romabakery.viewmodel.NetworkViewModel

class AllergenListActivity : AppCompatActivity() {
    private val viewModel: AllergenDataViewModel by viewModels()
    private lateinit var binding: ActivityAllergenListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllergenListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTitle("Alergēnu saraksts")

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.allAllergenRecyclerView.layoutManager = LinearLayoutManager(this)
        if (NetworkDataViewModel().checkConnection(this) == true) {
            refreshAllergenList()
        }
    }

    private fun refreshAllergenList() {
        viewModel.getAllAllergens() {
            if (it?.isNotEmpty() == true) {
                binding.allAllergenRecyclerView.adapter = AllergenDataAdapter(it!!)
                binding.allAllergenRecyclerView.visibility = View.VISIBLE
                Log.d(ContentValues.TAG, "NOT EMPTY")
            } else {
                Log.d(ContentValues.TAG, "EMPTY")
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.confectionery_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_new_allergen -> addNewAllergen()
            R.id.get_one_allergen -> getOneAllergenExample("12121212")
//            R.id.settings -> Toast.makeText(this,"Settings Selected",Toast.LENGTH_SHORT).show()
//            R.id.exit -> Toast.makeText(this,"Exit Selected",Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }

    public fun addNewAllergen() {
        Log.d(ContentValues.TAG, "ADD NEW ALLERGEN PRESSED")
        val allergen = AllergenDataClass(
            "12121212",
            "1212 alergēns Tikko",
            "madeBy",
            ArrayList<String>(),
            ArrayList<String>()
        )
        if (NetworkDataViewModel().checkConnection(this) == true) {
            viewModel.addNewAllergen(allergen) { added ->
                if (added == true) {
                    refreshAllergenList()
                } else {

                }
            }
        }
    }

    public fun getOneAllergenExample(id: String) {
        Log.d(ContentValues.TAG, "GET ALLERGEN PRESSED")
        if (NetworkViewModel().checkConnection(this) == true) {
            viewModel.getOneAllergen(id) { allergen ->
                if (allergen?.id != null) {
                    Log.d(ContentValues.TAG, allergen.title)
                } else {
                    Log.d(ContentValues.TAG, "ERROR getting one allergen")
                }
            }
        }
    }
}
