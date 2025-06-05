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
            if (action == PhotocardAdapter.ActionType.COLLECTION) {
                // Optional: remove from wishlist on click
                collectionViewModel.loadCollection()
            }
        }

        binding.collectionRecyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.collectionRecyclerView.adapter = adapter

        collectionViewModel.loadCollection()

        lifecycleScope.launch {
            collectionViewModel.collection.collectLatest { cards ->
                adapter.updateData(cards)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        collectionViewModel.loadCollection()
    }

}