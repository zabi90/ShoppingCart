package de.starkling.shoppingcartsample.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import de.starkling.shoppingcart.widget.CounterView
import de.starkling.shoppingcartsample.BaseRecyclerAdapter
import de.starkling.shoppingcartsample.R
import de.starkling.shoppingcartsample.models.Product
import kotlinx.android.synthetic.main.item_product_list.view.*




/**
 * Created by Zohaib Akram on 2020-01-28
 * Copyright © 2019 Starkling. All rights reserved.
 */
class ProductListAdapter(context:Context) : BaseRecyclerAdapter<Product, ProductListAdapter.ProductViewHolder>(context) {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(parent.inflate(R.layout.item_product_list))
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.onBindItem(items[position])
    }

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        fun onBindItem(product: Product){
            itemView.nameTextView.text = product.getName()
            itemView.priceTextView.text = "${product.getPrice()}.Rs"
            itemView.counterView.counterValue = product.getQuantity()

            itemView.setOnClickListener {
                listener?.onItemSelected(product,adapterPosition,itemView)
            }


            itemView.counterView.addCounterValueChangeListener(object :CounterView.CounterValueChangeListener{

                override fun onValueDelete(count: Int) {
                    product.itemQuantity = count
                    updateItemListener?.onItemUpdated(product,adapterPosition,itemView)
                }

                override fun onValueAdd(count: Int) {
                    product.itemQuantity = count
                    updateItemListener?.onItemUpdated(product,adapterPosition,itemView)
                }
            })
        }
    }
}
