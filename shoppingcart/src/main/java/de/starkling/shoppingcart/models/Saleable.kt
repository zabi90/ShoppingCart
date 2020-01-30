package de.starkling.shoppingcart.models

/**
 * Created by Zohaib Akram on 2020-01-28
 * Copyright © 2019 Starkling. All rights reserved.
 */
interface Saleable {

    var itemQuantity:Int

    fun getId():String

    fun getName():String

    fun getPrice():Float

    fun getQuantity():Int

    fun getTotal() : Float

}