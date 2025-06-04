package com.example.pcteez.ui.home.photocards

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pcteez.database.PhotocardRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
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
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val userDocRef = FirebaseFirestore.getInstance()
            .collection("users").document(userId)
            .collection("collection")
            .document(photocard.id)

        userDocRef.set(photocard)
            .addOnSuccessListener {
                Log.d("Photocard", "Added to collection: ${photocard.id}")
            }
            .addOnFailureListener {
                Log.e("Photocard", "Failed to add: ${it.message}")
            }
    }

    fun addToWishlist(photocard: Photocard) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val favDocRef = FirebaseFirestore.getInstance()
            .collection("users").document(userId)
            .collection("wishlist")
            .document(photocard.id)

        favDocRef.get()
            .addOnSuccessListener { doc ->
                if (doc.exists()) {
                    favDocRef.delete()
                } else {
                    favDocRef.set(photocard)
                }
            }
    }


}
