package de.starkling.shoppingcart.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Zohaib Akram on 2020-01-28
 * Copyright © 2019 Starkling. All rights reserved.
 */
@Entity
data class CartItem(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var itemId: String,
    @ColumnInfo(name = "name")
    var itemName: String,
    @ColumnInfo(name = "price")
    var itemPrice: Float = 0.0f,
    @ColumnInfo(name = "quantity")
    override var itemQuantity: Int = 0
) : Saleable {

    constructor(saleable: Saleable) : this(saleable.getId(),saleable.getName(),saleable.getPrice(), saleable.getQuantity())

    override fun getId(): String {
        return itemId
    }

    override fun getName(): String {
        return itemName
    }

    override fun getPrice(): Float {
        return itemPrice
    }

    override fun getQuantity(): Int {
        return itemQuantity
    }

    override fun getTotal(): Float {
        return itemPrice * itemQuantity
    }

}