package com.example.romabakery.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.romabakery.databinding.OrderConfectioneryItemBinding
import com.example.romabakery.model.ConfectioneryItem

class ChooseItemsAdapter :
    ListAdapter<ConfectioneryItem, ChooseItemsAdapter.ChooseItemsViewHolder>(ChooseItemsAdapter) {
    class ChooseItemsViewHolder(
        private var binding: OrderConfectioneryItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ConfectioneryItem) {
            binding.confectioneryItem = item
            binding.executePendingBindings()
        }
    }


    companion object DiffCallback : DiffUtil.ItemCallback<ConfectioneryItem>() {
        override fun areItemsTheSame(
            oldItem: ConfectioneryItem,
            newItem: ConfectioneryItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ConfectioneryItem,
            newItem: ConfectioneryItem
        ): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ChooseItemsViewHolder {
        return ChooseItemsViewHolder(
            OrderConfectioneryItemBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: ChooseItemsViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener {
//            openImageWindow(catPhoto, holder.itemView.context)
        }
    }
}