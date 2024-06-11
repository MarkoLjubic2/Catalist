package com.example.catalist.breeds.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catalist.breeds.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

class BreedDetailsViewModel(
    private val breedId: String,
    private val repository: Repository = Repository,
) : ViewModel() {

    private val _state = MutableStateFlow(BreedDetailsState(breedId = breedId))
    val state = _state.asStateFlow()
    private fun setState(reducer: BreedDetailsState.() -> BreedDetailsState) =
        _state.getAndUpdate(reducer)

    init {
        fetchBreedDetails()
    }

 private fun fetchBreedDetails() {
     viewModelScope.launch {
         setState { copy(fetching = true) }
         try {
             val breedDetails = withContext(Dispatchers.IO) {
                 repository.fetchBreedDetails(breedId = breedId)
             }
             setState { copy(data = breedDetails) }
         } catch (error: IOException) {
             setState {
                 copy(error = BreedDetailsState.DetailsError.DataUpdateFailed(cause = error))
             }
         } finally {
             setState { copy(fetching = false) }
         }
     }
 }


}