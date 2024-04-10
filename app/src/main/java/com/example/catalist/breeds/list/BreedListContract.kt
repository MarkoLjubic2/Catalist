package com.example.catalist.breeds.list

import com.example.catalist.breeds.domain.Breed

interface BreedListContract {

    data class BreedListState(
        val fetching: Boolean = false,
        val breeds: List<Breed> = emptyList(),
        val filteredBreeds: List<Breed> = emptyList(),
        val searchQuery: String = "",
        val error: ListError? = null
    ) {
        sealed class ListError {
            data class ListUpdateFailed(val cause: Throwable? = null) : ListError()
        }
    }

    sealed class BreedListUiEvent {
        data class FindBreed(val text: String) : BreedListUiEvent()
    }

}