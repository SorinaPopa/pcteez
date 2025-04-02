package com.example.pcteez.ui.wishlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.pcteez.databinding.FragmentWishlistBinding

class WishlistFragment : Fragment() {

    private val wishlistViewModel: WishlistViewModel by viewModels()
    private lateinit var binding: FragmentWishlistBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentWishlistBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.wishlistViewModel = wishlistViewModel

        return binding.root

    }

}