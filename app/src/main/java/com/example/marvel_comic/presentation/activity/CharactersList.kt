package com.example.marvel_comic.presentation.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marvel_comic.data.dao.model.MarvelCharacter
import com.example.marvel_comic.databinding.ActivityCharactersListBinding
import com.example.marvel_comic.presentation.adapter.MarvelCharacterGridViewAdapter
import com.example.marvel_comic.presentation.view.OnMarvelCharacterFilterChangedListener
import com.example.marvel_comic.presentation.viewmodel.MarvelCharacterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch
import java.util.regex.Pattern
import com.example.marvel_comic.data.network.model.*

@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class CharactersList : AppCompatActivity() {

    private lateinit var binding: ActivityCharactersListBinding
    lateinit var adapter: MarvelCharacterGridViewAdapter
    lateinit var layoutManager: GridLayoutManager
    private var offset = 0

    val viewModel by viewModels<MarvelCharacterViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCharactersListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        layoutManager = GridLayoutManager(this, 1, RecyclerView.VERTICAL, false)
        adapter = MarvelCharacterGridViewAdapter()
        binding.rvPosts.layoutManager = layoutManager
        binding.filterView.setFilterChangedListener(filterChangedListener)

        lifecycleScope.launch(Dispatchers.Main) {
            launch {
                viewModel.state.collect {
                    adapter.submitList(it)
                }
            }
            viewModel.fetchMarvelCharactersList(offset)
        }
        setPostGridAdapter()
        binding.swipeRefreshLayout.apply {
            setOnRefreshListener {
                setRefreshing(false)
                offset = offset+200
                // User defined method to shuffle the array list items
                lifecycleScope.launch(Dispatchers.Main) {
                    viewModel.fetchMarvelCharactersList(offset)
                }
            }
        }
    }

    private fun setPostGridAdapter() {
        adapter.onItemClick = {
//            val transaction = this.supportFragmentManager.beginTransaction()
//            transaction.add(containerId, MarvelCharacterDetailFragment::class.java, null, null)
//                .addToBackStack(null).commit()
//            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        }
            binding.rvPosts.adapter = adapter

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        binding.filterView.editTextView.setText("" as CharSequence)
    }

    val filterChangedListener = object : OnMarvelCharacterFilterChangedListener {
        override fun onMarvelCharacterFilterChanged(userChoice: CharSequence) {
            val filteredList = viewModel.state.value.filter { character -> isAllowed(character, userChoice) }
            adapter.submitList(filteredList)
        }
    }

    fun isAllowed(comicCharacter: MarvelCharacter, userFilterChoice: CharSequence): Boolean {
        if (userFilterChoice.isBlank()) return true
        val namePattern = Pattern.compile(".*" + userFilterChoice + ".*", Pattern.CASE_INSENSITIVE)
        return namePattern.matcher(comicCharacter.name)
            .find() || namePattern.matcher(comicCharacter.description).find()
    }
}