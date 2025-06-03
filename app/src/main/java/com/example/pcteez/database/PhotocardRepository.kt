package com.example.pcteez.database

import com.example.pcteez.ui.home.photocards.Photocard
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class PhotocardRepository {

    private val db = FirebaseFirestore.getInstance()

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

}
