package com.v1bottoni.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.v1bottoni.myapplication.databinding.ActivityMainBinding
import com.v1bottoni.myapplication.model.builders.CocktailDBListBuilder
import java.io.BufferedReader
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val json = assets.open("cocktails.json").bufferedReader().use { it.readText() }
        val builder = CocktailDBListBuilder(CocktailDBListBuilder.SearchType.LOCAL_JSON, json)
        supportFragmentManager.beginTransaction().apply {
            replace(binding.mainFragment.id, CocktailListFragment.newInstance(builder))
            commit()
        }
    }
}