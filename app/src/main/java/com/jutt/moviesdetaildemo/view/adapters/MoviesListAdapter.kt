package com.jutt.moviesdetaildemo.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.jutt.moviesdetaildemo.core.RecyclerViewAdapter
import com.jutt.moviesdetaildemo.data.models.Movie
import com.jutt.moviesdetaildemo.databinding.LayoutMovieItemBinding

class MoviesListAdapter(context: Context) :
    RecyclerViewAdapter<Movie, MoviesListAdapter.ViewHolder>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        adapter = this,
        binding = LayoutMovieItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: Movie = getItem(position) ?: return
        holder.bind(position = position, item = item)
    }

    class ViewHolder(
        adapter: MoviesListAdapter,
        var binding: LayoutMovieItemBinding
    ) : RecyclerViewAdapter.ViewHolder<Movie>(adapter, binding.root) {

        override fun bind(position: Int, item: Movie) {
            super.bind(position, item)

            binding.movieName.text = item.title
            binding.movieYear.text = item.year.toString()
            binding.mtvRating.rating = item.rating.toFloat()
        }
    }
}