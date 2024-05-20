package com.example.e_marketapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e_marketapp.R
import com.example.e_marketapp.data.model.Product
import com.example.e_marketapp.databinding.FragmentCartBinding
import com.example.e_marketapp.databinding.FragmentHomeBinding
import com.example.e_marketapp.ui.activity.MainActivity
import com.example.e_marketapp.ui.adapter.CartAdapter
import com.example.e_marketapp.ui.adapter.ProductAdapter
import com.example.e_marketapp.ui.viewmodel.CartViewModel
import com.example.e_marketapp.ui.viewmodel.HomeViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CartFragment : Fragment() {

    private var binding: FragmentCartBinding? = null
    private val cartViewModel: CartViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.viewModel = cartViewModel
        binding?.lifecycleOwner = viewLifecycleOwner
        cartViewModel.getCartProducts(activity = activity as MainActivity)
        binding?.recyclerView?.layoutManager = LinearLayoutManager(context)

        viewLifecycleOwner.lifecycleScope.launch {
            cartViewModel.cartUIState.collectLatest { uiState ->
                binding?.recyclerView?.adapter = CartAdapter(
                    productList = uiState.productList,
                    addToCartOnClick = ::addToCartOnClick,
                    removeFromCartOnClick = ::removeFromCartOnClick,
                )
            }
        }
    }

    private fun addToCartOnClick(product: Product) {
        (activity as MainActivity).addToCart(
            product = product,
            onClick = {
                cartViewModel.getCartProducts(activity = activity as MainActivity)
            },
        )
    }

    private fun removeFromCartOnClick(product: Product) {
        (activity as MainActivity).removeFromCart(
            product = product,
            onClick = {
                cartViewModel.getCartProducts(activity = activity as MainActivity)
            },)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}