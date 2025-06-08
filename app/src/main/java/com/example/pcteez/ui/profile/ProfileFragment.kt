package com.example.pcteez.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.pcteez.databinding.FragmentProfileBinding
import com.example.pcteez.ui.login.LoginActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class ProfileFragment : Fragment() {

    private val profileViewModel: ProfileViewModel by viewModels()
    private lateinit var binding: FragmentProfileBinding
    private lateinit var moodUpAuth: FirebaseAuth
    private lateinit var currentUser: FirebaseUser

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentProfileBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.profileViewModel = profileViewModel

        moodUpAuth = FirebaseAuth.getInstance()
        currentUser = moodUpAuth.currentUser!!

        // TODO: implement a finding friends function
        binding.findFriendsButton.visibility = View.GONE

        logoutButtonObserver()

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            profileViewModel.wishlistCount.collect { count ->
                binding.wishlistNumberText.text = "⭐: $count"
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            profileViewModel.collectionCount.collect { count ->
                binding.collectionNumberText.text = "➕: $count"
            }
        }
    }


    private fun logoutButtonObserver() {
        profileViewModel.onLogoutButtonClicked.observe(viewLifecycleOwner) { isClicked ->
            if (isClicked) {
                showLogoutDialog()
            }
        }
    }

    private fun showLogoutDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Log Out?")
            .setMessage("Are you sure you want log out from your account?")
            .setPositiveButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            .setNegativeButton("Yes") { _, _ ->
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(activity, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                activity?.finish()
                profileViewModel.onLogoutButtonClicked.value = false
            }
            .show()
    }

}