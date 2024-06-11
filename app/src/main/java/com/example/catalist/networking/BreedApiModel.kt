package com.example.catalist.networking

import com.example.catalist.breeds.domain.Breed
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BreedApiModel(
    val id: String,
    val name: String,
    @SerialName("alt_names") val altName: String = "",
    val description: String,
    val temperament: String,
    val origin: String,
    @SerialName("life_span") val lifeSpan: String,
    val intelligence: Int,
    val adaptability: Int,
    @SerialName("social_needs") val socialNeeds: Int,
    @SerialName("energy_level") val energyLevel: Int,
    @SerialName("stranger_friendly") val strangerFriendly: Int,
    val weight: Weight,
    val rare: Int,
    @SerialName("wikipedia_url") val wikipediaUrl: String? = null,
    val image: Image? = null
){
    fun asBreed() = Breed(
        id = this.id,
        name = this.name,
        altName = this.altName,
        description = this.description,
        temperament = this.temperament.split(", "),
        origin = this.origin,
        lifeSpan = this.lifeSpan,
        intelligence = this.intelligence,
        adaptability = this.adaptability,
        socialNeeds = this.socialNeeds,
        energyLevel = this.energyLevel,
        strangerFriendly = this.strangerFriendly,
        weight = this.weight.metric,
        rare = this.rare,
        wikipediaUrl = this.wikipediaUrl ?: "",
        imageUrl = this.image?.url ?: ""
    ).also {
        println("Mapped BreedApiModel to Breed: $it")
    }

    @Serializable
    data class Image(
        val id: String,
        val width: Int,
        val height: Int,
        val url: String
    )

    @Serializable
    data class Weight(
        val imperial: String,
        val metric: String
    )

}
