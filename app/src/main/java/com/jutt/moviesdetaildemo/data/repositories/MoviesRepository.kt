package com.jutt.moviesdetaildemo.data.repositories

import androidx.lifecycle.LiveData
import com.blankj.utilcode.util.JsonUtils
import com.blankj.utilcode.util.ResourceUtils
import com.jutt.moviesdetaildemo.R
import com.jutt.moviesdetaildemo.application.Contants.ASSETS_MOVIES_LIST_FIELD
import com.jutt.moviesdetaildemo.application.Contants.ASSETS_PATH_FOR_MOVIES
import com.jutt.moviesdetaildemo.data.models.Movie
import com.jutt.moviesdetaildemo.data.network.NetworkManager
import com.jutt.moviesdetaildemo.data.network.NetworkResponse
import com.jutt.moviesdetaildemo.data.network.toNetworkResponse
import com.jutt.moviesdetaildemo.di.NamedHilts
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.json.JSONArray
import org.json.JSONObject
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class MoviesRepository @Inject constructor(
    private val networkManager: NetworkManager,
    val databaseRepository: DatabaseRepository,
    @Named(NamedHilts.REPOSITORY_DISPATCHER)
    private val dispatcher: CoroutineDispatcher
) {

    suspend fun syncFetchMoviesList() =
        withContext(dispatcher) {
            val moviesAssets = getMoviesFromAssets()
            if (moviesAssets.size > databaseRepository.moviesCount()) {
                databaseRepository.clearAllMovies()
                databaseRepository.upsertMovies(*moviesAssets.toTypedArray())
            }
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