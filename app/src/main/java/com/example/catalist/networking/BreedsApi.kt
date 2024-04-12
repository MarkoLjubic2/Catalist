package com.example.catalist.networking

import retrofit2.http.GET

interface BreedsApi {

    @GET("breeds")
    suspend fun getAllBreeds(): List<BreedApiModel>

}
