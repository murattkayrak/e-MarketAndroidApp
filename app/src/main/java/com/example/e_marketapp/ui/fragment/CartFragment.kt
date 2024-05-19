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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CartFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CartFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var binding: FragmentCartBinding? = null
    private val cartViewModel: CartViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CartFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CartFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}