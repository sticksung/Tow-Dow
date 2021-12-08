package com.example.towdow

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController
import com.example.towdow.databinding.HomeFragmentBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class CreatePostFragment : Fragment() {

    private var _binding: HomeFragmentBinding? =null
    private val binding get() =_binding!!
    private lateinit var database: DatabaseReference
    private lateinit var forumName: String
    private lateinit var categoryName: String
    private lateinit var postName: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.create_post_fragment, container, false)

        return view
    }

    @SuppressLint("CutPasteId")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        categoryName = this.arguments?.getString("category").toString()
        forumName = this.arguments?.getString("forum").toString()

        database = Firebase.database.reference

        view.findViewById<Button>(R.id.create_post).setOnClickListener {
            if (view.findViewById<TextView>(R.id.post_title_enter).text.isEmpty()) {
                view.findViewById<TextView>(R.id.post_title_enter).error = "Please enter name"
                view.findViewById<TextView>(R.id.post_title_enter).requestFocus()
            }
            else if (view.findViewById<TextView>(R.id.post_description_enter).text.isEmpty()) {
                view.findViewById<TextView>(R.id.post_description_enter).error = "Please enter description"
                view.findViewById<TextView>(R.id.post_description_enter).requestFocus()
            }
            else {
                postName = view.findViewById<TextView>(R.id.post_title_enter).text.toString()
                println("PostName: " + postName)
                writeNewPost(view.findViewById<TextView>(R.id.post_title_enter).text.toString(), view.findViewById<TextView>(R.id.post_description_enter).text.toString())
                val bundle = Bundle()
                bundle.putString("post", view.findViewById<TextView>(R.id.post_title_enter).text.toString())
                bundle.putString("forum", forumName)
                bundle.putString("category", categoryName)
                view.findNavController().navigate(R.id.action_createPostFragment_to_categoryHomeFragment, bundle)
            }
        }
    }

    private fun writeNewPost(name: String, description: String) {
        val post = Post(name, description)

        database.child("Forums").child(forumName).child("Categories").child(categoryName).child("Posts").child(postName).setValue(post)
    }
}