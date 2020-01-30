package de.starkling.shoppingcartsample.models

import de.starkling.shoppingcart.models.Saleable

/**
 * Created by Zohaib Akram on 2020-01-28
 * Copyright © 2019 Starkling. All rights reserved.
 */
data class Product(private var id:String,private var name:String,private var price:Float) : Saleable {

    override var itemQuantity: Int = 0

    override fun getId(): String {
        return id
    }

    override fun getName(): String {
        return name
    }

    override fun getPrice(): Float {
        return price
    }

    override fun getQuantity(): Int {
        return itemQuantity
    }

    override fun getTotal(): Float {
        return price * itemQuantity
    }
}