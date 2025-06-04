package com.example.pcteez.database

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class CardCollectionRepository {

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private fun getUserId(): String = auth.currentUser?.uid.orEmpty()

    fun addCardToCollection(cardId: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val userId = getUserId()
        val cardData = mapOf("cardId" to cardId)

        db.collection("users").document(userId)
            .collection("collection").document(cardId)
            .set(cardData)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e -> onFailure(e) }
    }

    fun addCardToWishlist(cardId: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val userId = getUserId()
        val cardData = mapOf("cardId" to cardId)

        db.collection("users").document(userId)
            .collection("wishlist").document(cardId)
            .set(cardData)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e -> onFailure(e) }
    }

    fun removeCardFromWishlist(cardId: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val userId = getUserId()

        db.collection("users").document(userId)
            .collection("wishlist").document(cardId)
            .delete()
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e -> onFailure(e) }
    }

    fun fetchUserCollection(
        onSuccess: (List<String>) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val userId = getUserId()
        db.collection("users").document(userId).collection("collection")
            .get()
            .addOnSuccessListener { querySnapshot ->
                val cards = querySnapshot.documents.mapNotNull { it.id }
                onSuccess(cards)
            }
            .addOnFailureListener { onFailure(it) }
    }

    fun fetchUserWishlist(
        onSuccess: (List<String>) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val userId = getUserId()
        db.collection("users").document(userId).collection("wishlist")
            .get()
            .addOnSuccessListener { querySnapshot ->
                val cards = querySnapshot.documents.mapNotNull { it.id }
                onSuccess(cards)
            }
            .addOnFailureListener { onFailure(it) }
    }
}
