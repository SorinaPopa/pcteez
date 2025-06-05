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
        //wishlistViewModel.uploadPhotoCardsFromJson(requireContext(), "1_tr1_pcs.json", "treasure1")

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = PhotocardAdapter(emptyList()) { photocard, action ->
            if (action == PhotocardAdapter.ActionType.WISHLIST) {
                // Optional: remove from wishlist on click
                wishlistViewModel.loadWishlist()
            }
        }

        binding.wishlistRecyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.wishlistRecyclerView.adapter = adapter

        wishlistViewModel.loadWishlist()

        lifecycleScope.launch {
            wishlistViewModel.wishlist.collectLatest { cards ->
                adapter.updateData(cards)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        wishlistViewModel.loadWishlist()
    }


}