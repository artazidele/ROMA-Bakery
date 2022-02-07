package com.example.romabakery.view

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.romabakery.model.Allergen
import com.example.romabakery.model.ConfectioneryItem
import com.example.romabakery.view.allergens.AllergenAdapter
import com.example.romabakery.viewmodel.AllergenStatus
import com.example.romabakery.viewmodel.NetworkStatus

@BindingAdapter("allergenList")
fun bindAllergenRecyclerView(recyclerView: RecyclerView, data: List<Allergen>?) {
    val adapter = recyclerView.adapter as AllergenAdapter
    adapter.submitList(data)
}
@BindingAdapter("allergenTitle")
fun bindAllergenTitle(textView: TextView, text: String?) {
    textView.text = text
}
@BindingAdapter("allergenStatus")
fun bindAllergenStatus(statusTextView: TextView, status: AllergenStatus?) {
    when (status) {
        AllergenStatus.LOADING -> {
            statusTextView.visibility = View.VISIBLE
            statusTextView.text = "Loading..."
        }
        AllergenStatus.ERROR -> {
            statusTextView.visibility = View.VISIBLE
            statusTextView.text = "Something went wrong."
        }
        AllergenStatus.DONE -> {
            statusTextView.visibility = View.GONE
        }
    }
}
@BindingAdapter("allergenButtonStatus")
fun bindAllergenButtonStatus(statusTextView: Button, status: AllergenStatus?) {
    when (status) {
        AllergenStatus.LOADING -> {
            statusTextView.visibility = View.GONE
        }
        AllergenStatus.ERROR -> {
            statusTextView.visibility = View.VISIBLE
        }
        AllergenStatus.DONE -> {
            statusTextView.visibility = View.GONE
        }
    }
}
