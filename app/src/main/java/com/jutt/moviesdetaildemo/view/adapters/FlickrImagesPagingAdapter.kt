package com.jutt.moviesdetaildemo.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jutt.moviesdetaildemo.R
import com.jutt.moviesdetaildemo.data.models.FlickrMappedPhoto
import com.jutt.moviesdetaildemo.databinding.LayoutImageSimpleBinding
import com.jutt.moviesdetaildemo.utils.loadImageFromUrl

interface OnListItemClickListener<T> {
    fun onListItemClick(view: View, item: T, position: Int)
}

class FlickrImagesPagingAdapter : PagedListAdapter<FlickrMappedPhoto,
        FlickrImagesPagingAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FlickrMappedPhoto>() {
            override fun areItemsTheSame(
                oldItem: FlickrMappedPhoto,
                newItem: FlickrMappedPhoto
            ): Boolean = (oldItem.id == newItem.id)

            override fun areContentsTheSame(
                oldItem: FlickrMappedPhoto,
                newItem: FlickrMappedPhoto
            ): Boolean = (oldItem.title == newItem.title
                    && oldItem.urlOfImage == newItem.urlOfImage)
        }
    }

    private var itemClickListener : OnListItemClickListener<FlickrMappedPhoto>? = null
    fun setOnItemClick(itemClickListener: OnListItemClickListener<FlickrMappedPhoto>?){
        this@FlickrImagesPagingAdapter.itemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        itemClickListener = itemClickListener,
        binding = LayoutImageSimpleBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it,position) }
    }

    class ViewHolder(
        val binding: LayoutImageSimpleBinding,
        val itemClickListener: OnListItemClickListener<FlickrMappedPhoto>?
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FlickrMappedPhoto,position: Int) {
            binding.ivImage.loadImageFromUrl(
                url = item.urlOfImage ?: "",
                placeHolder = R.drawable.vertical_cover_style_gradient
            )
            binding.ivImage.tag = item
            binding.ivImage.setOnClickListener {
                this@ViewHolder.itemClickListener?.onListItemClick(itemView,item,position)
            }
        }
    }
}