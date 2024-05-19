package com.example.e_marketapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_marketapp.data.domain.RetrofitClient
import com.example.e_marketapp.data.repository.ProductRepository
import com.example.e_marketapp.ui.state.HomeUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val productRepository: ProductRepository = ProductRepository(RetrofitClient.apiService)

    private val _homeUIState = MutableStateFlow(HomeUIState())
    val homeUIState: StateFlow<HomeUIState> = _homeUIState.asStateFlow()

    fun getProductFromService() {
        viewModelScope.launch {
            val products = productRepository.getProduct()
            _homeUIState.update { currentState ->
                currentState.copy(
                    productList = products,
                    backupProductList = products,
                )
            }
        }
    }

    fun filterProducts(query: String) {
        _homeUIState.update { currentState ->
            currentState.copy(
                productList = homeUIState.value.backupProductList,
            )
        }
        val list = _homeUIState.value.productList.filter {
            it.name!!.contains(query, ignoreCase = true)
        }
        _homeUIState.update { currentState ->
            currentState.copy(
                productList = ArrayList(list),
            )
        }
    }
}