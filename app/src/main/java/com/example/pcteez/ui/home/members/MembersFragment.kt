package com.example.pcteez.ui.home.members

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.pcteez.R
import com.example.pcteez.databinding.FragmentMembersBinding
import com.example.pcteez.ui.home.SharedViewModel

class MembersFragment : Fragment() {
    private val membersViewModel: MembersViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()

    private lateinit var binding: FragmentMembersBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentMembersBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.membersViewModel = membersViewModel

        allButtonListener()
        hongjoongButtonListener()
        seonghwaButtonListener()
        yunhoButtonListener()
        yeosangButtonListener()
        sanButtonListener()
        mingiButtonListener()
        wooyoungButtonListener()
        jonghoButtonListener()

        return binding.root

    }

    private fun allButtonListener() {
        membersViewModel.onAllButtonClicked.observe(viewLifecycleOwner) { isClicked ->
            if (isClicked) {
                sharedViewModel.selectMember(null)
                findNavController().navigate(R.id.action_membersFragment_to_photocardsFragment)
                membersViewModel.onAllButtonClicked.value = false
            }

        }
    }

    private fun hongjoongButtonListener() {
        membersViewModel.onHongjoongButtonClicked.observe(viewLifecycleOwner) { isClicked ->
            if (isClicked) {
                sharedViewModel.selectMember("hj")
                findNavController().navigate(R.id.action_membersFragment_to_photocardsFragment)
                membersViewModel.onHongjoongButtonClicked.value = false
            }

        }
    }

    private fun seonghwaButtonListener() {
        membersViewModel.onSeonghwaButtonClicked.observe(viewLifecycleOwner) { isClicked ->
            if (isClicked) {
                sharedViewModel.selectMember("sh")
                findNavController().navigate(R.id.action_membersFragment_to_photocardsFragment)
                membersViewModel.onSeonghwaButtonClicked.value = false
            }

        }
    }

    private fun yunhoButtonListener() {
        membersViewModel.onYunhoButtonClicked.observe(viewLifecycleOwner) { isClicked ->
            if (isClicked) {
                sharedViewModel.selectMember("yh")
                findNavController().navigate(R.id.action_membersFragment_to_photocardsFragment)
                membersViewModel.onYunhoButtonClicked.value = false
            }

        }
    }

    private fun yeosangButtonListener() {
        membersViewModel.onYeosangButtonClicked.observe(viewLifecycleOwner) { isClicked ->
            if (isClicked) {
                sharedViewModel.selectMember("ys")
                findNavController().navigate(R.id.action_membersFragment_to_photocardsFragment)
                membersViewModel.onYeosangButtonClicked.value = false
            }

        }
    }

    private fun sanButtonListener() {
        membersViewModel.onSanButtonClicked.observe(viewLifecycleOwner) { isClicked ->
            if (isClicked) {
                sharedViewModel.selectMember("sn")
                findNavController().navigate(R.id.action_membersFragment_to_photocardsFragment)
                membersViewModel.onSanButtonClicked.value = false
            }

        }
    }

    private fun mingiButtonListener() {
        membersViewModel.onMingiButtonClicked.observe(viewLifecycleOwner) { isClicked ->
            if (isClicked) {
                sharedViewModel.selectMember("mg")
                findNavController().navigate(R.id.action_membersFragment_to_photocardsFragment)
                membersViewModel.onMingiButtonClicked.value = false
            }

        }
    }

    private fun wooyoungButtonListener() {
        membersViewModel.onWooyoungButtonClicked.observe(viewLifecycleOwner) { isClicked ->
            if (isClicked) {
                sharedViewModel.selectMember("wy")
                findNavController().navigate(R.id.action_membersFragment_to_photocardsFragment)
                membersViewModel.onWooyoungButtonClicked.value = false
            }

        }
    }

    private fun jonghoButtonListener() {
        membersViewModel.onJonghoButtonClicked.observe(viewLifecycleOwner) { isClicked ->
            if (isClicked) {
                sharedViewModel.selectMember("jh")
                findNavController().navigate(R.id.action_membersFragment_to_photocardsFragment)
                membersViewModel.onJonghoButtonClicked.value = false
            }

        }
    }
}