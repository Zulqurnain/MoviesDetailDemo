package com.jutt.moviesdetaildemo.data.repositories

import com.jutt.moviesdetaildemo.data.models.Movie
import com.jutt.moviesdetaildemo.data.persistence.AppDatabase
import javax.inject.Inject
import javax.inject.Singleton

class FakeDatabaseRepository {
    private val fakeMoviesDB : MutableList<Movie> by lazy { arrayListOf() }

    suspend fun getAllMovies() = fakeMoviesDB

    fun searchMoviesInput(): MutableList<Movie> {
        return mutableListOf(
            Movie(
                id = 1,
                title = "A Avengers1",
                year = 2016,
                rating = 1,
                cast = listOf("Me", "Myself", "mylaptop"),
                genres = listOf("Adventure", "action")
            ),
            Movie(
                id = 2,
                title = "A Avengers2",
                year = 2016,
                rating = 1,
                cast = listOf("Me", "Myself", "mylaptop"),
                genres = listOf("Adventure", "action")
            ),
            Movie(
                id = 3,
                title = "A Avengers3",
                year = 2016,
                rating = 2,
                cast = listOf("Me", "Myself", "mylaptop"),
                genres = listOf("Adventure", "action")
            ),
            Movie(
                id = 4,
                title = "A Avengers4",
                year = 2016,
                rating = 3,
                cast = listOf("Me", "Myself", "mylaptop"),
                genres = listOf("Adventure", "action")
            ),
            Movie(
                id = 5,
                title = "A Avengers5",
                year = 2016,
                rating = 3,
                cast = listOf("Me", "Myself", "mylaptop"),
                genres = listOf("Adventure", "action")
            ),
            Movie(
                id = 6,
                title = "A Avengers6",
                year = 2016,
                rating = 3,
                cast = listOf("Me", "Myself", "mylaptop"),
                genres = listOf("Adventure", "action")
            ),
            Movie(
                id = 7,
                title = "A Wisdom7",
                year = 2015,
                rating = 1,
                cast = listOf("Me", "Myself", "mylaptop"),
                genres = listOf("Adventure", "action")
            ),
            Movie(
                id = 8,
                title = "A Wisdom8",
                year = 2015,
                rating = 4,
                cast = listOf("Me", "Myself", "mylaptop"),
                genres = listOf("Adventure", "action")
            ),
            Movie(
                id = 9,
                title = "A Wisdom9",
                year = 2015,
                rating = 2,
                cast = listOf("Me", "Myself", "mylaptop"),
                genres = listOf("Adventure", "action")
            ),
            Movie(
                id = 10,
                title = "A Wisdom10",
                year = 2015,
                rating = 5,
                cast = listOf("Me", "Myself", "mylaptop"),
                genres = listOf("Adventure", "action")
            ),
            Movie(
                id = 11,
                title = "A Wisdom11",
                year = 2015,
                rating = 5,
                cast = listOf("Me", "Myself", "mylaptop"),
                genres = listOf("Adventure", "action")
            ),
            Movie(
                id = 12,
                title = "A Wisdom12",
                year = 2015,
                rating = 5,
                cast = listOf("Me", "Myself", "mylaptop"),
                genres = listOf("Adventure", "action")
            ),
            Movie(
                id = 13,
                title = "A Mr.Bean13",
                year = 2014,
                rating = 1,
                cast = listOf("Me", "Myself", "mylaptop"),
                genres = listOf("Adventure", "action")
            ),
            Movie(
                id = 14,
                title = "A Mr.Bean14",
                year = 2014,
                rating = 2,
                cast = listOf("Me", "Myself", "mylaptop"),
                genres = listOf("Adventure", "action")
            ),
            Movie(
                id = 15,
                title = "A Mr.Bean15",
                year = 2014,
                rating = 4,
                cast = listOf("Me", "Myself", "mylaptop"),
                genres = listOf("Adventure", "action")
            ),
            Movie(
                id = 16,
                title = "A Mr.Bean16",
                year = 2014,
                rating = 5,
                cast = listOf("Me", "Myself", "mylaptop"),
                genres = listOf("Adventure", "action")
            ),
            Movie(
                id = 17,
                title = "A Mr.Bean17",
                year = 2014,
                rating = 5,
                cast = listOf("Me", "Myself", "mylaptop"),
                genres = listOf("Adventure", "action")
            ),
            Movie(
                id = 18,
                title = "A Mr.Bean18",
                year = 2014,
                rating = 5,
                cast = listOf("Me", "Myself", "mylaptop"),
                genres = listOf("Adventure", "action")
            )
        )
    }

