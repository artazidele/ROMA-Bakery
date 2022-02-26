package com.example.romabakery.bekereja.views

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.romabakery.R
import com.example.romabakery.bekereja.models.items.CakeWeight
import java.util.*
import kotlin.collections.ArrayList

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
//            Log.d(TAG, "Delete pressed")
            deleteWeight(dataSet[position], viewHolder, position)
        }
        viewHolder.editButton.setOnClickListener {
//            Log.d(TAG, "Edit pressed")
            editWeight(dataSet[position], viewHolder, position)
        }
    }

    override fun getItemCount() = dataSet.size

    private fun editWeight(weight: CakeWeight, viewHolder: RecyclerView.ViewHolder, position: Int) {
        val dialogView = LayoutInflater.from(viewHolder.itemView.context).inflate(R.layout.cake_weight_window, null)
        val builder = AlertDialog.Builder(viewHolder.itemView.context)
            .setView(dialogView)
        val alertDialog = builder.show()
        dialogView.findViewById<Button>(R.id.close_button).setOnClickListener {
            alertDialog.dismiss()
        }
        dialogView.findViewById<EditText>(R.id.weight_et).setText(weight.weight.toString())
        dialogView.findViewById<EditText>(R.id.max_et).setText(weight.maxADay.toString())
        dialogView.findViewById<EditText>(R.id.centi_et).setText(weight.eiro.toString())
        dialogView.findViewById<EditText>(R.id.eiro_et).setText(weight.centi.toString())
        dialogView.findViewById<Button>(R.id.add_cake_button).text = "Saglabāt izmaiņas"
        dialogView.findViewById<Button>(R.id.add_cake_button).setOnClickListener {
            val weightInt = dialogView.findViewById<EditText>(R.id.weight_et).text.toString().toInt()
            val max = dialogView.findViewById<EditText>(R.id.max_et).text.toString().toInt()
            val centi = dialogView.findViewById<EditText>(R.id.eiro_et).text.toString().toInt()
            val eiro = dialogView.findViewById<EditText>(R.id.centi_et).text.toString().toInt()
            val weightId = weight.id
            val cakeId = ""
            val editedWeight = CakeWeight(weightId, cakeId, weightInt, eiro, centi, max)
            AddItemActivity().removeWeight(weight)
            AddItemActivity().addWeight(weight)
//            AddItemActivity().editWeight(position, editedWeight)
            dataSet.set(position, editedWeight)
            notifyDataSetChanged()
            alertDialog.dismiss()
        }
    }
    private fun deleteWeight(weight: CakeWeight, viewHolder: RecyclerView.ViewHolder, position: Int) {
        val dialogView = LayoutInflater.from(viewHolder.itemView.context).inflate(R.layout.delete_window, null)
        val builder = AlertDialog.Builder(viewHolder.itemView.context)
            .setView(dialogView)
        val alertDialog = builder.show()
        dialogView.findViewById<TextView>(R.id.item_title_tv).text = weight.weight.toString() + " grami"
        dialogView.findViewById<TextView>(R.id.question_tv).text = "Vai izdzēst šo tortes svaru?"
        dialogView.findViewById<Button>(R.id.close_button).setOnClickListener {
            alertDialog.dismiss()
        }
        dialogView.findViewById<Button>(R.id.not_delete_button).text = "Tomēr saglabāt tortes svaru"
        dialogView.findViewById<Button>(R.id.not_delete_button).setOnClickListener {
            alertDialog.dismiss()
        }
        dialogView.findViewById<Button>(R.id.delete_button).text = "Dzēst tortes svaru"
        dialogView.findViewById<Button>(R.id.delete_button).setOnClickListener {
            dataSet.removeAt(position)
            notifyDataSetChanged()
            AddItemActivity().removeWeight(weight)
            alertDialog.dismiss()
        }
    }
}
