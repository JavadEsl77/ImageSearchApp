package com.javadEsl.pixel.data

import androidx.paging.LoadState
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.javadEsl.pixel.NetworkHelper
import com.javadEsl.pixel.api.PixelApi
import java.lang.Error
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PixelRepository @Inject constructor(
    private val pixelApi: PixelApi,
    private val networkHelper: NetworkHelper,
) {
    fun getSearchResults(query: String) =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                UnsplashPagingSource(pixelApi, query, networkHelper)
            }
        ).liveData

    suspend fun getRandomPhoto() = pixelApi.getRandomPhotos()

    suspend fun getPhotoDetail(id: String) = pixelApi.getPhoto(id = id)

    suspend fun getUserPhotos(userName: String) = pixelApi.getUserPhotos(userName = userName)

}