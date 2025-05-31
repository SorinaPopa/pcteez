package com.example.pcteez.ui.home

import androidx.lifecycle.ViewModel
import com.example.pcteez.R
import com.example.pcteez.ui.home.business.Album

class HomeViewModel : ViewModel(){
    private val albumList = listOf(
        Album(id = "1", name = "TREASURE EP.1: All to Zero", coverResId = R.drawable.img_album_treasure1),
        Album(id = "2", name = "TREASURE EP.2: Zero to One", coverResId = R.drawable.img_album_treasure2),
        Album(id = "3", name = "TREASURE EP.3: One to All", coverResId = R.drawable.img_album_treasure3),
        Album(id = "4", name = "TREASURE EP.FIN: All to Action", coverResId = R.drawable.img_album_treasure4)
    )

    fun getAlbums(): List<Album> = albumList

}