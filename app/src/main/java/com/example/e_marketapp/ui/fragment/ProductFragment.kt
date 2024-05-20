package com.example.e_marketapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.e_marketapp.R
import com.example.e_marketapp.data.model.Product
import com.example.e_marketapp.databinding.FragmentHomeBinding
import com.example.e_marketapp.databinding.FragmentProductBinding
import com.example.e_marketapp.ui.activity.MainActivity
import com.example.e_marketapp.ui.viewmodel.HomeViewModel
import com.example.e_marketapp.ui.viewmodel.ProductViewModel

class ProductFragment : Fragment() {

    private var binding: FragmentProductBinding? = null
    private val productViewModel: ProductViewModel by activityViewModels()
    var product: Product? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            product = arguments?.getParcelable<Product>("product")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        product?.let {
            productViewModel.setProduct(it)
            Glide.with(requireContext())
                .load(it.image)
                .skipMemoryCache(true)
                .centerCrop()
                .into(binding!!.productImage)
        }
        binding?.viewModel = productViewModel
        binding?.lifecycleOwner = viewLifecycleOwner
        binding?.addToCart?.setOnClickListener { addToCartOnClick() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun addToCartOnClick() {
        product?.let { (activity as MainActivity).addToCart(product = it, onClick = {}) }
    }
}