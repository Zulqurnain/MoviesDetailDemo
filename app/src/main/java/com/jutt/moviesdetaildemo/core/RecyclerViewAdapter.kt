package com.jutt.moviesdetaildemo.core

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.jutt.moviesdetaildemo.R

abstract class RecyclerViewAdapter<M, VH : RecyclerViewAdapter.ViewHolder<M>>(
        val context: Context, arr: MutableList<M> = ArrayList()
) : RecyclerView.Adapter<VH>() {

    val inflater: LayoutInflater = LayoutInflater.from(context)
    val onItemClickListener: ItemClickListener<M> by lazy {
        ItemClickListener(
                adapter = this
        )
    }
    val onItemLongClickListener: LongClickListener<M> by lazy {
        LongClickListener(
                adapter = this
        )
    }

    var data: MutableList<M> = arr
        set(arr) {
            clear()
            addAll(arr)
        }

    open fun set(arr: MutableList<M>?) {
        data = arr ?: ArrayList()
    }

    val isEmpty: Boolean
        get() = data.isEmpty()

    val actualSize: Int
        get() = data.size

    fun getItem(position: Int): M? {
        return if (position >= 0 && position < data.size) data[position] else null
    }

    override fun getItemCount(): Int {
        return actualSize
    }

    fun swap(arr: MutableList<M>) {
        data = arr
    }

    fun clear() {
        data.clear()
    }

    fun addAll(arr: List<M>) {
        data.addAll(arr)
    }

    open fun setOnItemClickListener(onItemClick: OnItemClickListener<M>?) {
        onItemClickListener.onItemClick = onItemClick
    }

    open fun setOnItemLongClickListener(onItemLongClick: OnItemLongClickListener<M>?) {
        onItemLongClickListener.onItemLongClick = onItemLongClick
    }

    interface OnItemClickListener<M> {
        fun onItemClick(adapter: RecyclerViewAdapter<M, *>, view: View, position: Int, item: M)
    }

    interface OnItemLongClickListener<M> {
        fun onItemLongClick(
                adapter: RecyclerViewAdapter<M, *>,
                view: View,
                position: Int,
                item: M
        ): Boolean
    }

    class ItemClickListener<M>(
            private val adapter: RecyclerViewAdapter<M, *>,
            internal var onItemClick: OnItemClickListener<M>? = null
    ) : View.OnClickListener {
        override fun onClick(view: View) {
            onItemClick?.let { listener ->
                val position = view.getTag(R.id.position) as? Int ?: return@let
                val item = adapter.getItem(position) ?: return@let
                listener.onItemClick(adapter, view, position, item)
            }
        }

    }

    class LongClickListener<M>(
            private val adapter: RecyclerViewAdapter<M, *>,
            internal var onItemLongClick: OnItemLongClickListener<M>? = null
    ) : View.OnLongClickListener {
        override fun onLongClick(view: View): Boolean {
            return onItemLongClick?.let { listener ->
                val position = view.getTag(R.id.position) as? Int ?: return@let null
                val item = adapter.getItem(position) ?: return@let null
                return@let listener.onItemLongClick(adapter, view, position, item)
            } ?: false
        }
    }

    abstract class ViewHolder<in M> internal constructor(
            adapter: RecyclerViewAdapter<M, *>,
            itemView: View
    ) :
            RecyclerView.ViewHolder(itemView) {

        open fun bind(position: Int, item: M) {
            itemView.setTag(R.id.position, position)
        }

        init {
            itemView.setOnClickListener(adapter.onItemClickListener)
            itemView.setOnLongClickListener(adapter.onItemLongClickListener)
        }
    }

}