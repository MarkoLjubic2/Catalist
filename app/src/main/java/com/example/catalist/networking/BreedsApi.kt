package com.example.catalist.networking

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface BreedsApi {

    @Headers("x-api-key: live_hQzHG2bkkOYuSGk1fIcnsRqePkhrLChVTELTpkeg8gZ3IUpdqRP0ftAuVG3r7Oz3")
    @GET("breeds")
    suspend fun getAllBreeds(): List<BreedApiModel>

    @Headers("x-api-key: live_hQzHG2bkkOYuSGk1fIcnsRqePkhrLChVTELTpkeg8gZ3IUpdqRP0ftAuVG3r7Oz3")
    @GET("breeds/{breed_id}")
    suspend fun getBreed(
        @Path("breed_id") breedId: Int,
    ): BreedApiModel
}
