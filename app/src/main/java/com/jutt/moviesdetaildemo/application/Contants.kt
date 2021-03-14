package com.jutt.moviesdetaildemo.application

import androidx.paging.PagedList

object Contants {

    const val SERVER_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    const val FRIENDLY_DATE_TIME_FORMAT = "dd MMM yyyy hh:mm a"

    const val ASSETS_PATH_FOR_MOVIES = "gotjson/movies.json"
    const val ASSETS_MOVIES_LIST_FIELD = "movies"

    const val DEFAULT_PAGE_SIZE: Int = 20
    const val DEFAULT_PAGE_PREFETCH_DISTANCE: Int = 10

    val paginationConfig = PagedList.Config.Builder()
        .setPageSize(DEFAULT_PAGE_SIZE)
        .setInitialLoadSizeHint(DEFAULT_PAGE_SIZE)
        .setPrefetchDistance(DEFAULT_PAGE_PREFETCH_DISTANCE)
        .setEnablePlaceholders(false)
}