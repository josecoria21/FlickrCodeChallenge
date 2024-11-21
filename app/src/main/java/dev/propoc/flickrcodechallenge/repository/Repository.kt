package dev.propoc.flickrcodechallenge.repository

import dev.propoc.flickrcodechallenge.model.ImageResponseModel
import dev.propoc.flickrcodechallenge.network.ApiClient

class Repository {
    private val apiService = ApiClient.apiService

    suspend fun getData(tags: String): ImageResponseModel {
        return apiService.fetchData(name = tags)
    }
}