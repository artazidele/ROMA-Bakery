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

class AllergenActivity : AppCompatActivity() {

    fun toast(msg: String, context: Context) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    private fun checkConnectionType(context: Context): Boolean {
        val connectionManager =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val wifi_Connection = connectionManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        val mobile_data_connection =
            connectionManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)

        if (wifi_Connection!!.isConnectedOrConnecting) {
            toast("WIFI Connection is on", context)
            return true
        } else {
            if (mobile_data_connection!!.isConnectedOrConnecting) {
                toast("Mobile Data Connection is on", context)
                return true
            } else {
                toast("No Network Connection", context)
            }
        }
        return false
    }




    private val allergenViewModel: AllergenViewModel by viewModels()
    private lateinit var binding: ActivityAllergenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllergenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.lifecycleOwner = this
        binding.viewModel = allergenViewModel

        binding.allAllergenRecyclerView.layoutManager = LinearLayoutManager(this)

        binding.allAllergenRecyclerView.adapter = AllergenAdapter()
        binding.allAllergenRecyclerView.visibility = View.VISIBLE
        if (checkConnectionType(this) == true) {
            showAllergens() //allergenViewModel.getAllAllergens()
//            allergenViewModel.getAllAllergens()
//            binding.allAllergenRecyclerView.visibility = View.VISIBLE
        }

//        setHasOptionsMenu(true)
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
        val allergen = Allergen("1297", "Ceturtais alergēns Tikko", "madeBy", ArrayList<String>(), ArrayList<String>())
        if (checkConnectionType(this) == true) {
            binding.allAllergenRecyclerView.visibility = View.INVISIBLE
            allergenViewModel.addAllergen(allergen)
            showAllergens()
        }
    }

    public fun showAllergens() {
        allergenViewModel.getAllAllergens()
    }
}
