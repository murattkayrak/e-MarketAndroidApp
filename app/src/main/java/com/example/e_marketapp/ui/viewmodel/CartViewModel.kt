package com.example.e_marketapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_marketapp.ui.activity.MainActivity
import com.example.e_marketapp.ui.state.CartUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CartViewModel : ViewModel() {

    private val _cartUIState = MutableStateFlow(CartUIState())
    val cartUIState: StateFlow<CartUIState> = _cartUIState.asStateFlow()

    fun getCartProducts(activity: MainActivity) {
        viewModelScope.launch {
            val products = activity.getAllProducts()
            var total = 0.0F
            for (product in products) {
                total += (((product.price?.toFloat() ?: 0.0F) * (product.quantity ?: 0.0F).toFloat()))
            }
            _cartUIState.update { currentState ->
                currentState.copy(
                    productList = ArrayList(products),
                    total = total.toString(),
                )
            }
        }
    }
}