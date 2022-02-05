package com.example.romabakery.view

import android.content.ContentValues.TAG
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.romabakery.databinding.FragmentChooseItemsBinding
import com.example.romabakery.model.ConfectioneryItem
import com.example.romabakery.view.ChooseItemsAdapter
import com.example.romabakery.viewmodel.ChooseItemsViewModel
import kotlinx.coroutines.delay


class ChooseItemsFragment : Fragment() {


    fun toast(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    private fun checkConnectionType(): Boolean {
        val connectionManager =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val wifi_Connection = connectionManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        val mobile_data_connection =
            connectionManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)

        if (wifi_Connection!!.isConnectedOrConnecting) {
            toast("WIFI Connection is on")
            return true
        } else {
            if (mobile_data_connection!!.isConnectedOrConnecting) {
                toast("Mobile Data Connection is on")
                return true
            } else {
                toast("No Network Connection")
            }
        }
        return false
    }


    private val chooseViewModel: ChooseItemsViewModel by viewModels()
    var _binding: FragmentChooseItemsBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChooseItemsBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = chooseViewModel

        val item = ConfectioneryItem(
            "987",
            "title",
            true,
            false,
            false,
            10,
            10,
            10,
            false,
            false,
            false,
            false,
            "from",
            "until",
            "ingredients",
            "description",
            ArrayList<Int>(),
            "madeBy",
            ArrayList<String>(),
            ArrayList<String>(),
            false,
            "notInProductionBy",
            ArrayList<String>(),
        )
//        checkConnectionType()


//        if (checkConnectionType() == true) {
//            chooseViewModel.addConfectioneryItem(item)
//            Log.d(TAG, "INTERNET TRUE")
//        } else {
//            Log.d(TAG, "INTERNET FALSE")
//        }

//        chooseViewModel.addConfectioneryItem(item)


        chooseViewModel.getConfectioneryItems(true, false, false, false, false, false, false, ArrayList<String>(), false)
        binding.photosGrid.adapter = ChooseItemsAdapter()
        binding.photosGrid.visibility = View.VISIBLE


//        binding.statusRetry.setOnClickListener {
//            showCountWindow()
//        }
        setHasOptionsMenu(true)
        return binding.root
    }

    fun isOnline(): Boolean {
        val connectivityManager =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }

//    private fun checkForInternet(context: Context, connectivityManager: ConnectivityManager): Boolean {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            val network = connectivityManager.activeNetwork ?: return false
//            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
//            return when {
//                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
//                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
//                else -> false
//            }
//        } else {
//            @Suppress("DEPRECATION") val networkInfo =
//                connectivityManager.activeNetworkInfo ?: return false
//            @Suppress("DEPRECATION")
//            return networkInfo.isConnected
//        }
//    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.menu_layout, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            R.id.find_item -> showCountWindow()
//            R.id.find_votes -> showMyVotes()
//            R.id.log_out -> logOut()
//        }
        return super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


//        val connectivityManager = CreateOrderActivity().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager


//        showCountWindow()
    }


}