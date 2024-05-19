package com.example.e_marketapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.e_marketapp.data.model.Product
import com.example.e_marketapp.ui.state.ProductUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ProductViewModel : ViewModel() {

    private val _productUIState = MutableStateFlow(ProductUIState())
    val productUIState: StateFlow<ProductUIState> = _productUIState.asStateFlow()

    fun setProduct(product: Product) {
        _productUIState.update { currentState ->
            currentState.copy(
                product = product,
                title = product.name,
            )
        }
    }
}