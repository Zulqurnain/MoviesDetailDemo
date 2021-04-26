package com.jutt.moviesdetaildemo.data.data_sources

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.jutt.moviesdetaildemo.data.models.FlickrMappedPhoto
import com.jutt.moviesdetaildemo.data.network.NetworkManager
import javax.inject.Inject

class FlickrPhotoSearchFactory @Inject constructor(
    val networkManager: NetworkManager
) : DataSource.Factory<Int, FlickrMappedPhoto>() {

    var searchText: String = ""
    private var showLoader: MutableLiveData<Boolean> = MutableLiveData()

    var flickrImagesDataSourceLiveData = MutableLiveData<FlickrPhotoSearchDataSource>()

    fun setUpInit(searchText: String,showLoader: MutableLiveData<Boolean>){
        this.searchText = searchText ?: ""
        this.showLoader = showLoader
        create()
    }

    override fun create(): DataSource<Int, FlickrMappedPhoto> {
        val source =
            FlickrPhotoSearchDataSource(
                networkManager = networkManager,
                searchText = searchText,
                showLoader = showLoader,
            )
        flickrImagesDataSourceLiveData.postValue(source)
        return source
    }
    fun refresh() {
        flickrImagesDataSourceLiveData.value?.invalidate()
    }
}

class FlickrPhotoSearchDataSource(
    val networkManager: NetworkManager,
    val searchText: String? = "",
    val showLoader: MutableLiveData<Boolean>
) : PageKeyedDataSource<Int, FlickrMappedPhoto>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, FlickrMappedPhoto>
    ) {
        val currentPage = 1
        val nextPage = currentPage + 1

        showLoader.postValue(true)
        val response = networkManager.execute(
            networkManager.getImagesFromFlickr(
                searchText = if (searchText.isNullOrEmpty()) " " else searchText,
                page = currentPage,
                pageSize = params.requestedLoadSize
            )
        )
        if (response.isSuccessful) {
            val bodyData = response.body()?.photos
            if (bodyData?.photo.isNullOrEmpty().not()){
                val mappedPhotos: List<FlickrMappedPhoto> =
                    bodyData?.photo?.map {
                        return@map with(it){
                            FlickrMappedPhoto(
                                id = it.id,
                                urlOfImage = "http://farm"+farm+".static.flickr.com/"+server+"/"+id+"_"+secret+".jpg",
                                title = it.title
                            )
                        }
                    } ?: listOf()

                callback.onResult(mappedPhotos, null, nextPage)
            }
        }
        showLoader.postValue(false)

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, FlickrMappedPhoto>) {
        val currentPage = params.key
        val nextPage = currentPage + 1

        showLoader.postValue(true)
        val response = networkManager.execute(
            networkManager.getImagesFromFlickr(
                searchText = if (searchText.isNullOrEmpty()) " " else searchText,
                page = currentPage,
                pageSize = params.requestedLoadSize
            )
        )
        if (response.isSuccessful) {
            val bodyData = response.body()?.photos
            if (bodyData?.photo.isNullOrEmpty().not()){
                val mappedPhotos: List<FlickrMappedPhoto> =
                    bodyData?.photo?.map {
                        return@map with(it){
                            FlickrMappedPhoto(
                                id = it.id,
                                urlOfImage = "http://farm"+farm+".static.flickr.com/"+server+"/"+id+"_"+secret+".jpg",
                                title = it.title
                            )
                        }
                    } ?: listOf()
                callback.onResult(mappedPhotos, nextPage)
            }
        }
        showLoader.postValue(false)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, FlickrMappedPhoto>) {}

}