    fun searchMoviesExpectedOutput() =
        listOf(
            "2016",
            Movie(
                id = 2,
                title = "A Avengers2",
                year = 2016,
                rating = 1,
                cast = listOf("Me", "Myself", "mylaptop"),
                genres = listOf("Adventure", "action")
            ),
            Movie(
                id = 3,
                title = "A Avengers3",
                year = 2016,
                rating = 2,
                cast = listOf("Me", "Myself", "mylaptop"),
                genres = listOf("Adventure", "action")
            ),
            Movie(
                id = 4,
                title = "A Avengers4",
                year = 2016,
                rating = 3,
                cast = listOf("Me", "Myself", "mylaptop"),
                genres = listOf("Adventure", "action")
            ),
            Movie(
                id = 5,
                title = "A Avengers5",
                year = 2016,
                rating = 3,
                cast = listOf("Me", "Myself", "mylaptop"),
                genres = listOf("Adventure", "action")
            ),
            Movie(
                id = 6,
                title = "A Avengers6",
                year = 2016,
                rating = 3,
                cast = listOf("Me", "Myself", "mylaptop"),
                genres = listOf("Adventure", "action")
            ),
            "2015",
            Movie(
                id = 8,
                title = "A Wisdom8",
                year = 2015,
                rating = 4,
                cast = listOf("Me", "Myself", "mylaptop"),
                genres = listOf("Adventure", "action")
            ),
            Movie(
                id = 9,
                title = "A Wisdom9",
                year = 2015,
                rating = 2,
                cast = listOf("Me", "Myself", "mylaptop"),
                genres = listOf("Adventure", "action")
            ),
            Movie(
                id = 10,
                title = "A Wisdom10",
                year = 2015,
                rating = 5,
                cast = listOf("Me", "Myself", "mylaptop"),
                genres = listOf("Adventure", "action")
            ),
            Movie(
                id = 11,
                title = "A Wisdom11",
                year = 2015,
                rating = 5,
                cast = listOf("Me", "Myself", "mylaptop"),
                genres = listOf("Adventure", "action")
            ),
            Movie(
                id = 12,
                title = "A Wisdom12",
                year = 2015,
                rating = 5,
                cast = listOf("Me", "Myself", "mylaptop"),
                genres = listOf("Adventure", "action")
            ),
            "2014",
            Movie(
                id = 14,
                title = "A Mr.Bean14",
                year = 2014,
                rating = 2,
                cast = listOf("Me", "Myself", "mylaptop"),
                genres = listOf("Adventure", "action")
            ),
            Movie(
                id = 15,
                title = "A Mr.Bean15",
                year = 2014,
                rating = 4,
                cast = listOf("Me", "Myself", "mylaptop"),
                genres = listOf("Adventure", "action")
            ),
            Movie(
                id = 16,
                title = "A Mr.Bean16",
                year = 2014,
                rating = 5,
                cast = listOf("Me", "Myself", "mylaptop"),
                genres = listOf("Adventure", "action")
            ),
            Movie(
                id = 17,
                title = "A Mr.Bean17",
                year = 2014,
                rating = 5,
                cast = listOf("Me", "Myself", "mylaptop"),
                genres = listOf("Adventure", "action")
            ),
            Movie(
                id = 18,
                title = "A Mr.Bean18",
                year = 2014,
                rating = 5,
                cast = listOf("Me", "Myself", "mylaptop"),
                genres = listOf("Adventure", "action")
            )
        )

    suspend fun moviesCount() = fakeMoviesDB.size

    suspend fun clearAllMovies() = fakeMoviesDB.clear()

    suspend fun upsertMovies(vararg items: Movie){
        // simple logic to upsert
        val mutableList = items.toMutableList()
        mutableList.forEach { input ->
            var found = false
            fakeMoviesDB.forEachIndexed { index, db ->
                if(db.id == input.id){
                    found = true
                    fakeMoviesDB[index] = input
                }
            }
            if(!found){
                fakeMoviesDB.add(input)
            }
        }
    }
}