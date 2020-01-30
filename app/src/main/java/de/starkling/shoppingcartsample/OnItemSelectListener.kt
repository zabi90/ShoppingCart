package de.starkling.shoppingcartsample

import android.view.View


interface OnItemSelectListener<T> {

    fun onItemSelected(item: T, position: Int, view: View)
}