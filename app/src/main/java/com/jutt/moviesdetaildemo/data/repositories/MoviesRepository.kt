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
    val databaseRepository: DatabaseRepository,
    @Named(NamedHilts.REPOSITORY_DISPATCHER)
    private val dispatcher: CoroutineDispatcher
) {

    suspend fun syncFetchMoviesList() =
        withContext(dispatcher) {
            /**
             * We can Optimize more by adding more caching strategies here
             * like `Do not load movies from assets unless database have zero values`
             */
            val moviesAssets = getMoviesFromAssets()
            if (moviesAssets.size > databaseRepository.moviesCount()) {
                databaseRepository.clearAllMovies()
                databaseRepository.upsertMovies(*moviesAssets.toTypedArray())
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
    private fun getMoviesFromAssets(): List<Movie> {
        return try {
            Json.decodeFromString(
                JsonUtils.getJSONArray(
                    ResourceUtils.readAssets2String(ASSETS_PATH_FOR_MOVIES),
                    ASSETS_MOVIES_LIST_FIELD,
                    JSONArray()
                ).toString()
            ) ?: listOf()
        } catch (e: Exception) {
            emptyList()
        }
    }
}