package com.jutt.moviesdetaildemo.data.repositories

import com.jutt.moviesdetaildemo.data.models.Movie
import com.jutt.moviesdetaildemo.data.persistence.AppDatabase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DatabaseRepository @Inject constructor(private val database: AppDatabase) {
    fun getAllMovies()  = database.moviesDao().loadAll()

    suspend fun clearAllMovies() {
        database.moviesDao().clearTable()
    }

    suspend fun upsertMovies(vararg items: Movie){
        database.moviesDao().insertOrUpdate(items.toList())
    }

    suspend fun addAllMovies(facts: List<Movie>){
        database.moviesDao().insertOrUpdate(facts)
    }
}