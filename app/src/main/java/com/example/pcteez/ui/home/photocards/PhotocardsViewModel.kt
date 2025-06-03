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

    fun loadAllPhotocards() {
        viewModelScope.launch {
            val all = repository.getAllPhotocards()
            _photocards.value = all
        }
    }
}
