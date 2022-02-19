package com.example.romabakery.bekereja.views

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.romabakery.R
import com.example.romabakery.bekereja.models.items.CakeWeight

class AddItemWeightAdapter(private val dataSet: ArrayList<CakeWeight>) :
    RecyclerView.Adapter<AddItemWeightAdapter.AddItemWeightViewHolder>() {

    class AddItemWeightViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val weightTV: TextView
        val amountTV: TextView
        val costTV: TextView
        val deleteButton: Button
        val editButton: Button

        init {
            weightTV = view.findViewById(R.id.weight_tv)
            amountTV = view.findViewById(R.id.max_tv)
            costTV = view.findViewById(R.id.cost_tv)
            deleteButton = view.findViewById(R.id.delete_weight_button)
            editButton = view.findViewById(R.id.edit_weight_button)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): AddItemWeightViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.weight_row, viewGroup, false)

        return AddItemWeightViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: AddItemWeightViewHolder, position: Int) {
        viewHolder.weightTV.text =
            "Vienas tortes svars: " + dataSet[position].weight.toString() + " g"
        viewHolder.amountTV.text =
            "Paredzētais daudzums: " + dataSet[position].maxADay.toString() + " tortes dienā"
        var centi = dataSet[position].centi.toString()
        if (dataSet[position].centi < 10) {
            centi = "0" + dataSet[position].centi.toString()
        }
        viewHolder.costTV.text =
            "Cena: " + dataSet[position].eiro.toString() + "." + centi + " eiro/gab"
        viewHolder.deleteButton.setOnClickListener {
            Log.d(TAG, "Delete pressed")
        }
        viewHolder.editButton.setOnClickListener {
            Log.d(TAG, "Edit pressed")
        }
    }

    override fun getItemCount() = dataSet.size
}
