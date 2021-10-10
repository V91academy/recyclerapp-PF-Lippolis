package com.v1bottoni.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.v1bottoni.myapplication.adapters.CocktailAdapter
import com.v1bottoni.myapplication.databinding.FragmentCocktailListBinding
import com.v1bottoni.myapplication.model.builders.CocktailListBuilder

// the fragment initialization parameters
private const val ARG_BUILDER = "builder"

/**
 * A simple [Fragment] subclass.
 * Use the [CocktailListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CocktailListFragment private constructor(): Fragment() {
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
        binding.cocktailsList.adapter = CocktailAdapter(drinkList)
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
