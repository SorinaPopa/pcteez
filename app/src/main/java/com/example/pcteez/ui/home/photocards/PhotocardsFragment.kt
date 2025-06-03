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
import com.example.pcteez.ui.home.members.MembersViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PhotocardsFragment : Fragment() {
    private lateinit var binding: FragmentPhotocardsBinding
    private lateinit var adapter: PhotocardAdapter

    private val photocardsViewModel: PhotocardsViewModel by viewModels()
    private val membersViewModel: MembersViewModel by activityViewModels()

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
            // Handle add to collection or wishlist (can implement later)
        }

        binding.albumRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.albumRecyclerView.adapter = adapter

        lifecycleScope.launch {
            photocardsViewModel.photocards.collectLatest { cards ->
                adapter.updateData(cards)
            }
        }

        membersViewModel.onAllButtonClicked.observe(viewLifecycleOwner) { clicked ->
            if (clicked) {
                photocardsViewModel.loadAllPhotocards()
                membersViewModel.onAllButtonClicked.value = false
            }
        }
    }
}
