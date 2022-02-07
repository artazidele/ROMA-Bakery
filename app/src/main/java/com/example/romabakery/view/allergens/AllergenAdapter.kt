package com.example.romabakery.view.allergens

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.romabakery.R
import com.example.romabakery.databinding.AllAllergenListRowBinding
import com.example.romabakery.model.Allergen
import com.example.romabakery.model.ConfectioneryItem
import com.example.romabakery.viewmodel.AllergenViewModel

class AllergenAdapter : ListAdapter<Allergen, AllergenAdapter.AllergenViewHolder>(AllergenAdapter) {
    class AllergenViewHolder(
        private var binding: AllAllergenListRowBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Allergen) {
            binding.allergen = item
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Allergen>() {
        override fun areItemsTheSame(
            oldItem: Allergen,
            newItem: Allergen
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Allergen,
            newItem: Allergen
        ): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AllergenViewHolder {
        return AllergenViewHolder(
            AllAllergenListRowBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: AllergenViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.findViewById<Button>(R.id.delete_allergen_button).setOnClickListener {
            deleteAllergen(item.id, holder)
        }
        holder.itemView.findViewById<Button>(R.id.edit_allergen_button).setOnClickListener {
            updateAllergen(item)
        }
    }


    public fun deleteAllergen(id: String, holder: RecyclerView.ViewHolder) {
        AllergenViewModel().deleteAllergen(id)
        Log.d(TAG, "DELETE ALLERGEN: " + id)
        if (AllergenViewModel().deleteAllergen(id) == ArrayList<ConfectioneryItem>()) {
            Log.d(TAG, "CAN BE DELETED")
            AllergenViewModel().completeDeleteAllergen(id, holder)
//            holder.itemView.visibility = View.GONE
//            Log.d(TAG, "CAN BE DELETED CAN BE DELETED")
        } else {
//            AllergenViewModel().completeDeleteAllergen(id, holder)
            Log.d(TAG, "CANNOT BE DELETED")
        }
    }

    public fun hideDeletedRow(holder: RecyclerView.ViewHolder) {
        holder.itemView.visibility = View.GONE
        Log.d(TAG, "CAN BE DELETED CAN BE DELETED")
    }

    public fun updateAllergen(allergen: Allergen) {
        val updatedAllergen =
            Allergen(allergen.id, "newTitle", allergen.madeBy, allergen.editedBy, allergen.editedOn)
        AllergenViewModel().updateAllergen(updatedAllergen)
        Log.d(TAG, "EDIT ALLERGEN: " + allergen.title)
    }
}