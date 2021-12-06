package com.example.towdow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController


class ReplyFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.reply_fragment, container, false)

        view.findViewById<Button>(R.id.finish_reply_button).setOnClickListener {
            view.findNavController().navigate(R.id.action_replyFragment_to_forumPostFragment)
        }

        return view
    }

}