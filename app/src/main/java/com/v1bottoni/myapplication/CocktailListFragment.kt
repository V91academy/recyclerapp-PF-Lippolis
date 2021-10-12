package com.v1bottoni.myapplication

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.v1bottoni.myapplication.adapters.CocktailAdapter
import com.v1bottoni.myapplication.adapters.HeaderAdapter
import com.v1bottoni.myapplication.databinding.FragmentCocktailListBinding
import com.v1bottoni.myapplication.model.builders.CocktailListBuilder
import java.io.IOException

// the fragment initialization parameters
private const val ARG_BUILDER = "builder"

/**
 * A simple [Fragment] subclass.
 * Use the [CocktailListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CocktailListFragment: Fragment() {
    private var builder: CocktailListBuilder? = null
    lateinit var binding: FragmentCocktailListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            builder = it.getParcelable(ARG_BUILDER)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding  = FragmentCocktailListBinding.inflate(inflater, container, false)
        //TODO(Put placeholder in case the list doesn't load)
        val drinkList = builder!!.build()
        val onItemClick: ((String) -> Unit) = {
                cocktail: String ->
            try{
                Log.d("RecyclerView onClickListener", "activity: $activity")
                Log.d("RecyclerView onClickListener", "filesDir = ${activity?.filesDir}")
                activity?.openFileOutput(getString(R.string.cocktails_file), Context.MODE_PRIVATE).use {
                    Log.d("RecyclerView onClickListener", "os: ${it.toString()}")
                    it?.write(cocktail.toByteArray())
                }
                Toast.makeText(activity,"Cocktail saved",Toast.LENGTH_LONG).show()

            } catch (e: IOException) {
                Toast.makeText(activity, "Sorry, we couldn't save your cocktail", Toast.LENGTH_LONG).show()
            }

        }
        binding.cocktailsList.adapter = HeaderAdapter("Drinks", CocktailAdapter(drinkList, onItemClick))
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param builder The builder which will be used to populate the RecyclerView of the fragment
         *  It requires a builder and not the list directly to allow refreshing in case the view is
         * @return A new instance of fragment CocktaiLListFragment.
         */
        @JvmStatic
        fun newInstance(builder: CocktailListBuilder) =
            CocktailListFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_BUILDER, builder)
                }
            }
    }
}
