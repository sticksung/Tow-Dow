package com.example.towdow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.towdow.databinding.HomeFragmentBinding
import com.example.towdow.databinding.SignupFragmentBinding

import com.google.android.material.bottomnavigation.BottomNavigationView




class HomeFragment : Fragment() {

    private var _binding: HomeFragmentBinding? =null
    private val binding get() =_binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = HomeFragmentBinding.inflate(inflater,container, false)
        val v = binding.root


        val bottomNavigationView: BottomNavigationView = requireActivity().findViewById(com.example.towdow.R.id.bottomNavigationView)
        bottomNavigationView.visibility = View.VISIBLE

        binding.fab.setImageResource(R.drawable.plus)
     //   binding.homeImage.setImageResource(R.drawable.home)
      //  binding.searchImage.setImageResource(R.drawable.search)
      //  binding.profileImage.setImageResource(R.drawable.profile)
        return v
    }
}