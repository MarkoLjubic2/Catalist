package com.example.catalist.breeds.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catalist.breeds.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import com.example.catalist.breeds.list.BreedListContract.BreedListUiEvent
import com.example.catalist.breeds.list.BreedListContract.BreedListState

@OptIn(FlowPreview::class)
class BreedListViewModel (
    private val repository: Repository = Repository
) : ViewModel() {

    private val _state = MutableStateFlow(BreedListState())
    val state = _state.asStateFlow()
    private fun setState(reducer: BreedListState.() -> BreedListState) =
        _state.getAndUpdate(reducer)

    private val events = MutableSharedFlow<BreedListUiEvent>()
    fun setEvent(event: BreedListUiEvent) {
        viewModelScope.launch {
            events.emit(event)
            if (event is BreedListUiEvent.FindBreed) {
                searchQuery.emit(event.text)
            }
        }
    }

    private val searchQuery = MutableSharedFlow<String>(replay = 1)

    init {
        observeBreeds()
        fetchBreeds()
        setupSearch()
    }

    private fun setupSearch() {
        viewModelScope.launch {
            searchQuery
                .debounce(500)
                .collect { query ->
                    filterBreeds(query)
                }
        }
    }

    private fun observeBreeds() {
        viewModelScope.launch {
            repository.observeBreeds().collect {
                setState { copy(breeds = it) }
                filterBreeds("")
            }
        }
    }

    private fun fetchBreeds() {
        viewModelScope.launch {
            setState { copy(fetching = true) }
            try {
                withContext(Dispatchers.IO) {
                    repository.fetchBreeds()
                }
            } catch (error: IOException) {
                setState { copy(error = BreedListState.ListError.ListUpdateFailed(cause = error)) }
            } finally {
                setState { copy(fetching = false) }
            }
        }
    }


    private fun filterBreeds(searchText: String) {
        val filteredBreeds = if (searchText.isEmpty()) {
            _state.value.breeds
        } else {
            _state.value.breeds.filter { it.name.startsWith(searchText, ignoreCase = true) }
        }
        _state.compareAndSet(_state.value, _state.value.copy(filteredBreeds = filteredBreeds, searchQuery = searchText))
    }
}