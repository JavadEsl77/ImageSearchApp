package com.javadEsl.imageSearchApp.api

import com.javadEsl.imageSearchApp.data.ModelPhoto
import com.javadEsl.imageSearchApp.data.UnsplashPhoto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface UnsplashApi {

    companion object {
        //        const val BASE_URL = "https://api.unsplash.com/"
//        const val BASE_URL = "https://us.mahdi-saberi.ir/"
        const val BASE_URL = "https://un.one-developer.ir/napi/"
        const val CLIENT_ID = "KbhV9-G3KY1l7EtsmS0wI3BgeOVBhjWodZ9Ix9n1Btw"
    }

    @Headers("Accept-Version: v1", "Authorization: Client-ID $CLIENT_ID")
    @GET("search/photos")
    suspend fun searchPhotos(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): UnsplashResponse


    @Headers("Accept-Version: v1", "Authorization: Client-ID $CLIENT_ID")
    @GET("photos/{id}")
    suspend fun getPhoto(
        @Path("id") id: String
    ): Response<ModelPhoto>

    @GET("users/{username}/photos")
    suspend fun getUserPhotos(
        @Path("username") userName: String
    ): List<UnsplashPhoto>?
}