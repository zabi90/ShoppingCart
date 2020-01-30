package de.starkling.shoppingcartsample

import android.view.View


interface OnItemUpdateListener<T> {

    fun onItemUpdated(item: T, position: Int, view: View)
}