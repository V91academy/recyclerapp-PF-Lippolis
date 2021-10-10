package com.v1bottoni.myapplication.model.builders

import android.os.Parcelable
import com.v1bottoni.myapplication.model.Cocktail

interface CocktailListBuilder: Parcelable {

    fun build(): List<Cocktail>

}