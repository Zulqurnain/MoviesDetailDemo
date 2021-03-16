package com.jutt.moviesdetaildemo.data.persistence.daos

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth
import com.jutt.moviesdetaildemo.data.models.Movie
import com.jutt.moviesdetaildemo.data.persistence.AppDatabase
import com.jutt.moviesdetaildemo.data.repositories.FakeMovieRepositoryAndroidTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@HiltAndroidTest
@ExperimentalCoroutinesApi
@SmallTest
class MovieDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_db")
    lateinit var database: AppDatabase
    private lateinit var movieDao: MoviesDao

    private val moviesRepository by lazy { FakeMovieRepositoryAndroidTest() }

    @Before
    fun setup() {
        hiltRule.inject()
        movieDao = database.moviesDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertUser() = runBlocking {
        val movie = Movie(
            id = 1,
            title = "Avengers",
            year = 2016,
            rating = 10,
            cast = listOf("Me","Myself","mylaptop"),
            genres = listOf("Adventure","action")
        )
        movieDao.insert(movie)
        val allUsers = movieDao.loadAll()
        Truth.assertThat(allUsers).contains(movie)
    }
    @Test
    fun upsert() = runBlocking {
        val movie = Movie(
            id = 1,
            title = "Avengers",
            year = 2016,
            rating = 10,
            cast = listOf("Me","Myself","mylaptop"),
            genres = listOf("Adventure","action")
        )
        movieDao.insertOrUpdate(movie)
        val allUsers = movieDao.loadAll()
        Truth.assertThat(allUsers).contains(movie)
    }
    @Test
    fun uniqueInsert() = runBlocking {
        val movie = Movie(
            id = 1,
            title = "Avengers",
            year = 2016,
            rating = 10,
            cast = listOf("Me","Myself","mylaptop"),
            genres = listOf("Adventure","action")
        )
        val listOfSame = listOf(movie,movie,movie)
        movieDao.insertOrUpdate(listOfSame)
        val allUsers = movieDao.loadAll()
        Truth.assertThat(allUsers).hasSize(1)
    }
    @Test
    fun clearMovies() = runBlocking {
        val listOfSame = listOf(
            Movie(
                id = 2,
                title = "Avengers",
                year = 2015,
                rating = 10,
                cast = listOf("Me","Myself","mylaptop"),
                genres = listOf("Adventure","action")
            ),
            Movie(
                id = 2,
                title = "Avengers",
                year = 2012,
                rating = 10,
                cast = listOf("Me","Myself","mylaptop"),
                genres = listOf("Adventure","action")
            ),
            Movie(
                id = 3,
                title = "Avengers",
                year = 2019,
                rating = 10,
                cast = listOf("Me","Myself","mylaptop"),
                genres = listOf("Adventure","action")
            )
        )
        movieDao.insertOrUpdate(listOfSame)
        movieDao.clearTable()
        val allUsers = movieDao.loadAll()
        Truth.assertThat(allUsers).isEmpty()
    }

    /**
     * Also Tested Here: https://dbfiddle.uk/?rdbms=mysql_5.7&fiddle=ab9459e9b3307554bd19ffaa792863d4
     *
     */
    @Test
    fun loadMoviesByQuery_SeprateByYear_And_EachYearMustHave_TopRated_Five_Movies() = runBlocking {
        movieDao.insertOrUpdate(moviesRepository.searchMoviesInputTest())
        val allUsers = moviesRepository.searchMovies(
            searchQuery = "A",
            maximumRecordsPerYear = 5
        )
        Truth.assertThat(allUsers).isEqualTo(moviesRepository.searchMoviesExpectedOutput())
    }

}