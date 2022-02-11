package com.example.romabakery.bekereja.views

import android.content.ContentValues
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.romabakery.R
import com.example.romabakery.bekereja.models.items.ItemDataClass

class ItemDataAdapter(private val dataSet: ArrayList<ItemDataClass>) :
    RecyclerView.Adapter<ItemDataAdapter.ItemDataViewHolder>() {

    class ItemDataViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView
        val deleteButton: Button
        val editButton: Button

        init {
            textView = view.findViewById(R.id.allergen_title)
            deleteButton = view.findViewById(R.id.delete_allergen_button)
            editButton = view.findViewById(R.id.edit_allergen_button)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ItemDataViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.allergen_list_row, viewGroup, false)

        return ItemDataViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ItemDataViewHolder, position: Int) {
        viewHolder.textView.text = dataSet[position].title
    }

    override fun getItemCount() = dataSet.size
}
