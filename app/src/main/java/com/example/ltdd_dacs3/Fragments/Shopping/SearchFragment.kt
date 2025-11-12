package com.example.ltdd_dacs3.Fragments.Shopping

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ltdd_dacs3.Adapters.SearchAdapter
import com.example.ltdd_dacs3.R
import com.example.ltdd_dacs3.ViewModel.SearchViewModel
import com.example.ltdd_dacs3.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {

    private lateinit var binding: FragmentSearchBinding
    private val searchViewModel: SearchViewModel by viewModels()
    private lateinit var searchAdapter: SearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    if (it.isNotEmpty()) {
                        searchViewModel.searchProducts(it.trim())
                    } else {
                        searchAdapter.differ.submitList(emptyList())
                    }
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    if (it.isNotEmpty()) {
                        searchViewModel.searchProducts(it.trim())
                    } else {
                        searchAdapter.differ.submitList(emptyList())
                    }
                }
                return false
            }
        })

        searchViewModel.products.observe(viewLifecycleOwner, Observer { products ->
            searchAdapter.differ.submitList(products)
        })
    }

    private fun setupRecyclerView() {
        searchAdapter = SearchAdapter()
        binding.rvSearch.apply {
            layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
            adapter = searchAdapter
        }
    }
}
