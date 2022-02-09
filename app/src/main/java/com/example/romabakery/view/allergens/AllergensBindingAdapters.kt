package com.example.romabakery.view

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.romabakery.model.Allergen
import com.example.romabakery.mvvm.MyAdapter
import com.example.romabakery.view.allergens.AllergenAdapter
import com.example.romabakery.viewmodel.NetworkLoadingStatus



//@BindingAdapter("myList")
//fun bindMyRecyclerView(recyclerView: RecyclerView, data: List<Allergen>?) {
////    val adapter = recyclerView.adapter as MyAdapter
////    adapter.submitList(data)
//}
@BindingAdapter("myTitle")
fun bindMyTitle(textView: TextView, text: String?) {
    textView.text = text
}


@BindingAdapter("allergenList")
fun bindAllergenRecyclerView(recyclerView: RecyclerView, data: List<Allergen>?) {
    val adapter = recyclerView.adapter as AllergenAdapter
    adapter.submitList(data)
}
@BindingAdapter("allergenTitle")
fun bindAllergenTitle(textView: TextView, text: String?) {
    textView.text = text
}
@BindingAdapter("allergenRecyclerViewStatus")
fun bindAllergenRecyclerViewStatus(recyclerView: RecyclerView, status: NetworkLoadingStatus?) {
    when (status) {
        NetworkLoadingStatus.LOADING -> {
            recyclerView.visibility = View.GONE
        }
        NetworkLoadingStatus.ERROR -> {
            recyclerView.visibility = View.GONE
        }
        NetworkLoadingStatus.DONE -> {
            recyclerView.visibility = View.VISIBLE
        }
    }
}
@BindingAdapter("allergenStatus")
fun bindNetworkLoadingStatus(statusTextView: TextView, status: NetworkLoadingStatus?) {
    when (status) {
        NetworkLoadingStatus.LOADING -> {
            statusTextView.visibility = View.VISIBLE
            statusTextView.text = "Loading..."
        }
        NetworkLoadingStatus.ERROR -> {
            statusTextView.visibility = View.VISIBLE
            statusTextView.text = "Something went wrong."
        }
        NetworkLoadingStatus.DONE -> {
            statusTextView.visibility = View.GONE
        }
    }
}
@BindingAdapter("allergenButtonStatus")
fun bindAllergenButtonStatus(statusTextView: Button, status: NetworkLoadingStatus?) {
    when (status) {
        NetworkLoadingStatus.LOADING -> {
            statusTextView.visibility = View.GONE
        }
        NetworkLoadingStatus.ERROR -> {
            statusTextView.visibility = View.VISIBLE
        }
        NetworkLoadingStatus.DONE -> {
            statusTextView.visibility = View.GONE
        }
    }
}