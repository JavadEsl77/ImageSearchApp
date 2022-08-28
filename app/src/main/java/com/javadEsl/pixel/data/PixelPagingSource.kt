package com.javadEsl.pixel.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.javadEsl.pixel.NetworkHelper
import com.javadEsl.pixel.api.PixelApi
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

private const val UNSPLASH_STARTING_PAGE_INDEX = 1

class UnsplashPagingSource @Inject constructor(
    private val pixelApi: PixelApi,
    private val query: String,
    private val networkHelper: NetworkHelper
) :
    PagingSource<Int, UnsplashPhoto>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnsplashPhoto> {

        val position = params.key ?: UNSPLASH_STARTING_PAGE_INDEX
        return try {
            val response = pixelApi.searchPhotos(query, position, params.loadSize)
            val photos = response.results
            LoadResult.Page(
                data = photos,
                prevKey = if (position == UNSPLASH_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (photos.isEmpty()) null else position + 1
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, UnsplashPhoto>): Int? {
        TODO("Not yet implemented")
    }
}