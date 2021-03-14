package com.jutt.moviesdetaildemo.data.repositories

import com.jutt.moviesdetaildemo.data.models.Movie
import com.jutt.moviesdetaildemo.data.persistence.AppDatabase
import com.jutt.moviesdetaildemo.di.NamedHilts
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class DatabaseRepository @Inject constructor(
    private val database: AppDatabase
) {
    suspend fun getAllMovies() = database.moviesDao().loadAll()

    suspend fun getMoviesByQuery(query: String, maximumRecordPerYear: Int) = database.moviesDao().loadMoviesByQuery(query, maximumRecordPerYear)

    suspend fun moviesCount() = database.moviesDao().getCount()

    suspend fun clearAllMovies() = database.moviesDao().clearTable()

    suspend fun upsertMovies(vararg items: Movie) = database.moviesDao().insertOrUpdate(items.toList())

    suspend fun addAllMovies(facts: List<Movie>) = database.moviesDao().insertOrUpdate(facts)
}