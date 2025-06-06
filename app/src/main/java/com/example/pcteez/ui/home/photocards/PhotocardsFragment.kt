package com.example.pcteez.ui.home.photocards

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pcteez.databinding.FragmentPhotocardsBinding
import com.example.pcteez.ui.home.SharedViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PhotocardsFragment : Fragment() {
    private lateinit var binding: FragmentPhotocardsBinding
    private lateinit var adapter: PhotocardAdapter

    private val photocardsViewModel: PhotocardsViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPhotocardsBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = PhotocardAdapter(emptyList()) { photocard, action ->
            when (action) {
                PhotocardAdapter.ActionType.COLLECTION -> {
                    photocardsViewModel.addToCollection(photocard)
                }

                PhotocardAdapter.ActionType.WISHLIST -> {
                    photocardsViewModel.addToWishlist(photocard)
                }
            }
        }

        binding.photocardRecyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.photocardRecyclerView.adapter = adapter

        // Only one job to handle both album and member selection changes
        lifecycleScope.launch {
            sharedViewModel.selectedAlbumId.collectLatest { albumId ->
                sharedViewModel.selectedMember.collectLatest { memberCode ->
                    photocardsViewModel.loadPhotocards(albumId, memberCode)
                }
            }
        }

        lifecycleScope.launch {
            photocardsViewModel.photocards.collectLatest { cards ->
                adapter.updateData(cards)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        sharedViewModel.selectedAlbumId.value?.let { albumId ->
            sharedViewModel.selectedMember.value?.let { memberCode ->
                photocardsViewModel.loadPhotocards(albumId, memberCode)
            }
        }
    }


}
