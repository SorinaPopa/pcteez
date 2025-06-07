package com.example.pcteez.database

import android.util.Log
import com.example.pcteez.ui.home.photocards.Photocard
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class PhotocardRepository {

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    fun getCurrentUserId(): String? = auth.currentUser?.uid

    suspend fun getAllPhotocards(): List<Photocard> {
        val snapshot = db.collection("photocards").get().await()
        val allCards = mutableListOf<Photocard>()

        for (albumDoc in snapshot.documents) {
            val pcsSnapshot = albumDoc.reference.collection("pcs").get().await()
            val cards = pcsSnapshot.documents.mapNotNull { it.toObject(Photocard::class.java) }
            allCards.addAll(cards)
        }
        return allCards
    }

    suspend fun getPhotocardsForAlbum(albumId: String): List<Photocard> {
        val albumDocRef = db.collection("photocards").document(albumId)
        val pcsSnapshot = albumDocRef.collection("pcs").get().await()
        return pcsSnapshot.documents.mapNotNull { it.toObject(Photocard::class.java) }
    }

    fun addToCollection(photocard: Photocard) {
        val userId = getCurrentUserId() ?: return
        db.collection("users").document(userId)
            .collection("collection")
            .document(photocard.id)
            .set(photocard)
            .addOnSuccessListener {
                Log.d("PhotocardRepository", "Added to collection: ${photocard.id}")
            }
            .addOnFailureListener {
                Log.e("PhotocardRepository", "Failed to add to collection: ${it.message}")
            }
    }

    fun addToWishlist(photocard: Photocard) {
        val userId = getCurrentUserId() ?: return
        db.collection("users").document(userId)
            .collection("wishlist")
            .document(photocard.id)
            .set(photocard)
            .addOnSuccessListener {
                Log.d("PhotocardRepository", "Added to collection: ${photocard.id}")
            }
            .addOnFailureListener {
                Log.e("PhotocardRepository", "Failed to add to collection: ${it.message}")
            }
    }

    fun toggleWishlist(photocard: Photocard) {
        val userId = getCurrentUserId() ?: return
        val docRef = db.collection("users").document(userId)
            .collection("wishlist")
            .document(photocard.id)

        docRef.get()
            .addOnSuccessListener { snapshot ->
                if (snapshot.exists()) {
                    docRef.delete()
                        .addOnSuccessListener {
                            Log.d("PhotocardRepository", "Removed from wishlist: ${photocard.id}")
                        }
                } else {
                    docRef.set(photocard)
                        .addOnSuccessListener {
                            Log.d("PhotocardRepository", "Added to wishlist: ${photocard.id}")
                        }
                }
            }
            .addOnFailureListener {
                Log.e("PhotocardRepository", "Error toggling wishlist: ${it.message}")
            }
    }

    fun toggleCollection(photocard: Photocard) {
        val userId = getCurrentUserId() ?: return
        val docRef = db.collection("users").document(userId)
            .collection("collection")
            .document(photocard.id)

        docRef.get()
            .addOnSuccessListener { snapshot ->
                if (snapshot.exists()) {
                    docRef.delete()
                        .addOnSuccessListener {
                            Log.d("PhotocardRepository", "Removed from collection: ${photocard.id}")
                        }
                } else {
                    docRef.set(photocard)
                        .addOnSuccessListener {
                            Log.d("PhotocardRepository", "Added to collection: ${photocard.id}")
                        }
                }
            }
            .addOnFailureListener {
                Log.e("PhotocardRepository", "Error toggling collection: ${it.message}")
            }
    }

    suspend fun getUserWishlist(): List<Photocard> {
        val userId = getCurrentUserId() ?: return emptyList()
        val snapshot = db.collection("users")
            .document(userId)
            .collection("wishlist")
            .get()
            .await()

        return snapshot.documents.mapNotNull { it.toObject(Photocard::class.java) }
    }

    suspend fun getUserCollection(): List<Photocard> {
        val userId = getCurrentUserId() ?: return emptyList()
        val snapshot = db.collection("users")
            .document(userId)
            .collection("collection")
            .get()
            .await()

        return snapshot.documents.mapNotNull { it.toObject(Photocard::class.java) }
    }


}
