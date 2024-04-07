package com.example.catalist.breeds.repository

import com.example.catalist.breeds.domain.Breed
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlin.time.Duration.Companion.seconds

object Repository {

    private val breeds = MutableStateFlow(listOf<Breed>())

    fun allBreeds(): List<Breed> = breeds.value

    suspend fun fetchBreeds() {
        delay(2.seconds)
        breeds.update { Data.toMutableList() }
    }

    suspend fun fetchBreedDetails(breedId: String) {
        delay(1.seconds)
    }

    fun observeBreeds(): Flow<List<Breed>> = breeds.asStateFlow()

    fun observeBreedDetails(breedId: String): Flow<Breed?> {
        return observeBreeds()
            .map { breeds -> breeds.find { it.id == breedId } }
            .distinctUntilChanged()
    }

}