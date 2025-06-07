package com.example.pcteez.ui.wishlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pcteez.databinding.FragmentWishlistBinding
import com.example.pcteez.ui.home.photocards.PhotocardAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class WishlistFragment : Fragment() {

    private val wishlistViewModel: WishlistViewModel by viewModels()
    private lateinit var binding: FragmentWishlistBinding
    private lateinit var adapter: PhotocardAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentWishlistBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.wishlistViewModel = wishlistViewModel

        //uncomment to populate the db
        //wishlistViewModel.uploadPhotoCardsFromJson(requireContext(), "7_fv2_pcs.json", "fever2")

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = PhotocardAdapter(emptyList()) { photocard, action ->
            when (action) {
                PhotocardAdapter.ActionType.COLLECTION -> {
                    val isCollected = wishlistViewModel.collection.value.any { it.id == photocard.id }
                    if (isCollected) {
                        showConfirmDialog(
                            title = "Remove from Collection?",
                            message = "Are you sure you want to remove this photocard from your collection?",
                            onConfirm = {
                                wishlistViewModel.toggleCollection(photocard)
                            }
                        )
                    } else {
                        wishlistViewModel.toggleCollection(photocard)
                    }
                }

                PhotocardAdapter.ActionType.WISHLIST -> {
                    val isWishlisted = wishlistViewModel.wishlist.value.any { it.id == photocard.id }
                    if (isWishlisted) {
                        showConfirmDialog(
                            title = "Remove from Wishlist?",
                            message = "Are you sure you want to remove this photocard from your wishlist?",
                            onConfirm = {
                                wishlistViewModel.toggleWishlist(photocard)
                            }
                        )
                    } else {
                        wishlistViewModel.toggleWishlist(photocard)
                    }
                }
            }
        }


        binding.wishlistRecyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.wishlistRecyclerView.adapter = adapter

        wishlistViewModel.loadData()

        lifecycleScope.launch {
            launch {
                wishlistViewModel.wishlist.collectLatest { wishlist ->
                    adapter.updateData(
                        newItems = wishlist,
                        newWishlist = wishlist,
                        newCollection = wishlistViewModel.collection.value
                    )
                }
            }

            launch {
                wishlistViewModel.collection.collectLatest { collection ->
                    adapter.updateData(
                        newItems = wishlistViewModel.wishlist.value,
                        newWishlist = wishlistViewModel.wishlist.value,
                        newCollection = collection
                    )
                }
            }
        }

    }


    override fun onResume() {
        super.onResume()
        wishlistViewModel.loadData()
    }

    private fun showConfirmDialog(title: String, message: String, onConfirm: () -> Unit) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .setNegativeButton("Yes") { _, _ ->
                onConfirm()
            }
            .show()
    }


}