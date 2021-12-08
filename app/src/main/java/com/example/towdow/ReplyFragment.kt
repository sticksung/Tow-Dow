package com.example.towdow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class ReplyFragment : Fragment() {

    private lateinit var postName: String
    private lateinit var categoryName: String
    private lateinit var forumName: String
    private lateinit var description: String
    private lateinit var reply: String

    private lateinit var database: DatabaseReference
    private val user = Firebase.auth.currentUser

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        database = Firebase.database.reference

        categoryName = this.arguments?.getString("category").toString()
        postName = this.arguments?.getString("post").toString()
        forumName = this.arguments?.getString("forum").toString()
        description = this.arguments?.getString("description").toString()

        view.findViewById<Button>(R.id.finish_reply_button).setOnClickListener {
            if (view.findViewById<TextView>(R.id.reply_enter).text.isEmpty()) {
                view.findViewById<TextView>(R.id.reply_enter).error = "Please enter name"
                view.findViewById<TextView>(R.id.reply_enter).requestFocus()
            }
            else {
                reply = view.findViewById<TextView>(R.id.reply_enter).text.toString()
                writeNewReply(reply)
                val bundle = Bundle()
                bundle.putString("post", postName)
                bundle.putString("forum", forumName)
                bundle.putString("category", categoryName)
                bundle.putString("description", description)
                view.findNavController().navigate(R.id.action_replyFragment_to_forumPostFragment, bundle)
            }
        }
    }

    private fun writeNewReply(reply: String) {
        val post = Reply(reply, user?.email)
        database.child("Forums").child(forumName).child("Categories").child(categoryName).child("Posts").child(postName).child("Replies").child(reply).setValue(post)
    }

}