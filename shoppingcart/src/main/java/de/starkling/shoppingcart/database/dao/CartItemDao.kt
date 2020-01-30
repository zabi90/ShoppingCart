package de.starkling.shoppingcart.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import de.starkling.shoppingcart.models.CartItem

/**
 * Created by Zohaib Akram on 2020-01-28
 * Copyright © 2019 Starkling. All rights reserved.
 */
@Dao
interface CartItemDao {

    @Query("SELECT * FROM CartItem")
    suspend fun getAll(): List<CartItem>

    @Query("SELECT * FROM CartItem Where id =:id")
    suspend fun get(id: String): CartItem?

    @Insert
    suspend fun insert(cartItem: CartItem):Long

    @Query("DELETE FROM CartItem Where id=:id")
    suspend fun delete(id: String):Int

    @Query("UPDATE CartItem SET quantity = :quantity  Where id=:id")
    suspend fun update(id:String,quantity:Int):Int

}