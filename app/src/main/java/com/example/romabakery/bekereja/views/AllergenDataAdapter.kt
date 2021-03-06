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
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

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
            deleteAllergen(dataSet[position], viewHolder, position)
        }
        viewHolder.editButton.setOnClickListener {
            editAllergen(dataSet[position], viewHolder, position)
        }
    }
    override fun getItemCount() = dataSet.size
    private fun deleteAllergen(allergen: AllergenDataClass, viewHolder: RecyclerView.ViewHolder, position: Int) {
        val dialogView = LayoutInflater.from(viewHolder.itemView.context).inflate(R.layout.delete_window, null)
        val builder = AlertDialog.Builder(viewHolder.itemView.context)
            .setView(dialogView)
        val alertDialog = builder.show()
        dialogView.findViewById<TextView>(R.id.item_title_tv).text = allergen.title
        dialogView.findViewById<TextView>(R.id.question_tv).text = "Vai izdz??st ??o alerg??nu?"
        dialogView.findViewById<Button>(R.id.close_button).setOnClickListener {
            alertDialog.dismiss()
        }
        dialogView.findViewById<Button>(R.id.not_delete_button).text = "Tom??r saglab??t alerg??nu"
        dialogView.findViewById<Button>(R.id.not_delete_button).setOnClickListener {
            alertDialog.dismiss()
        }
        dialogView.findViewById<Button>(R.id.delete_button).text = "Dz??st alerg??nu"
        dialogView.findViewById<Button>(R.id.delete_button).setOnClickListener {
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
                                alertDialog.dismiss()
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
    }
    private fun editAllergen(allergen: AllergenDataClass, viewHolder: RecyclerView.ViewHolder, position: Int) {
        val dialogView = LayoutInflater.from(viewHolder.itemView.context).inflate(R.layout.add_allergen_window, null)
        val builder = AlertDialog.Builder(viewHolder.itemView.context)
            .setView(dialogView)
        val alertDialog = builder.show()
        dialogView.findViewById<Button>(R.id.close_button).setOnClickListener {
            alertDialog.dismiss()
        }
        dialogView.findViewById<EditText>(R.id.allergen_title_et).setText(allergen.title)
        dialogView.findViewById<Button>(R.id.add_allergen_button).text = "Saglab??t izmai??as"
        dialogView.findViewById<Button>(R.id.add_allergen_button).setOnClickListener {
            val title = dialogView.findViewById<EditText>(R.id.allergen_title_et).text.toString()
            val editBy = "ConfectionerSecondId" // Get current user id or etc
            val editedBy = allergen.editedBy
            val editedOn = allergen.editedOn
            val dateAndTimeNow = LocalDateTime.now()
            val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
            val dateAndTimeToSave = dateAndTimeNow.format(dateFormat).toString()
            editedBy.add(editBy)
            editedOn.add(dateAndTimeToSave)
            val editedAllergen = AllergenDataClass(allergen.id, title, allergen.madeBy, editedBy, editedOn)
            if (NetworkDataViewModel().checkConnection(viewHolder.itemView.context) == true) {
                AllergenDataViewModel().updateOneAllergen(editedAllergen) { edited ->
                    if (edited == true) {
                        dataSet.set(position, editedAllergen)
                        notifyItemChanged(position)
                        alertDialog.dismiss()
                    } else {

                    }
                }
            }
        }
    }
}
