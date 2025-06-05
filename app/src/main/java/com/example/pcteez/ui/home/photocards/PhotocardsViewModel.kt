package com.example.pcteez.ui.home.photocards

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pcteez.database.PhotocardRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PhotocardsViewModel : ViewModel() {
    private val repository = PhotocardRepository()

    private val _photocards = MutableStateFlow<List<Photocard>>(emptyList())
    val photocards: StateFlow<List<Photocard>> = _photocards

    fun loadPhotocards(albumId: String?, memberCode: String?) {
        viewModelScope.launch {
            val allCards = if (albumId != null) {
                repository.getPhotocardsForAlbum(albumId)
            } else {
                repository.getAllPhotocards()
            }

            val filteredCards = memberCode?.let { code ->
                allCards.filter { it.member.equals(code, ignoreCase = true) }
            } ?: allCards

            _photocards.value = filteredCards
        }
    }

    fun addToCollection(photocard: Photocard) {
        repository.addToCollection(photocard)
    }

    fun addToWishlist(photocard: Photocard) {
        repository.toggleWishlist(photocard)
    }
}
