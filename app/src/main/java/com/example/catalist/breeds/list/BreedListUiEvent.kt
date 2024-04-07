package com.example.catalist.breeds.list

sealed class BreedListUiEvent {
    data class FindBreed(val text: String) : BreedListUiEvent()
}