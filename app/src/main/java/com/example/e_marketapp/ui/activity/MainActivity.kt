package com.example.e_marketapp.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.e_marketapp.MarketApplication
import com.example.e_marketapp.R
import com.example.e_marketapp.data.model.Product
import com.example.e_marketapp.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        setupActionBarWithNavController(navController)
        NavigationUI.setupWithNavController(binding.bottomNavigation, navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    fun addToCart(product: Product) {
        product.quantity = 1
        lifecycleScope.launch {
            MarketApplication.database.productDao().insertProduct(product)
        }
    }

    fun removeFromCart(product: Product) {
        lifecycleScope.launch {
            MarketApplication.database.productDao().removeProduct(product)
        }
    }

    suspend fun getAllProducts(): List<Product> {
        return MarketApplication.database.productDao().getAllProducts()
    }

}