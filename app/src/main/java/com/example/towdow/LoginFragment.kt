package com.example.towdow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.towdow.databinding.LoginFragmentBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class LoginFragment : Fragment() {
    private var _binding: LoginFragmentBinding? =null
    private val binding get() =_binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = LoginFragmentBinding.inflate(inflater,container, false)
        val v = binding.root

        val bottomNavigationView: BottomNavigationView = requireActivity().findViewById(com.example.towdow.R.id.bottomNavigationView)
        bottomNavigationView.visibility = View.GONE

        binding.towDowImage.setImageResource(R.drawable.logo)
        binding.signinButton.setOnClickListener{
            view?.findNavController()?.navigate(R.id.action_loginFragment_to_homeFragment)
        }

        binding.signupButton.setOnClickListener{
            view?.findNavController()?.navigate(R.id.action_loginFragment_to_signupFragment)
        }
        auth = Firebase.auth

        return v
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(currentUser: FirebaseUser?) {

    }

}

