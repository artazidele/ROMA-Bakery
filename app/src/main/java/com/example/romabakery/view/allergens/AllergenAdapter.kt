package com.example.romabakery.view.allergens

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.romabakery.R
import com.example.romabakery.databinding.AllAllergenListRowBinding
import com.example.romabakery.model.Allergen

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
            deleteAllergen(item.id)
        }
        holder.itemView.findViewById<Button>(R.id.edit_allergen_button).setOnClickListener {
            updateAllergen(item)
        }
    }


    public fun deleteAllergen(id: String) {
        Log.d(TAG, "DELETE ALLERGEN: " + id)
    }

    public fun updateAllergen(allergen: Allergen) {
        Log.d(TAG, "EDIT ALLERGEN: " + allergen.title)
    }
}