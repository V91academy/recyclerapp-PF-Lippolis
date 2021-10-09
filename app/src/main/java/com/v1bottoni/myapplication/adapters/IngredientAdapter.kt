package com.v1bottoni.myapplication.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.v1bottoni.myapplication.R
import com.v1bottoni.myapplication.model.Ingredient

class IngredientAdapter(val list: List<Ingredient>):
    RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder>() {

    inner class IngredientViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val name: TextView
        val quantity: TextView
        init {
            name = view.findViewById(R.id.ingredient_name)
            quantity = view.findViewById(R.id.ingredient_qty)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.ingredient, parent, false)
        return IngredientViewHolder(view)
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        val ingredient = list[position]
        holder.name.text = ingredient.name
        holder.quantity.text = ingredient.quantity
    }

    override fun getItemCount(): Int {
        return list.size
    }
}