package com.example.towdow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.towdow.databinding.SearchFragmentBinding
import com.example.towdow.databinding.SignupFragmentBinding

import com.google.android.material.bottomnavigation.BottomNavigationView




class SearchFragment : Fragment() {

    private var _binding: SearchFragmentBinding? =null
    private val binding get() =_binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = SearchFragmentBinding.inflate(inflater,container, false)
        val v = binding.root

        binding.searchImageButton.setImageResource(R.drawable.search1)


        return v
    }
}