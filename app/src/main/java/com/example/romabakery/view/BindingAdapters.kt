package com.example.romabakery.view

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.romabakery.model.ConfectioneryItem
import com.example.romabakery.viewmodel.NetworkStatus

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<ConfectioneryItem>?) {
    val adapter = recyclerView.adapter as ChooseItemsAdapter
    adapter.submitList(data)
}
@BindingAdapter("itemTitle")
fun bindImage(textView: TextView, text: String?) {
    textView.text = text
}
@BindingAdapter("networkStatus")
fun bindStatus(statusTextView: TextView, status: NetworkStatus?) {
    when (status) {
        NetworkStatus.LOADING -> {
            statusTextView.visibility = View.VISIBLE
            statusTextView.text = "Loading..."
        }
        NetworkStatus.ERROR -> {
            statusTextView.visibility = View.VISIBLE
            statusTextView.text = "Something went wrong."
        }
        NetworkStatus.DONE -> {
            statusTextView.visibility = View.GONE
        }
    }
}
@BindingAdapter("networkIconStatus")
fun bindIconStatus(statusTextView: Button, status: NetworkStatus?) {
    when (status) {
        NetworkStatus.LOADING -> {
            statusTextView.visibility = View.GONE
        }
        NetworkStatus.ERROR -> {
            statusTextView.visibility = View.VISIBLE
        }
        NetworkStatus.DONE -> {
            statusTextView.visibility = View.GONE
        }
    }
}
