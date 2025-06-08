package com.example.pcteez.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pcteez.database.PhotocardRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {
    private val repository = PhotocardRepository()

    val onLogoutButtonClicked = MutableLiveData(false)

    private val _wishlistCount = MutableStateFlow(0)
    val wishlistCount: StateFlow<Int> = _wishlistCount

    private val _collectionCount = MutableStateFlow(0)
    val collectionCount: StateFlow<Int> = _collectionCount

    init {
        loadCounts()
    }

    private fun loadCounts() {
        viewModelScope.launch {
            _wishlistCount.value = repository.getUserWishlist().size
            _collectionCount.value = repository.getUserCollection().size
        }
    }

    fun onClickLogoutButton() {
        onLogoutButtonClicked.value = true
    }
}