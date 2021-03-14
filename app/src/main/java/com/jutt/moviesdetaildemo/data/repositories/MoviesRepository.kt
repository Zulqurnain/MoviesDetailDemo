package com.jutt.moviesdetaildemo.data.repositories

import com.blankj.utilcode.util.JsonUtils
import com.blankj.utilcode.util.ResourceUtils
import com.jutt.moviesdetaildemo.application.Contants.ASSETS_MOVIES_LIST_FIELD
import com.jutt.moviesdetaildemo.application.Contants.ASSETS_PATH_FOR_MOVIES
import com.jutt.moviesdetaildemo.data.models.FlickrMappedPhoto
import com.jutt.moviesdetaildemo.data.models.Movie
import com.jutt.moviesdetaildemo.data.network.NetworkManager
import com.jutt.moviesdetaildemo.data.network.NetworkResponse
import com.jutt.moviesdetaildemo.data.network.toNetworkResponse
import com.jutt.moviesdetaildemo.di.NamedHilts
import com.jutt.moviesdetaildemo.extensions.string
import com.jutt.moviesdetaildemo.extensions.swap
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.json.JSONArray
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class MoviesRepository @Inject constructor(
    val networkManager: NetworkManager,
    private val databaseRepository: DatabaseRepository,
    @Named(NamedHilts.REPOSITORY_DISPATCHER)
    private val dispatcher: CoroutineDispatcher
) {

    suspend fun refreshMoviesTableWithOldData() =
        withContext(dispatcher){
            databaseRepository.upsertMovies(*emptyArray())
        }

    suspend fun syncFetchMoviesList() =
        withContext(dispatcher) {
            /**
             * We can Optimize more by adding more caching strategies here
             * like `Do not load movies from assets unless database have zero values`
             */
            val moviesAssets: ArrayList<Movie> = getMoviesFromAssets()
            if (moviesAssets.size > databaseRepository.moviesCount()) {
                sortMoviesBeforeStoringByYear(moviesAssets)
                databaseRepository.clearAllMovies()
                databaseRepository.upsertMovies(*moviesAssets.toTypedArray())
            }
            return@withContext databaseRepository.getAllMovies()
        }
    suspend fun searchMovies(searchQuery: String, maximumRecordsPerYear: Int = 5) =
        withContext(dispatcher) {
            val listOfMappedMovies: MutableList<Any> = mutableListOf()
            val moviesByYearMap =  databaseRepository.getMoviesByQuery(
                searchQuery,
                maximumRecordsPerYear
            ).groupBy { it.year }

            moviesByYearMap.keys.forEach { year ->
                listOfMappedMovies.add(year.toString())
                listOfMappedMovies.addAll(
                    moviesByYearMap[year] ?: listOf()
                )
            }
            return@withContext listOfMappedMovies
        }

    /**
     * I am Using Shell Sort
     *
     * @param movies
     */
    private fun sortMoviesBeforeStoringByYear(movies: ArrayList<Movie>) {
        /**
         * Why Use Shell Sort?
         * Because It have good performance on worst case scenario and
         * We almost always have worst case in this case AS SIMPLE AS THAT
         */
        var k = movies.size / 2
        while (k > 0) {
            for (i in k until movies.size) {
                var j = i
                while (j >= k && movies[j].year < movies[j - k].year) {
                    movies.swap(movies[j].year, movies[j - k].year)
                    j -= k
                }
            }
            k /= 2
        }
    }

    suspend fun getSingleImageFromFlickr(movieName: String) =
        withContext(dispatcher) {
            val response = networkManager.execute(
                networkManager.getImagesFromFlickr(
                    searchText = movieName,
                    page = 1,
                    pageSize = 1
                )
            )
            val listOfPhotos = response.body()?.photos?.photo ?: listOf()
            if(!listOfPhotos.isNullOrEmpty()){
                val firstOne = listOfPhotos[0]
                return@withContext response.toNetworkResponse {
                    FlickrMappedPhoto(
                        id = firstOne.id,
                        urlOfImage = "http://farm"+firstOne.farm+".static.flickr.com/"+firstOne.server+"/"+firstOne.id+"_"+firstOne.secret+".jpg",
                        title = firstOne.title
                    )
                }
            }
            return@withContext NetworkResponse<FlickrMappedPhoto>(
                success = false,
                message = response.string
            )
        }

    /**
     * Get List of Movies from Json File Provided in Assets
     *
     * @return
     */
    private fun getMoviesFromAssets(): ArrayList<Movie> {
        return try {
            Json.decodeFromString(
                JsonUtils.getJSONArray(
                    ResourceUtils.readAssets2String(ASSETS_PATH_FOR_MOVIES),
                    ASSETS_MOVIES_LIST_FIELD,
                    JSONArray()
                ).toString()
            ) ?: arrayListOf()
        } catch (e: Exception) {
            arrayListOf()
        }
    }
}