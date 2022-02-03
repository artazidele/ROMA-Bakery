package com.example.romabakery.view

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.romabakery.databinding.FragmentChooseItemsBinding
import com.example.romabakery.view.ChooseItemsAdapter
import com.example.romabakery.viewmodel.ChooseItemsViewModel


class ChooseItemsFragment : Fragment() {

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
        chooseViewModel.getConfItems()
        binding.photosGrid.adapter = ChooseItemsAdapter()
//        binding.photosGrid.visibility = View.VISIBLE
//        binding.statusRetry.setOnClickListener {
//            showCountWindow()
//        }
        setHasOptionsMenu(true)
        return binding.root
    }

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
//        showCountWindow()
    }


}