package com.example.romabakery.bekereja.views

import android.content.ContentValues
import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.romabakery.R
import com.example.romabakery.bekereja.Navigation
import com.example.romabakery.bekereja.models.allergens.AllergenDataClass
import com.example.romabakery.bekereja.viewmodels.ItemDataViewModel
import com.example.romabakery.bekereja.viewmodels.NetworkDataViewModel
import com.example.romabakery.databinding.ActivityItemListBinding

class ItemListActivity : AppCompatActivity() {
    private val viewModel: ItemDataViewModel by viewModels()
    private lateinit var binding: ActivityItemListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTitle("Izstrādājumi")

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.allAllergenRecyclerView.layoutManager = LinearLayoutManager(this)
        if (NetworkDataViewModel().checkConnection(this) == true) {
            refreshItemList()
        }
    }

    private fun refreshItemList() {
        viewModel.getAllItems(true, true, true, false, false, false, false, ArrayList<String>(), false) {
            if (it?.isNotEmpty() == true) {
                binding.allAllergenRecyclerView.adapter = ItemDataAdapter(it!!)
                binding.allAllergenRecyclerView.visibility = View.VISIBLE
                Log.d(ContentValues.TAG, "NOT EMPTY")
            } else {
                Log.d(ContentValues.TAG, "EMPTY")
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.confectioner_item_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.allergens -> Navigation().fromTo(this, AllergenListActivity())
            R.id.add -> Navigation().fromTo(this, AddItemActivity())//addNewItem()
            R.id.filter -> filterItems()
//            R.id.add_new_allergen -> addNewAllergen()
//            R.id.get_one_allergen -> getOneAllergenExample("12121212")
//            R.id.settings -> Toast.makeText(this,"Settings Selected",Toast.LENGTH_SHORT).show()
//            R.id.exit -> Toast.makeText(this,"Exit Selected",Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun addNewItem() {
        Log.d(TAG, "Add new item pressed.")
    }

    private fun filterItems() {
        Log.d(TAG, "Filter items pressed.")
    }



//    public fun addNewAllergen() {
//        Log.d(ContentValues.TAG, "ADD NEW ALLERGEN PRESSED")
//        val allergen = AllergenDataClass(
//            "12121212",
//            "1212 alergēns Tikko",
//            "madeBy",
//            ArrayList<String>(),
//            ArrayList<String>()
//        )
//        if (NetworkDataViewModel().checkConnection(this) == true) {
//            viewModel.addNewAllergen(allergen) { added ->
//                if (added == true) {
//                    refreshAllergenList()
//                } else {
//
//                }
//            }
//        }
//    }

//    public fun getOneAllergenExample(id: String) {
//        Log.d(ContentValues.TAG, "GET ALLERGEN PRESSED")
//        if (NetworkViewModel().checkConnection(this) == true) {
//            viewModel.getOneAllergen(id) { allergen ->
//                if (allergen?.id != null) {
//                    Log.d(ContentValues.TAG, allergen.title)
//                } else {
//                    Log.d(ContentValues.TAG, "ERROR getting one allergen")
//                }
//            }
//        }
//    }
}