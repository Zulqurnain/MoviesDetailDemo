package com.jutt.citiessearchmvvm.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jutt.citiessearchmvvm.R
import com.jutt.citiessearchmvvm.data.models.GeoCity
import com.jutt.citiessearchmvvm.databinding.LayoutCityItemBinding

interface OnListItemClickListener<T> {
    fun onListItemClick(view: View, item: T, position: Int)
}

class CitiesListAdapter : ListAdapter<GeoCity,
        CitiesListAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<GeoCity>() {
            override fun areItemsTheSame(
                oldItem: GeoCity,
                newItem: GeoCity
            ): Boolean = (oldItem.id == newItem.id)

            override fun areContentsTheSame(
                oldItem: GeoCity,
                newItem: GeoCity
            ): Boolean = (oldItem.fclName == newItem.fclName
                    && oldItem.fcl == newItem.fcl)
        }
    }

    private var itemClickListener : OnListItemClickListener<GeoCity>? = null
    fun setOnItemClick(itemClickListener: OnListItemClickListener<GeoCity>?){
        this@CitiesListAdapter.itemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        itemClickListener = itemClickListener,
        binding = LayoutCityItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it,position) }
    }

    class ViewHolder(
        val binding: LayoutCityItemBinding,
        val itemClickListener: OnListItemClickListener<GeoCity>?
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: GeoCity,position: Int) {
            binding.cityName.text = item.name ?: ""
            binding.areaName.text = item.adminName1 ?: ""
            binding.countryName.text = item.countryName ?: ""
            binding.root.setOnClickListener {
                this@ViewHolder.itemClickListener?.onListItemClick(itemView,item,position)
            }
        }
    }
}