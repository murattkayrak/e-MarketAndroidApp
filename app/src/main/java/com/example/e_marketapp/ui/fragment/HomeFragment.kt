package com.example.e_marketapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.e_marketapp.R
import com.example.e_marketapp.data.model.Product
import com.example.e_marketapp.databinding.FragmentHomeBinding
import com.example.e_marketapp.ui.activity.MainActivity
import com.example.e_marketapp.ui.adapter.ProductAdapter
import com.example.e_marketapp.ui.viewmodel.HomeViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var binding: FragmentHomeBinding? = null
    private val homeViewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.viewModel = homeViewModel
        binding?.lifecycleOwner = viewLifecycleOwner
        homeViewModel.getProductFromService()

        binding?.recyclerView?.layoutManager = GridLayoutManager(context, 2)

        viewLifecycleOwner.lifecycleScope.launch {
            homeViewModel.homeUIState.collectLatest { uiState ->
                binding?.recyclerView?.adapter = ProductAdapter(
                    productList = uiState.productList,
                    productOnClick = ::navigateToProductFragment,
                    addToCartOnClick = ::addToCartOnClick,
                )
            }
        }

        binding?.searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    homeViewModel.filterProducts(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (!newText.isNullOrEmpty()) {
                    homeViewModel.filterProducts(newText)
                }
                return true
            }
        })

        binding?.selectFilterButton?.setOnClickListener {
            val filterDialog = FilterDialogFragment()
            filterDialog.show(parentFragmentManager, "FilterDialog")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun navigateToProductFragment(product: Product) {
        val bundle = Bundle()
        bundle.putParcelable("product", product)
        findNavController().navigate(R.id.action_homeFragment_to_productFragment, bundle)
    }

    private fun addToCartOnClick(product: Product) {
        (activity as MainActivity).addToCart(product = product, onClick = {})
    }
}