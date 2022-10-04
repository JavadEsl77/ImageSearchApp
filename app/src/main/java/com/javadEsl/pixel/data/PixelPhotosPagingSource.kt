package com.javadEsl.pixel.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.javadEsl.pixel.api.PixelApi
import com.javadEsl.pixel.data.search.PixelPhoto
import javax.inject.Inject

private const val UNSPLASH_STARTING_PAGE_INDEX = 1

class UnsplashPhotosPagingSource @Inject constructor(
    private val pixelApi: PixelApi
) :
    PagingSource<Int, PixelPhoto>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PixelPhoto> {

        val position = params.key ?: UNSPLASH_STARTING_PAGE_INDEX
        return try {
            val response = pixelApi.getPhotos(position, params.loadSize)
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

    override fun getRefreshKey(state: PagingState<Int, PixelPhoto>): Int? {
       return state.anchorPosition
    }
}