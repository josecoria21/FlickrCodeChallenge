package dev.propoc.flickrcodechallenge.view.searchpage

import ImageAdapter
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import dev.propoc.flickrcodechallenge.databinding.FragmentSearchBinding
import dev.propoc.flickrcodechallenge.viewmodel.SearchViewModel
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {
    private val viewModel: SearchViewModel by viewModels()
    private lateinit var binding: FragmentSearchBinding
    private lateinit var adapter: ImageAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(layoutInflater, container, false).apply {
            this.viewModel = this@SearchFragment.viewModel
            lifecycleOwner = viewLifecycleOwner
        }

        val navController = findNavController()

        adapter = ImageAdapter(navController)


        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.fetchData(query.orEmpty())
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.fetchData(newText.orEmpty())
                return true
            }
        })

        binding.imageRv.adapter = adapter
        viewModel.fetchData("porcupine")
        lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.dataList.collect { list ->
                    adapter.setData(list)
                }
            }
        }


        return binding.root
    }
}