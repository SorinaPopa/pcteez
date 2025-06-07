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

    private val _wishlist = MutableStateFlow<List<Photocard>>(emptyList())
    val wishlist: StateFlow<List<Photocard>> = _wishlist

    fun loadData() {
        viewModelScope.launch {
            val collectionCards = repository.getUserCollection()
            val wishlistCards = repository.getUserWishlist()
            _collection.value = collectionCards
            _wishlist.value = wishlistCards
        }
    }

    fun toggleCollection(photocard: Photocard) {
        repository.toggleCollection(photocard)
        loadData()
    }

    fun addToWishlist(photocard: Photocard) {
        viewModelScope.launch {
            repository.addToWishlist(photocard)
            loadData()
        }
    }

    fun toggleWishlist(photocard: Photocard) {
        repository.toggleWishlist(photocard)
        loadData()
    }

}