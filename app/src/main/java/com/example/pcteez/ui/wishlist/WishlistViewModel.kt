package com.example.pcteez.ui.wishlist

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pcteez.database.PhotocardRepository
import com.example.pcteez.ui.home.photocards.Photocard
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.json.JSONArray

class WishlistViewModel : ViewModel() {

    private val repository = PhotocardRepository()

    private val _wishlist = MutableStateFlow<List<Photocard>>(emptyList())
    val wishlist: StateFlow<List<Photocard>> = _wishlist

    private val _collection = MutableStateFlow<List<Photocard>>(emptyList())
    val collection: StateFlow<List<Photocard>> = _collection

    fun loadData() {
        viewModelScope.launch {
            val wishlistCards = repository.getUserWishlist()
            val collectionCards = repository.getUserCollection()
            _wishlist.value = wishlistCards
            _collection.value = collectionCards
        }
    }

    fun addToCollection(photocard: Photocard) {
        viewModelScope.launch {
            repository.addToCollection(photocard)
            loadData()
        }
    }

    fun toggleWishlist(photocard: Photocard) {
        repository.toggleWishlist(photocard)
        loadData()
    }

    fun toggleCollection(photocard: Photocard) {
        repository.toggleCollection(photocard)
        loadData()
    }


    // temporary function to populate the db
    fun uploadPhotoCardsFromJson(context: Context, jsonFileName: String, albumId: String) {
        val db = FirebaseFirestore.getInstance()
        val assetManager = context.assets
        val jsonString = assetManager.open(jsonFileName).bufferedReader().use { it.readText() }

        val photoCards = JSONArray(jsonString)

        for (i in 0 until photoCards.length()) {
            val card = photoCards.getJSONObject(i)
            val pcId = "pc${card.getString("id")}"

            val data = hashMapOf(
                "id" to card.getString("id"),
                "pcTitle" to card.getString("pcTitle"),
                "albumId" to card.getString("albumId"),
                "member" to card.getString("member"),
                "imgUrl" to card.getString("imgUrl")
            )

            db.collection("photocards")
                .document(albumId)
                .collection("pcs")
                .document(pcId)
                .set(data)
                .addOnSuccessListener {
                    Log.d("Upload", "Uploaded $pcId")
                }
                .addOnFailureListener { e ->
                    Log.e("Upload", "Failed to upload $pcId", e)
                }
        }
    }

}