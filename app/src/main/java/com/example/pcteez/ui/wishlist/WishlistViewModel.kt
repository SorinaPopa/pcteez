package com.example.pcteez.ui.wishlist

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import org.json.JSONArray

class WishlistViewModel : ViewModel() {
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