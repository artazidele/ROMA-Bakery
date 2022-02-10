package com.example.romabakery.mvvm

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.romabakery.R
import com.example.romabakery.model.Allergen

class MySecondAdapter : ListAdapter<Allergen, MySecondAdapter.MySecondViewHolder>(AllergensComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MySecondViewHolder {
        return MySecondViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: MySecondViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.title)
    }

    class MySecondViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val AllergenItemView: TextView = itemView.findViewById(R.id.allergen_title)

        fun bind(text: String?) {
            AllergenItemView.text = text
        }

        companion object {
            fun create(parent: ViewGroup): MySecondViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.my_row, parent, false)
                return MySecondViewHolder(view)
            }
        }
    }

    class AllergensComparator : DiffUtil.ItemCallback<Allergen>() {
        override fun areItemsTheSame(oldItem: Allergen, newItem: Allergen): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Allergen, newItem: Allergen): Boolean {
            return oldItem.id == newItem.id
        }
    }
}