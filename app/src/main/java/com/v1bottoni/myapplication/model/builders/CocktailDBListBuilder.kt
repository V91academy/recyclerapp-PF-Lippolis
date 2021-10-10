package com.v1bottoni.myapplication.model.builders

import android.os.Parcel
import android.os.Parcelable
import com.v1bottoni.myapplication.model.Cocktail
import java.lang.IllegalStateException

/**
 * Class responsible for creating a List<Cocktail> using data from the site www.thecocktaildb.com
 */
class CocktailDBListBuilder(
    val searchType: SearchType? = null,
    val searchString: String? = null,
    val resourceID: Int? = null
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
            SearchType.LOCAL_JSON -> listOf()
            null -> throw IllegalStateException("CocktailDBListBuilder needs a searchType before it can build")
        }
    }

    constructor(parcel: Parcel) : this(
        parcel.readParcelable(SearchType::class.java.classLoader),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int
    )



    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(searchType, flags)
        parcel.writeString(searchString)
        parcel.writeValue(resourceID)
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