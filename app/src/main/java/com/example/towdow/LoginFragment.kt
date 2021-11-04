package com.example.towdow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.towdow.databinding.LoginFragmentBinding


class LoginFragment : Fragment() {
    private var _binding: LoginFragmentBinding? =null
    private val binding get() =_binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = LoginFragmentBinding.inflate(inflater,container, false)
        val v = binding.root


        binding.towDowImage.setImageResource(R.drawable.logo)
        binding.signinButton.setOnClickListener{
            view?.findNavController()?.navigate(R.id.action_loginFragment_to_homeFragment)
        }

        binding.signupButton.setOnClickListener{
            view?.findNavController()?.navigate(R.id.action_loginFragment_to_signupFragment)
        }
        return v
    }


}

