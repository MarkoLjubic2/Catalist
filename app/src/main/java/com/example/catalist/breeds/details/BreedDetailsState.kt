package com.example.catalist.breeds.details

import com.example.catalist.breeds.domain.Breed

data class BreedDetailsState(
    val breedId: String,
    val fetching: Boolean = false,
    val data: Breed? = null,
    val error: DetailsError? = null,
) {
    sealed class DetailsError {
        data class DataUpdateFailed(val cause: Throwable? = null) : DetailsError()
    }
}
