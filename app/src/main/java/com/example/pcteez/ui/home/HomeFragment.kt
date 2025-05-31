package com.example.pcteez.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pcteez.R
import com.example.pcteez.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.homeViewModel = homeViewModel


        val albumAdapter = AlbumAdapter(homeViewModel.getAlbums()) { _ ->
            findNavController().navigate(R.id.action_homeFragment_to_membersFragment)
        }

        binding.albumRecyclerView.layoutManager = GridLayoutManager(context, 2)
        binding.albumRecyclerView.adapter = albumAdapter





        return binding.root

    }
}