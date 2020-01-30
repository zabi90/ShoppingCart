package de.starkling.shoppingcartsample

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import de.starkling.shoppingcart.managers.CartManager
import de.starkling.shoppingcart.models.CartItem
import de.starkling.shoppingcartsample.adapter.CartListAdapter
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class CartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        val cart =  CartManager.getInstance(applicationContext)
        val cartItems = cart.getCartItems()
        supportActionBar?.title = "Cart"
        val adapter = CartListAdapter(this)

        adapter.setItems(cartItems)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        cart.subscribeCartTotal().observe(this, Observer {
            totalTextView.text = "Total Amount: ${it.totalAmount}.Rs"
        })

        adapter.addItemUpdateListener(object : OnItemUpdateListener<CartItem> {

            override fun onItemUpdated(item: CartItem, position: Int, view: View) {
                CoroutineScope(Dispatchers.Main).launch {
                    cart.updateItem(item)
                }
            }
        })
    }
}
