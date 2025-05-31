package com.example.pcteez.ui.home.members

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.pcteez.databinding.FragmentMembersBinding

class MembersFragment : Fragment() {
    private val membersViewModel: MembersViewModel by viewModels()
    private lateinit var binding: FragmentMembersBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentMembersBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.membersViewModel = membersViewModel

        return binding.root

    }
}