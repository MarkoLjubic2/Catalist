package com.example.catalist.breeds.list

import com.example.catalist.breeds.domain.Breed

data class BreedListState(
    val fetching: Boolean = false,
    val breeds: List<Breed> = emptyList(),
    val error: ListError? = null
)   {
    sealed class ListError {
        data class ListUpdateFailed(val cause: Throwable? = null) : ListError()
    }

}
