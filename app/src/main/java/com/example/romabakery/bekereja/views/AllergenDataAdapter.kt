package com.example.romabakery.bekereja.views

import android.content.ContentValues
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
import com.example.romabakery.bekereja.models.allergens.AllergenDataClass
import com.example.romabakery.bekereja.models.items.CakeWeight
import com.example.romabakery.bekereja.viewmodels.AllergenDataViewModel
import com.example.romabakery.bekereja.viewmodels.NetworkDataViewModel

class AllergenDataAdapter(private val dataSet: ArrayList<AllergenDataClass>) :
    RecyclerView.Adapter<AllergenDataAdapter.AllergenDataViewHolder>() {

    class AllergenDataViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView
        val deleteButton: Button
        val editButton: Button

        init {
            textView = view.findViewById(R.id.allergen_title)
            deleteButton = view.findViewById(R.id.delete_allergen_button)
            editButton = view.findViewById(R.id.edit_allergen_button)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): AllergenDataViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.allergen_list_row, viewGroup, false)

        return AllergenDataViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: AllergenDataViewHolder, position: Int) {
        viewHolder.textView.text = dataSet[position].title
        viewHolder.deleteButton.setOnClickListener {
            Log.d(ContentValues.TAG, "Delete button pressed")
            if (NetworkDataViewModel().checkConnection(viewHolder.itemView.context) == true) {
                AllergenDataViewModel().getAllergenItems(dataSet[position].id) { itemList ->
                    if (itemList?.isEmpty() == true) {
                        Log.d(ContentValues.TAG, "CAN DELETE")
                        AllergenDataViewModel().deleteOneAllergen(dataSet[position].id) { isDeleted ->
                            if (isDeleted == true) {
                                Log.d(ContentValues.TAG, "TRUE")
                                Log.d(ContentValues.TAG, "Position: " + position.toString())
                                dataSet.removeAt(position)
                                notifyDataSetChanged()
                                Log.d(ContentValues.TAG, "Count: " + itemCount.toString())
                            } else {
                                Log.d(ContentValues.TAG, "FALSE")
                            }
                        }
                    } else if (itemList?.isNotEmpty() == true) {
                        Log.d(ContentValues.TAG, "CANNOT DELETE")
                    } else {
                        Log.d(ContentValues.TAG, "NULL")
                    }
                }
            }
        }
        viewHolder.editButton.setOnClickListener {
            Log.d(ContentValues.TAG, "Edit button pressed")
            if (NetworkDataViewModel().checkConnection(viewHolder.itemView.context) == true) {
                val editedAllergen = AllergenDataClass(dataSet[position].id, "Labots1234", dataSet[position].madeBy, dataSet[position].editedOn, dataSet[position].editedBy)
                AllergenDataViewModel().updateOneAllergen(editedAllergen) { isEdited ->
                    if (isEdited == true) {
                        Log.d(ContentValues.TAG, "isEdited TRUE")
                        dataSet.set(position, editedAllergen)
                        notifyDataSetChanged()
                    } else {
                        Log.d(ContentValues.TAG, "isEdited FALSE")
                    }
                }
            }
//            notifyItemChanged(position)
        }
    }
    override fun getItemCount() = dataSet.size
    private fun deleteAllergen() {

    }
    private fun editAllergen(allergen: AllergenDataClass, viewHolder: RecyclerView.ViewHolder, position: Int) {
//        val dialogView = LayoutInflater.from(viewHolder.itemView.context).inflate(R.layout.cake_weight_window, null)
//        val builder = AlertDialog.Builder(viewHolder.itemView.context)
//            .setView(dialogView)
//        val alertDialog = builder.show()
//        dialogView.findViewById<Button>(R.id.close_button).setOnClickListener {
//            alertDialog.dismiss()
//        }
//        dialogView.findViewById<EditText>(R.id.weight_et).setText(weight.weight.toString())
//        dialogView.findViewById<EditText>(R.id.max_et).setText(weight.maxADay.toString())
//        dialogView.findViewById<EditText>(R.id.centi_et).setText(weight.eiro.toString())
//        dialogView.findViewById<EditText>(R.id.eiro_et).setText(weight.centi.toString())
//        dialogView.findViewById<Button>(R.id.add_cake_button).text = "Saglabāt izmaiņas"
//        dialogView.findViewById<Button>(R.id.add_cake_button).setOnClickListener {
//            val weightInt = dialogView.findViewById<EditText>(R.id.weight_et).text.toString().toInt()
//            val max = dialogView.findViewById<EditText>(R.id.max_et).text.toString().toInt()
//            val centi = dialogView.findViewById<EditText>(R.id.eiro_et).text.toString().toInt()
//            val eiro = dialogView.findViewById<EditText>(R.id.centi_et).text.toString().toInt()
//            val weightId = weight.id
//            val cakeId = ""
//            val editedWeight = CakeWeight(weightId, cakeId, weightInt, eiro, centi, max)
//            AddItemActivity().removeWeight(weight)
//            AddItemActivity().addWeight(editedWeight)
//            dataSet.set(position, editedWeight)
//            notifyDataSetChanged()
//            alertDialog.dismiss()
//        }
    }
}
