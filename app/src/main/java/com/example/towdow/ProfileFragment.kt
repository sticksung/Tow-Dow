package com.example.towdow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import com.example.towdow.databinding.CardViewTowdowBinding.inflate
import com.example.towdow.databinding.HomeFragmentBinding
import com.example.towdow.databinding.ProfileFragmentBinding
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class ProfileFragment : Fragment() {

    private var _binding: ProfileFragmentBinding? =null
    private val binding get() =_binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = ProfileFragmentBinding.inflate(inflater,container, false)
        val v = binding.root

        val user = Firebase.auth.currentUser
        if (user != null) {
            binding.tvName.text = user.email
        }

        binding.signoutButton2.setOnClickListener {
            Firebase.auth.signOut()
            v.findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
        }

        binding.homeHomeButton4.setOnClickListener{
            v.findNavController().navigate(R.id.action_profileFragment_to_homeFragment)
        }

        binding.homeSearchBotton4.setOnClickListener{
            v.findNavController().navigate(R.id.action_profileFragment_to_searchFragment)
        }
       // binding.myUsernameText.text = "John Doe"
        v.findViewById<TextView>(R.id.reply_text).text = "Hi! My name is John Doe and I'm a sophomore at Virginia Tech. I'm a computer science who loves Android Development!"
       // binding.myProfileImage.setImageResource(R.drawable.img)
        return v
    }
}