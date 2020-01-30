package de.starkling.shoppingcartsample

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.util.*


abstract class BaseRecyclerAdapter<T, H : RecyclerView.ViewHolder> constructor(private var context: Context) :
    RecyclerView.Adapter<H>() {

    protected var listener: OnItemSelectListener<T>? = null
    protected var updateItemListener: OnItemUpdateListener<T>? = null

    var items: ArrayList<T> = ArrayList()

    var tempList: ArrayList<T> = ArrayList()


    fun setItems(items: List<T>?) {
        this.items.clear()
        if (items != null && items.isNotEmpty()) {
            this.items.addAll(items)
            tempList.addAll(items)
        }

        this.notifyDataSetChanged()
    }

    fun addItems(items: List<T>?) {
        val position = this.items.size
        if (items != null && items.isNotEmpty()) {
            this.items.addAll(items)
            this.notifyItemRangeInserted(position, items.size)
        }

    }

    fun getItem(position: Int): T? {
        return if (position < this.items.size) this.items[position] else null
    }

    fun getItems(): List<T> {
        return this.items
    }

    fun remove(position: Int) {
        this.items.removeAt(position)
        this.notifyItemRemoved(position)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun ViewGroup.inflate(layoutResId: Int): View {
        return LayoutInflater.from(context).inflate(layoutResId, this, false)
    }


    fun addListener(listener: OnItemSelectListener<T>) {
        this.listener = listener
    }

    fun addItemUpdateListener(listener: OnItemUpdateListener<T>) {
        this.updateItemListener = listener
    }
}