package com.example.e_marketapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.e_marketapp.data.model.Product
import com.example.e_marketapp.databinding.ItemProductCartBinding

class CartAdapter(
    private val productList: List<Product>,
//    val productOnClick: (product: Product) -> Unit,
//    val addToCartOnClick: (product: Product) -> Unit,
) : RecyclerView.Adapter<CartAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(private val binding: ItemProductCartBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
//            binding.productLayout.setOnClickListener { productOnClick.invoke(product) }
            binding.product = product
//            binding.addToCart.setOnClickListener { addToCartOnClick.invoke(product) }
//            Glide.with(binding.productImage.context)
//                .load(product.image)
//                .skipMemoryCache(true)
//                .into(binding.productImage)
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemProductCartBinding.inflate(layoutInflater, parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(productList[position])
    }

    override fun getItemCount(): Int = productList.size
}