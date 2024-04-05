package com.example.catalist.model

object Repository {

    private var breeds = Data.toMutableList()

    fun allBreeds() : List<Breed> = breeds

    fun getById(id: Int) : Breed? {
        return breeds.find { it.id == id }
    }

}