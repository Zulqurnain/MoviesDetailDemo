package com.jutt.moviesdetaildemo.data.persistence.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.jutt.moviesdetaildemo.data.models.Movie

@Dao
abstract class MoviesDao : BaseDao<Movie>() {
    @Query("SELECT * FROM Movie WHERE id = :id LIMIT 1")
    abstract suspend fun loadById(id: Long): Movie

    @Query("DELETE FROM Movie WHERE id = :id")
    abstract suspend fun deleteById(id: Long): Int

    @Query("SELECT * FROM Movie")
    abstract suspend fun loadAll(): List<Movie>

    @Query("SELECT * FROM Movie WHERE title LIKE :query || '%' GROUP BY year LIMIT 0,:max")
    abstract suspend fun loadMoviesByQuery(query: String, max: Int): List<Movie>

    @Query("SELECT COUNT(id) FROM Movie")
    abstract suspend fun getCount(): Int

    @Query("DELETE FROM Movie")
    abstract suspend fun clearTable()
}
