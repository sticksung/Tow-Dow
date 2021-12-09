package com.example.towdow

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class ForumCreateFragment : Fragment() {

    //private var _binding: ForumCreateFragmentBinding? =null
    //private val binding get() =_binding!!
    private lateinit var database: DatabaseReference
    private val model: ForumsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.forum_create_fragment, container, false)

        database = Firebase.database.reference


        view.findViewById<Button>(R.id.create_forum).setOnClickListener {
            if (view.findViewById<TextView>(R.id.post_title_enter).text.isEmpty()) {
                view.findViewById<TextView>(R.id.post_title_enter).error = "Please enter name"
                view.findViewById<TextView>(R.id.post_title_enter).requestFocus()
            }
            else if (view.findViewById<TextView>(R.id.post_description_enter).text.isEmpty()) {
                view.findViewById<TextView>(R.id.post_description_enter).error = "Please enter description"
                view.findViewById<TextView>(R.id.post_description_enter).requestFocus()
            }
            else {
                Log.d("Name", view.findViewById<TextView>(R.id.post_title_enter).text.toString());
                Log.d("Desc", view.findViewById<TextView>(R.id.post_description_enter).text.toString());
                writeNewForum(view.findViewById<TextView>(R.id.post_title_enter).text.toString(), view.findViewById<TextView>(R.id.post_description_enter).text.toString())
                view?.findNavController()?.navigate(R.id.action_forumCreateFragment_to_homeFragment)
            }
        }

        return view
    }

//    @IgnoreExtraProperties
//    data class Forum(val name: String? = null, val description: String? = null, val users: ArrayList<String>) {
//        // Null default values create a no-argument default constructor, which is needed
//        // for deserialization from a DataSnapshot.
//    }

    private fun writeNewForum(name: String, description: String) {
        var users = ArrayList<String>()
        users.add(model.currentUserUID)
        val forum = Forum(name, description, users)

        database.child("Forums").child(name).setValue(forum)
    }
}