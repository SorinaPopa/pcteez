package com.example.pcteez.ui.home

import androidx.lifecycle.ViewModel
import com.example.pcteez.R

class HomeViewModel : ViewModel(){
    private val albumList = listOf(
        Album(id = "treasure1", name = "TREASURE EP.1: All to Zero", coverResId = R.drawable.img_album_treasure1),
        Album(id = "treasure2", name = "TREASURE EP.2: Zero to One", coverResId = R.drawable.img_album_treasure2),
        Album(id = "treasure3", name = "TREASURE EP.3: One to All", coverResId = R.drawable.img_album_treasure3),
        Album(id = "treasure4", name = "TREASURE EP.FIN: All to Action", coverResId = R.drawable.img_album_treasure4),
        Album(id = "treasure5", name = "TREASURE EPILOGUE: Action to Answer", coverResId = R.drawable.img_album_treasure5),
        Album(id = "fever1", name = "ZERO: FEVER Part.1", coverResId = R.drawable.img_album_fever1),
        Album(id = "fever2", name = "ZERO: FEVER Part.2", coverResId = R.drawable.img_album_fever2)
    )

    fun getAlbums(): List<Album> = albumList

}