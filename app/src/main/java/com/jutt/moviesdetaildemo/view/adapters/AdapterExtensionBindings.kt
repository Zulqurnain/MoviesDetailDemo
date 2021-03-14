package com.jutt.moviesdetaildemo.view.adapters

import android.widget.TextView
import androidx.appcompat.widget.AppCompatRatingBar
import androidx.recyclerview.widget.RecyclerView
import com.abed.kotlin_recycler.adapters.ViewTypeRecyclerAdapter
import com.abed.kotlin_recycler.withViewTypeAdapter
import com.jutt.moviesdetaildemo.R
import com.jutt.moviesdetaildemo.data.models.Movie

object AdapterExtensionBindings {

    fun multiTypeBindingForSearchResults(
        recyclerView: RecyclerView,
        data:List<Any>,
        onClick : ((Any) -> Unit)? = null
    ): ViewTypeRecyclerAdapter<Any> {
        return recyclerView.withViewTypeAdapter(
            dataList = data,
            viewType = { position ->
                when(data[position]) {
                    is String -> R.layout.layout_header_year
                    is Movie -> R.layout.layout_movie_item
                    else -> throw IllegalArgumentException("Invalid type of data $position")
                }
            },
            onBindView = { local ->
                when(local) {
                    is String -> {
                        val yearName = itemView.findViewById<TextView>(R.id.year_name)
                        yearName.text = local
                    }
                    is Movie -> {
                        val movieName = itemView.findViewById<TextView>(R.id.movie_name)
                        val yearName = itemView.findViewById<TextView>(R.id.movie_year)
                        val ratingBar = itemView.findViewById<AppCompatRatingBar>(R.id.mtv_rating)
                        movieName.text = local.title
                        yearName.text = local.year.toString()
                        ratingBar.rating = local.rating.toFloat()
                    }
                    else -> throw IllegalArgumentException("Invalid type of data $position")
                }
            },
            onClickListener = { position -> onClick?.invoke(data[position]) })
    }
}