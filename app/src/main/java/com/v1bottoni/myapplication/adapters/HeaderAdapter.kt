package com.v1bottoni.myapplication.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.v1bottoni.myapplication.databinding.ListHeaderBinding

class HeaderAdapter<R: RecyclerView.ViewHolder, T: RecyclerView.Adapter<R>>
    (val title: String, val adapter: T):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class HeaderViewHolder(binding: ListHeaderBinding): RecyclerView.ViewHolder(binding.root) {
        val title: TextView
        init{
            title = binding.listHeader
        }
    }

    override fun getItemViewType(position: Int): Int {
        /* If it's the first item, it must be of header type
        *  If it's a subsequent item, its viewType depends on the type the adapter would give in its
        *  previous position (e.g. the second item is the first item in the list without header.
        *  We add 1 to the type returned to keep 0 reserved for the header type.
        */
        return if (position == 0) 0 else adapter.getItemViewType(position - 1) + 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            0 -> HeaderViewHolder(
                ListHeaderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false))
            else -> adapter.onCreateViewHolder(parent, viewType-1)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(position == 0) {
            (holder as HeaderAdapter<*,*>.HeaderViewHolder).title.text = title
        } else {
            adapter.onBindViewHolder(holder as R, position-1)
        }
    }

    override fun getItemCount(): Int {
        return adapter.itemCount + 1
    }
}