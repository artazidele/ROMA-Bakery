package com.example.romabakery.view.allergens

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.romabakery.R
import com.example.romabakery.databinding.AllAllergenListRowBinding
import com.example.romabakery.model.Allergen
import com.example.romabakery.model.ConfectioneryItem
import com.example.romabakery.viewmodel.AllergenViewModel
import com.example.romabakery.viewmodel.NetworkViewModel
import kotlinx.coroutines.*

class AllergenAdapter(private val data: ArrayList<Allergen>) : ListAdapter<Allergen, AllergenAdapter.AllergenViewHolder>(DiffCallback) {

//class AllergenAdapter(private val data: ArrayList<Allergen>) : ListAdapter<Allergen, AllergenAdapter.AllergenViewHolder>(AllergenAdapter) {
    class AllergenViewHolder(
        private var binding: AllAllergenListRowBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Allergen) {
            binding.allergen = item
            binding.executePendingBindings()
        }
//        fun remove(item: Allergen) {
//
//        }
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
            AllAllergenListRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

//    override fun onCurrentListChanged(
//        previousList: MutableList<Allergen>,
//        currentList: MutableList<Allergen>
//    ) {
//        super.onCurrentListChanged(previousList, currentList)
//    }

//    public fun removeData(position: Int) {
//        data.removeAt(position)
//        notifyItemRemoved(position)
//        notifyItemRangeChanged(position, itemCount)
//    }

    private fun openDelete(position: Int, item: Allergen) {
        AllergenViewModel().getAllergenItems(item.id) {
            if (it?.isEmpty() == true) {//if (it!!.equals(ArrayList<ConfectioneryItem>())) {//if (it?.count() == 0) {
                Log.d(TAG, "Length = 0")
                AllergenViewModel().isDeleted(item.id) {
                    if (it.equals("SUCCESS")) {
                        Log.d(TAG, "PIRMS: " + getDataItemCount().toString())
                        Log.d(TAG, "TRUE")
                        data.removeAt(position)
                        Log.d(TAG, "PĒC: " + getDataItemCount().toString())
//                        itemCount = getDataItemCount()
                        notifyItemRemoved(position)
//                        notifyDataSetChanged()
                        Log.d(TAG, "PĒC: " + getItemCount().toString())

//                        notifyDataSetChanged()
//                        notifyItemRemoved(position)
//                        notifyItemRangeChanged(position, getDataItemCount()-1)
//                        notifyItemRangeRemoved(position, 1)

                    } else {
                        Log.d(TAG, "FALSE")
                    }

                }
//                AllergenViewModel().deleteAllergen(item.id) { its ->
//                    if (its?.equals("SUCCESS") == true) {
//                        Log.d(TAG, "SUCCESS")
//                        notifyItemRemoved(position)
//                    } else {
//                        Log.d(TAG, "Null")
//                    }
//                }
            } else if (it?.isNotEmpty() == true) {
                // Cannot delete
                Log.d(TAG, "NOT empty")
            } else {
                Log.d(TAG, "NULL")
            }
        }
    }

    private fun getDataItemCount(): Int = data.size

    override fun getItemCount(): Int {
        return data.size
//        return super.getItemCount()
    }

    override fun onBindViewHolder(holder: AllergenViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)

        holder.itemView.findViewById<Button>(R.id.delete_allergen_button).setOnClickListener {

            openDelete(position, item)
//            AllergenViewModel().getAllergenItems(item.id) {
//                if (it?.count() == 0) {
//                    AllergenViewModel().deleteAllergen(item.id) {
//                        if (it?.length == 0) {
//                            Log.d(TAG, "Length = 0")
//                        } else {
//                            Log.d(TAG, "Notify")
//                            notifyItemRemoved(position)
//                        }
//                    }
//                } else if (it?.isNotEmpty() == true) {
//                    Log.d(TAG, "NOT empty")
//                } else {
//                    Log.d(TAG, "Else")
//                }
//            }




//            Log.d(TAG, "Press delete 1")
////            AllergenViewModel().callMakeItemList(item.id)
//            CoroutineScope(Job() + Dispatchers.Main).launch{
//                async {
//                    AllergenViewModel().makeItemListWithAllergen(item.id)
//                }.await()
//                Log.d(TAG, "Press delete 5")
//                if (AllergenViewModel().returnItemListWithAllergen() == ArrayList<ConfectioneryItem>()) {
//                    Log.d(TAG, "EMPTY")
//                } else {
//                    Log.d(TAG, "NOT EMPTY")
//                }
//            }
//            Log.d(TAG, "Press delete 5")
//            if (AllergenViewModel().returnItemListWithAllergen() == ArrayList<ConfectioneryItem>()) {
//                Log.d(TAG, "EMPTY")
//            } else {
//                Log.d(TAG, "NOT EMPTY")
//            }




//            if (deleteAllergen(item.id, holder) == true) {
//                // Pārjautā vai dzēst
//                AllergenViewModel().completeDeleteAllergen(item.id, position)
//                    data.removeAt(position)
//                    notifyItemRemoved(position)
//
//
////                notifyItemRangeChanged(position, itemCount)
////                    notifyItemRemoved(position)
////                    Log.d(TAG, "NOTIFY")
//
//
//            }

        }
        holder.itemView.findViewById<Button>(R.id.edit_allergen_button).setOnClickListener {
            updateAllergen(item, holder)
        }
    }


    public fun hideDeletedRow(holder: RecyclerView.ViewHolder) {
        holder.itemView.findViewById<TextView>(R.id.deleted_allergen_title).visibility = View.VISIBLE
        holder.itemView.findViewById<TextView>(R.id.allergen_title).visibility = View.INVISIBLE
        holder.itemView.findViewById<Button>(R.id.delete_allergen_button).visibility = View.GONE
        holder.itemView.findViewById<Button>(R.id.edit_allergen_button).visibility = View.GONE
    }

    public fun updateAllergen(allergen: Allergen, holder: RecyclerView.ViewHolder) {
        val updatedAllergen =
            Allergen(allergen.id, "EditedTitle", allergen.madeBy, allergen.editedBy, allergen.editedOn)
        if (NetworkViewModel().checkConnection(holder.itemView.context) == true) {
            AllergenViewModel().updateAllergen(updatedAllergen, holder)
            Log.d(TAG, "EDIT ALLERGEN: " + allergen.title)
        }
    }

    public fun changeAllergenTitle(holder: RecyclerView.ViewHolder, newTitle: String) {
        holder.itemView.findViewById<TextView>(R.id.allergen_title).text = newTitle
    }
}
