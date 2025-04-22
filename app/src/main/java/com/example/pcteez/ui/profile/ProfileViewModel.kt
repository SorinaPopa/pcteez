package com.example.pcteez.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel() {
    val onLogoutButtonClicked = MutableLiveData(false)

    fun onClickLogoutButton() {
        onLogoutButtonClicked.value = true
    }
}