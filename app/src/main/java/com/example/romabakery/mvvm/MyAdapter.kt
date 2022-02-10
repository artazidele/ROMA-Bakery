package com.example.romabakery.mvvm

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.romabakery.R
import com.example.romabakery.model.Allergen
import com.example.romabakery.viewmodel.AllergenViewModel
import com.example.romabakery.viewmodel.NetworkViewModel


class MyAdapter(private val dataSet: ArrayList<Allergen>) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView
        val deleteButton: Button
        val editButton: Button

        init {
            textView = view.findViewById(R.id.allergen_title)
            deleteButton = view.findViewById(R.id.delete_allergen_button)
            editButton = view.findViewById(R.id.edit_allergen_button)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.my_row, viewGroup, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: MyViewHolder, position: Int) {
        viewHolder.textView.text = dataSet[position].title
        viewHolder.deleteButton.setOnClickListener {
            Log.d(TAG, "Delete button pressed")
            if (NetworkViewModel().checkConnection(viewHolder.itemView.context) == true) {
                MyViewModel().getAllergenItems(dataSet[position].id) { itemList ->
                    if (itemList?.isEmpty() == true) {
                        Log.d(TAG, "CAN DELETE")
                        MyViewModel().deleteOneAllergen(dataSet[position].id) { isDeleted ->
                            if (isDeleted == true) {
                                Log.d(TAG, "TRUE")
                                Log.d(TAG, "Position: " + position.toString())
                                dataSet.removeAt(position)
                                notifyDataSetChanged()
                                Log.d(TAG, "Count: " + itemCount.toString())
                            } else {
                                Log.d(TAG, "FALSE")
                            }
                        }
                    } else if (itemList?.isNotEmpty() == true) {
                        Log.d(TAG, "CANNOT DELETE")
                    } else {
                        Log.d(TAG, "NULL")
                    }
                }
            }
        }
        viewHolder.editButton.setOnClickListener {
            Log.d(TAG, "Edit button pressed")
            if (NetworkViewModel().checkConnection(viewHolder.itemView.context) == true) {

                MyViewModel().updateOneAllergen(dataSet[position]) { isEdited ->
                    if (isEdited == true) {
                        Log.d(TAG, "isEdited TRUE")
                    } else {
                        Log.d(TAG, "isEdited FALSE")
                    }
                }

            }
//            dataSet[position].title// = "EDITED"
            notifyItemChanged(position)
        }

    }

    override fun getItemCount() = dataSet.size

}