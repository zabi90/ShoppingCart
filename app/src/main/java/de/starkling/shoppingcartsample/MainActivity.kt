package de.starkling.shoppingcartsample

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import de.starkling.shoppingcart.managers.CartManager
import de.starkling.shoppingcartsample.models.Product
import de.starkling.shoppingcartsample.adapter.ProductListAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private var products  = ArrayList<Product>()
    private lateinit var adapter: ProductListAdapter
    private lateinit var cart:CartManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        cart = CartManager.getInstance(applicationContext)

        supportActionBar?.title = "Products"

        adapter = ProductListAdapter(this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter


        adapter.addItemUpdateListener(object : OnItemUpdateListener<Product> {

            override fun onItemUpdated(item: Product, position: Int, view: View) {
                CoroutineScope(Dispatchers.Main).launch {
                    cart.updateItem(item)
                }
            }
        })

        loadDummyProduct()

        cart.subscribeCartTotal().observe(this, Observer {
            totalTextView.text = "Checkout (${it.totalItems})  Total Amount: ${it.totalAmount}.Rs"
        })

        totalTextView.setOnClickListener {
            startActivity(
                Intent(this@MainActivity,CartActivity::class.java)
            )
        }
    }

    override fun onResume() {
        super.onResume()
        mapProductWithCartItems()
    }

    private fun loadDummyProduct(){
        for (i in 1..50){
            val product = Product("i:$i","Product:$i", 1.0f * i)
            products.add(product)
        }
    }

    private fun mapProductWithCartItems(){
        CoroutineScope(Dispatchers.Main).launch {
            adapter.setItems( cart.mapWithCart(products))
        }
    }
}
