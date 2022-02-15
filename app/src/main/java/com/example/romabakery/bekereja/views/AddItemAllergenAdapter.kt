package com.example.romabakery.bekereja.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.example.romabakery.R
import com.example.romabakery.bekereja.models.allergens.AllergenDataClass

class AddItemAllergenAdapter(private val dataSet: ArrayList<AllergenDataClass>) :
    RecyclerView.Adapter<AddItemAllergenAdapter.AddItemAllergenViewHolder>() {

    class AddItemAllergenViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val checkBox: CheckBox

        init {
            checkBox = view.findViewById(R.id.allergen_title)
        }
    }

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): AddItemAllergenViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.add_item_allergen_row, viewGroup, false)
        return AddItemAllergenViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: AddItemAllergenViewHolder, position: Int) {
        viewHolder.checkBox.text = dataSet[position].title
        viewHolder.checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                AddItemActivity().addAllergen(dataSet[position].id)
            } else {
                AddItemActivity().removeAllergen(dataSet[position].id)
            }
        }
    }

    override fun getItemCount() = dataSet.size
}
