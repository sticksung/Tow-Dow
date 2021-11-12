package com.example.towdow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.towdow.databinding.CardViewTowdowBinding.inflate
import com.example.towdow.databinding.HomeFragmentBinding
import com.example.towdow.databinding.ProfileFragmentBinding

class ProfileFragment : Fragment() {

    private var _binding: ProfileFragmentBinding? =null
    private val binding get() =_binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = ProfileFragmentBinding.inflate(inflater,container, false)
        val v = binding.root


       // binding.myUsernameText.text = "John Doe"
        binding.shortDescription.text = "Hi! My name is John Doe and I'm a sophomore at Virginia Tech. I'm a computer science who loves Android Development!"
       // binding.myProfileImage.setImageResource(R.drawable.img)
        return v
    }
}