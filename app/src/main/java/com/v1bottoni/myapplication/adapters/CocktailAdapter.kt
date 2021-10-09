package com.v1bottoni.myapplication.adapters

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.v1bottoni.myapplication.R
import com.v1bottoni.myapplication.databinding.BigCardviewBinding
import com.v1bottoni.myapplication.databinding.SmallCardviewBinding
import com.v1bottoni.myapplication.model.Cocktail

class CocktailAdapter(val list: List<Cocktail>): RecyclerView.Adapter<CocktailAdapter.CocktailViewHolder>() {

    abstract inner class CocktailViewHolder(view: View): RecyclerView.ViewHolder(view) {
        abstract val name: TextView
        abstract val recipe: TextView
        abstract val ingredients: RecyclerView
        abstract val image: ImageView
    }

    inner class CocktailBigViewHolder(binding: BigCardviewBinding): CocktailViewHolder(binding.root) {
        override val name: TextView
        override val recipe: TextView
        override val ingredients: RecyclerView
        override val image: ImageView
        init {
            name = binding.cocktailName
            recipe = binding.cocktailRecipe
            ingredients = binding.ingredientsList
            image = binding.cocktailPhoto
        }
    }

    inner class CocktailSmallViewHolder(binding: SmallCardviewBinding): CocktailViewHolder(binding.root) {
        override val name: TextView
        override val recipe: TextView
        override val ingredients: RecyclerView
        override val image: ImageView
        init {
            name = binding.cocktailName
            recipe = binding.cocktailRecipe
            ingredients = binding.ingredientsList
            image = binding.cocktailPhoto
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position % 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CocktailViewHolder {
        return when(viewType) {
            0 -> {
                CocktailBigViewHolder(
                    BigCardviewBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false))
            }
            else -> {
                CocktailSmallViewHolder(
                    SmallCardviewBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false))
            }
        }
    }
    override fun onBindViewHolder(holder: CocktailViewHolder, position: Int) {
        val cocktail = list[position]
        holder.name.text = cocktail.name
        holder.recipe.text = cocktail.recipe
        holder.ingredients.adapter = IngredientAdapter(cocktail.ingredients)

        val circularProgressDrawable = CircularProgressDrawable(this)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()
        Glide.with(holder.image.context)
            .load(cocktail.image)
            .placeholder(circularProgressDrawable).into(holder.image)
    }

    override fun getItemCount(): Int {
        return list.size
    }


}