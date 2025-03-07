package com.example.catalist.breeds.domain

data class Breed (
    val id: String,
    val name: String,
    val altName: String,
    val description: String,
    val temperament: List<String>,
    val origin: String,
    val lifeSpan: String,
    val intelligence: Int,
    val adaptability: Int,
    val socialNeeds: Int,
    val energyLevel: Int,
    val strangerFriendly: Int,
    val weight: String,
    val rare: Int,
    val wikipediaUrl: String,
    val imageUrl: String = ""
)
