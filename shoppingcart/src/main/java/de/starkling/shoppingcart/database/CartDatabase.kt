package de.starkling.shoppingcart.database

import androidx.room.Database
import androidx.room.RoomDatabase
import de.starkling.shoppingcart.database.dao.CartItemDao
import de.starkling.shoppingcart.models.CartItem

/**
 * Created by Zohaib Akram on 2020-01-28
 * Copyright © 2019 Starkling. All rights reserved.
 */
@Database(entities = [CartItem::class], version = 1)
abstract class CartDatabase : RoomDatabase() {
    abstract fun cartItemDao(): CartItemDao
}