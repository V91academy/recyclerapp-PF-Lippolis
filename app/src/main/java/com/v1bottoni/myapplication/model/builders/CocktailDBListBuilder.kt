package com.v1bottoni.myapplication.model.builders

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi
import com.v1bottoni.myapplication.CocktailListFragment
import com.v1bottoni.myapplication.model.Cocktail
import com.v1bottoni.myapplication.model.Ingredient
import org.json.JSONObject
import java.lang.IllegalStateException
import java.nio.file.Paths

/**
 * Class responsible for creating a List<Cocktail> using data from the site www.thecocktaildb.com
 */
class CocktailDBListBuilder(
    val searchType: SearchType? = null,
    val searchString: String? = null
) : CocktailListBuilder {

    enum class SearchType(val url: String): Parcelable {

        LOCAL_JSON("");

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeInt(ordinal)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<SearchType> {
            override fun createFromParcel(parcel: Parcel): SearchType {
                return values()[parcel.readInt()]
            }

            override fun newArray(size: Int): Array<SearchType?> {
                return arrayOfNulls(size)
            }
        }
    }

    override fun build(): List<Cocktail> {
        return when(searchType) {
            SearchType.LOCAL_JSON -> searchString?.let {buildFromJSONString(it)} ?: throw IllegalStateException()
            null -> throw IllegalStateException("CocktailDBListBuilder needs a searchType before it can build")
        }
    }

    private fun buildFromJSONString(jsonString: String): List<Cocktail> {
        val cocktails = JSONObject(jsonString).getJSONArray("drinks")
        var cocktailsList = mutableListOf<Cocktail>()
        for(i in 0 until cocktails.length()){
            val cocktail = cocktails.getJSONObject(i)
            val name = cocktail.getString("strDrink") ?: ""
            val recipe = cocktail.getString("strInstructions") ?:""
            val ingredients = mutableListOf<Ingredient>()
            var j = 1
            var ingredientName: String? = null
            ingredientName = cocktail.getString("strIngredient$j")
            while(!ingredientName.isNullOrEmpty()) {
                val ingredientQuantity = cocktail.getString("strMeasure$j") ?: ""
                ingredients.add(Ingredient(ingredientName, ingredientQuantity))
                j++
                ingredientName = cocktail.getString("strIngredient$j")
            }
            val image = cocktail.getString("strDrinkThumb")
            cocktailsList.add(Cocktail(name, recipe, ingredients, image))
        }
        return cocktailsList
    }

    constructor(parcel: Parcel) : this(
        parcel.readParcelable(SearchType::class.java.classLoader),
        parcel.readString()
    )



    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(searchType, flags)
        parcel.writeString(searchString)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CocktailDBListBuilder> {
        override fun createFromParcel(parcel: Parcel): CocktailDBListBuilder {
            return CocktailDBListBuilder(parcel)
        }

        override fun newArray(size: Int): Array<CocktailDBListBuilder?> {
            return arrayOfNulls(size)
        }
    }
}
