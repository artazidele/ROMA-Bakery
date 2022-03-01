package com.example.romabakery.bekereja.views

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.romabakery.R
import com.example.romabakery.bekereja.Navigation
import com.example.romabakery.bekereja.models.allergens.AllergenDataClass
import com.example.romabakery.bekereja.models.items.CakeWeight
import com.example.romabakery.bekereja.viewmodels.AllergenDataViewModel
import com.example.romabakery.bekereja.viewmodels.NetworkDataViewModel
import com.example.romabakery.databinding.ActivityAllergenListBinding
import com.example.romabakery.viewmodel.NetworkViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

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

    public fun refreshAllergenList() {
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
        menuInflater.inflate(R.menu.confectionery_allergen_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add -> addNewAllergen()
            R.id.items -> Navigation().fromTo(this, ItemListActivity())
//            R.id.add_new_allergen -> addNewAllergen()
//            R.id.get_one_allergen -> getOneAllergenExample("12121212")
//            R.id.settings -> Toast.makeText(this,"Settings Selected",Toast.LENGTH_SHORT).show()
//            R.id.exit -> Toast.makeText(this,"Exit Selected",Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }

    public fun addNewAllergen() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.add_allergen_window, null)
        val builder = AlertDialog.Builder(this)
            .setView(dialogView)
        val alertDialog = builder.show()
        dialogView.findViewById<Button>(R.id.close_button).setOnClickListener {
            alertDialog.dismiss()
        }
        dialogView.findViewById<Button>(R.id.add_allergen_button).setOnClickListener {
            val title = dialogView.findViewById<EditText>(R.id.allergen_title_et).text.toString()
            val madeBy = "ConfectionerId" // Get current user id or etc
            val editedBy: ArrayList<String> = ArrayList()
            val editedOn: ArrayList<String> = ArrayList()
            val dateAndTimeNow = LocalDateTime.now()
            val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
            val dateAndTimeToSave = dateAndTimeNow.format(dateFormat).toString()
            editedBy.add(madeBy)
            editedOn.add(dateAndTimeToSave)
            val uuid = UUID.randomUUID()
            val id = uuid.toString()
            val newAllergen = AllergenDataClass(id, title, madeBy, editedBy, editedOn)
            if (NetworkDataViewModel().checkConnection(this) == true) {
                viewModel.addNewAllergen(newAllergen) { added ->
                    if (added == true) {
                        refreshAllergenList()
                        alertDialog.dismiss()
                    } else {

                    }
                }
            }
        }
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
    }
}
