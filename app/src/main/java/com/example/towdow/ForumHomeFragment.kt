package com.example.towdow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.towdow.databinding.HomeFragmentBinding

class ForumHomeFragment : Fragment() {

    private var _binding: HomeFragmentBinding? =null
    private val binding get() =_binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.forum_home_fragment, container, false)

        binding.addCategoryImage.setImageResource(R.drawable.plus);
        binding.addCategoryImage.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_forumHomeFragment_to_createCategoryFragment)
        }

        return view
    }
}