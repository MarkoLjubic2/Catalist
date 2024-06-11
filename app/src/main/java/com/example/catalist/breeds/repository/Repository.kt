package com.example.catalist.breeds.repository

import com.example.catalist.breeds.domain.Breed
import com.example.catalist.networking.BreedApiModel
import com.example.catalist.networking.BreedsApi
import com.example.catalist.networking.retrofit
import kotlinx.coroutines.flow.MutableStateFlow


object Repository {

    private val breedsApi: BreedsApi = retrofit.create(BreedsApi::class.java)
    private val breeds = MutableStateFlow(listOf<Breed>())

    suspend fun fetchAllBreeds(): List<BreedApiModel> {
        val fetchedBreeds = breedsApi.getAllBreeds()
        breeds.value = fetchedBreeds.map { it.asBreed() }
        println("Fetched breeds: $fetchedBreeds")
        return fetchedBreeds
    }

    fun fetchBreedDetails(breedId: String): Breed? {
        val breed = breeds.value.find { it.id == breedId }
        if (breed != null) {
            println("Found breed: $breed")
        } else {
            println("No breed found with ID: $breedId")
        }
        return breed
    }

}