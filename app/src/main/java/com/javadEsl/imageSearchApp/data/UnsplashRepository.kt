package com.javadEsl.imageSearchApp.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.javadEsl.imageSearchApp.api.UnsplashApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UnsplashRepository @Inject constructor(private val unsplashApi: UnsplashApi) {
    private val scope = CoroutineScope(Dispatchers.Main + Job())
    fun getSearchResults(query: String) =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { UnsplashPagingSource(unsplashApi, query) }
        ).liveData


    suspend fun getPhotoDetail(id: String) = unsplashApi.getPhoto(id = id)

    suspend fun getUserPhotos(userName: String) = unsplashApi.getUserPhotos(userName = userName)

}