package com.example.pcteez.ui.home.members

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MembersViewModel : ViewModel() {
    val onAllButtonClicked = MutableLiveData(false)
    val onHongjoongButtonClicked = MutableLiveData(false)
    val onSeonghwaButtonClicked = MutableLiveData(false)
    val onYunhoButtonClicked = MutableLiveData(false)
    val onYeosangButtonClicked = MutableLiveData(false)
    val onSanButtonClicked = MutableLiveData(false)
    val onMingiButtonClicked = MutableLiveData(false)
    val onWooyoungButtonClicked = MutableLiveData(false)
    val onJonghoButtonClicked = MutableLiveData(false)

    fun onAllButton() {
        onAllButtonClicked.value = true
    }

    fun onHongjoongButton() {
        onHongjoongButtonClicked.value = true
    }

    fun onSeonghwaButton() {
        onSeonghwaButtonClicked.value = true
    }

    fun onYunhoButton() {
        onYunhoButtonClicked.value = true
    }

    fun onYeosangButton() {
        onYeosangButtonClicked.value = true
    }

    fun onSanButton() {
        onSanButtonClicked.value = true
    }

    fun onMingiButton() {
        onMingiButtonClicked.value = true
    }

    fun onWooyoungButton() {
        onWooyoungButtonClicked.value = true
    }

    fun onJonghoButton() {
        onJonghoButtonClicked.value = true
    }




}