package com.example.pcteez.ui.collection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pcteez.databinding.FragmentCollectionBinding
import com.example.pcteez.ui.home.photocards.PhotocardAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CollectionFragment : Fragment() {

    private val collectionViewModel: CollectionViewModel by viewModels()
    private lateinit var binding: FragmentCollectionBinding
    private lateinit var adapter: PhotocardAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentCollectionBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.collectionViewModel = collectionViewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = PhotocardAdapter(emptyList()) { photocard, action ->
            when (action) {
                PhotocardAdapter.ActionType.COLLECTION -> {
                    // Show confirmation if photocard is already in collection
                    val isCollected = collectionViewModel.collection.value.any { it.id == photocard.id }
                    if (isCollected) {
                        showConfirmDialog(
                            title = "Remove from Collection?",
                            message = "Are you sure you want to remove this photocard from your collection?",
                            onConfirm = {
                                collectionViewModel.toggleCollection(photocard)
                            }
                        )
                    } else {
                        collectionViewModel.toggleCollection(photocard)
                    }
                }

                PhotocardAdapter.ActionType.WISHLIST -> {
                    val isWishlisted = collectionViewModel.wishlist.value.any { it.id == photocard.id }
                    if (isWishlisted) {
                        showConfirmDialog(
                            title = "Remove from Wishlist?",
                            message = "Are you sure you want to remove this photocard from your wishlist?",
                            onConfirm = {
                                collectionViewModel.toggleWishlist(photocard)
                            }
                        )
                    } else {
                        collectionViewModel.toggleWishlist(photocard)
                    }
                }
            }
        }


        binding.collectionRecyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.collectionRecyclerView.adapter = adapter

        collectionViewModel.loadData()

        lifecycleScope.launch {
            launch {
                collectionViewModel.collection.collectLatest { collection ->
                    adapter.updateData(
                        newItems = collection,
                        newCollection = collection,
                        newWishlist = collectionViewModel.wishlist.value
                    )
                }
            }

            launch {
                collectionViewModel.wishlist.collectLatest { wishlist ->
                    adapter.updateData(
                        newItems = collectionViewModel.collection.value,
                        newCollection = collectionViewModel.collection.value,
                        newWishlist = wishlist
                    )
                }
            }
        }

    }


    override fun onResume() {
        super.onResume()
        collectionViewModel.loadData()
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