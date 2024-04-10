package com.example.catalist.breeds.domain

data class Breed (
    val id: String,
    val image: String,
    val name: String,
    val altName: String,
    val description: String,
    val temperament: List<String>,
    val origin: String,
    val lifeSpan: String,
    val weight: String,
    val intelligence: Int,
    val adaptability: Int,
    val socialNeeds: Int,
    val energyLevel: Int,
    val strangerFriendly: Int,
    val rare: Int,
    val wikipediaUrl: String,
)