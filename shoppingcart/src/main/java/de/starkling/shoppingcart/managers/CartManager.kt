package de.starkling.shoppingcart.managers

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import de.starkling.shoppingcart.database.CartDatabase
import de.starkling.shoppingcart.models.CartItem
import de.starkling.shoppingcart.models.Saleable
import kotlinx.coroutines.*

/**
 * Created by Zohaib Akram on 2020-01-28
 * Copyright © 2019 Starkling. All rights reserved.
 */

class CartManager private constructor(context: Context) {


    private val cartTotal: MutableLiveData<CartTotal> by lazy {
        MutableLiveData<CartTotal>()
    }

    private val items = ArrayList<CartItem>()


    private var db: CartDatabase

    init {

        db = Room.databaseBuilder(
            context,
            CartDatabase::class.java, DB_NAME
        ).build()

        updateTotals()
    }

    /**
     * Return the list of cart item
     */
    fun getCartItems(): ArrayList<CartItem> {
        val cartItem = ArrayList<CartItem>()
        cartItem.addAll(items)
        return cartItem
    }

    /**
     * Notify totals items and their amounts when item add or remove from cart
     * by calling #updateItem(saleable: Saleable)
     * @return LiveData<CartTotal>
     */
    fun subscribeCartTotal(): LiveData<CartTotal> {
        return cartTotal
    }

    /**
     * Remove item from cart db
     */
    suspend fun removeItem(saleable: Saleable): Int = coroutineScope {

        val deleteResult = async { db.cartItemDao().delete(saleable.getId()) }
        updateTotals()
        return@coroutineScope deleteResult.await()
    }

    /**
     * Update item in cart db and update the totals items and amounts
     */
    suspend fun updateItem(saleable: Saleable) = coroutineScope {

        val readResult = async { db.cartItemDao().get(saleable.getId()) }

        val cartItem = readResult.await()

        if (cartItem != null) {

            if (saleable.getQuantity() == 0) {
                db.cartItemDao().delete(saleable.getId())
            } else {
                cartItem.itemQuantity = saleable.getQuantity()
                val updateResult =
                    async { db.cartItemDao().update(saleable.getId(), saleable.getQuantity()) }
                updateResult.await()
            }
            updateTotals()
        } else {
            if (saleable.getQuantity() > 0) {
                val insertResult = async { db.cartItemDao().insert(CartItem(saleable)) }
                insertResult.await()
                updateTotals()
            }
        }
    }

    /**
     * Map your saleable list quantity with the cart item quantity
     * @return Mapped saleable list
     */
    suspend fun <T : Saleable> mapWithCart(saleable: ArrayList<T>): ArrayList<T> = coroutineScope {

        val products = saleable as ArrayList<Saleable>

        val result = async {

            products.forEachIndexed { index, product ->

                val cartItem:CartItem? = try {
                    items.first { cartItem ->
                        product.getId().equals(cartItem.getId(), false)
                    }
                } catch (exception:Exception){
                     null
                }

                if(cartItem != null){
                    products[index].itemQuantity = cartItem.getQuantity()
                }else{
                    products[index].itemQuantity = 0
                }
            }
        }
        result.await()

        return@coroutineScope products as ArrayList<T>
    }


    private fun updateTotals() {

        CoroutineScope(Dispatchers.Default).launch {

            items.clear()

            val readResult = async { items.addAll(db.cartItemDao().getAll()) }

            readResult.await()

            var totalCost = 0.0f

            items.forEach {
                totalCost += it.getTotal()
            }

            cartTotal.postValue(CartTotal(totalCost, items.size))
        }

    }

    companion object : SingletonHolder<CartManager, Context>(::CartManager) {
        const val DB_NAME = "ShoppingCartDB"
    }
}

/**
 * Holder model class for cart total info
 */
data class CartTotal(var totalAmount: Float, var totalItems: Int)


open class SingletonHolder<out T : Any, in A>(creator: (A) -> T) {
    private var creator: ((A) -> T)? = creator
    @Volatile
    private var instance: T? = null

    fun getInstance(arg: A): T {
        val i = instance
        if (i != null) {
            return i
        }

        return synchronized(this) {
            val i2 = instance
            if (i2 != null) {
                i2
            } else {
                val created = creator!!(arg)
                instance = created
                creator = null
                created
            }
        }
    }
}