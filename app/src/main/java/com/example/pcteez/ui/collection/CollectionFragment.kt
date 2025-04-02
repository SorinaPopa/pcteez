package com.example.pcteez.ui.collection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.pcteez.databinding.FragmentCollectionBinding

class CollectionFragment : Fragment() {

    private val collectionViewModel: CollectionViewModel by viewModels()
    private lateinit var binding: FragmentCollectionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentCollectionBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.collectionViewModel = collectionViewModel

        return binding.root

    }
}