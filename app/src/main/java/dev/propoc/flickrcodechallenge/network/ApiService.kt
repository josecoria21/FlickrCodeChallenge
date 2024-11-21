package dev.propoc.flickrcodechallenge.network

import dev.propoc.flickrcodechallenge.model.ImageResponseModel
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("photos_public.gne")
    suspend fun fetchData(
        @Query("format") formato: String = "json",
        @Query("nojsoncallback") valor: Int = 1,
        @Query("tags") name: String
    ): ImageResponseModel // Adjusted return type if applicable
}