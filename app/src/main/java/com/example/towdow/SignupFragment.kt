package com.example.towdow

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import com.example.towdow.databinding.SignupFragmentBinding
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView


class SignupFragment : Fragment() {
    private var _binding: SignupFragmentBinding? =null
    private val binding get() =_binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = SignupFragmentBinding.inflate(inflater,container, false)
        val v = binding.root
        binding.submitButton.setOnClickListener{
            view?.findNavController()?.navigate(R.id.action_signupFragment_to_homeFragment)
        }

        val bottomNavigationView: BottomNavigationView = requireActivity().findViewById(com.example.towdow.R.id.bottomNavigationView)
        bottomNavigationView.visibility = View.GONE
        return v
    }
}