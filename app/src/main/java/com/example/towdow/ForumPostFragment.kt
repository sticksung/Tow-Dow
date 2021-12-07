package com.example.towdow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import com.example.towdow.databinding.HomeFragmentBinding

class ForumPostFragment : Fragment() {

    private var _binding: ForumPostFragment? =null
    private val binding get() =_binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.forum_post_fragment, container, false)

        view.findViewById<Button>(R.id.reply_button).setOnClickListener {
            view.findNavController().navigate(R.id.action_forumPostFragment_to_replyFragment)
        }

        return view
    }
}