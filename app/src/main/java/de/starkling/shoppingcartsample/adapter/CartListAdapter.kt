package de.starkling.shoppingcartsample.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import de.starkling.shoppingcart.models.CartItem
import de.starkling.shoppingcart.widget.CounterView
import de.starkling.shoppingcartsample.BaseRecyclerAdapter
import de.starkling.shoppingcartsample.R
import kotlinx.android.synthetic.main.item_product_list.view.*

/**
 * Created by Zohaib Akram on 2020-01-30
 * Copyright © 2019 Starkling. All rights reserved.
 */
class CartListAdapter(context:Context) : BaseRecyclerAdapter<CartItem, CartListAdapter.CartViewHolder>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
      return CartViewHolder(parent.inflate(R.layout.item_product_list))
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.onBindItem(items[position])
    }

    inner class CartViewHolder(itemView:View): RecyclerView.ViewHolder(itemView) {

        fun onBindItem(cartItem: CartItem){
            itemView.nameTextView.text = cartItem.getName()
            itemView.priceTextView.text = "${cartItem.getPrice()}.Rs"
            itemView.counterView.counterValue = cartItem.getQuantity()

            itemView.setOnClickListener {
                listener?.onItemSelected(cartItem,adapterPosition,itemView)
            }


            itemView.counterView.addCounterValueChangeListener(object : CounterView.CounterValueChangeListener{

                override fun onValueDelete(count: Int) {
                    cartItem.itemQuantity = count
                    updateItemListener?.onItemUpdated(cartItem,adapterPosition,itemView)
                }

                override fun onValueAdd(count: Int) {
                    cartItem.itemQuantity = count
                    updateItemListener?.onItemUpdated(cartItem,adapterPosition,itemView)
                }
            })
        }
    }
}