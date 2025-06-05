package com.example.pcteez.ui.collection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pcteez.database.PhotocardRepository
import com.example.pcteez.ui.home.photocards.Photocard
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CollectionViewModel : ViewModel() {
    private val repository = PhotocardRepository()

    private val _collection = MutableStateFlow<List<Photocard>>(emptyList())
    val collection: StateFlow<List<Photocard>> = _collection

    fun loadCollection() {
        viewModelScope.launch {
            val cards = repository.getUserCollection()
            _collection.value = cards
        }
    }
}