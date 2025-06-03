package com.example.pcteez.ui.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class SharedViewModel : ViewModel() {
    private val _selectedAlbumId = MutableStateFlow<String?>(null)
    val selectedAlbumId = _selectedAlbumId

    private val _selectedMember = MutableStateFlow<String?>(null)
    val selectedMember = _selectedMember

    fun selectAlbum(id: String) {
        _selectedAlbumId.value = id
    }

    fun selectMember(name: String?) {
        _selectedMember.value = name
    }
}

