package com.example.towdow

import android.os.Bundle
import android.util.Log
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

class CreateCategoryFragment : Fragment() {

    private var _binding: CreateCategoryFragment? =null
    private val binding get() =_binding!!
    private lateinit var forumName: String
    private lateinit var categoryName: String
    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.create_category_fragment, container, false)

//        database = Firebase.database.reference
//
//        view.findViewById<Button>(R.id.submit_button).setOnClickListener {
//            if (view.findViewById<TextView>(R.id.section_name_enter).text.isEmpty()) {
//                view.findViewById<TextView>(R.id.section_name_enter).error = "Please enter name"
//                view.findViewById<TextView>(R.id.section_name_enter).requestFocus()
//            }
//            else if (view.findViewById<TextView>(R.id.short_description_enter).text.isEmpty()) {
//                view.findViewById<TextView>(R.id.short_description_enter).error = "Please enter description"
//                view.findViewById<TextView>(R.id.short_description_enter).requestFocus()
//            }
//            else {
//                categoryName = view.findViewById<TextView>(R.id.section_name_enter).text.toString()
//                writeNewCategory(view.findViewById<TextView>(R.id.section_name_enter).text.toString(), view.findViewById<TextView>(R.id.short_description_enter).text.toString())
//                view?.findNavController()?.navigate(R.id.action_createCategoryFragment_to_categoryHomeFragment)
//            }
//        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        forumName = this.arguments?.getString("name").toString()

        database = Firebase.database.reference

        view.findViewById<Button>(R.id.submit_button).setOnClickListener {
            if (view.findViewById<TextView>(R.id.section_name_enter).text.isEmpty()) {
                view.findViewById<TextView>(R.id.section_name_enter).error = "Please enter name"
                view.findViewById<TextView>(R.id.section_name_enter).requestFocus()
            }
            else if (view.findViewById<TextView>(R.id.short_description_enter).text.isEmpty()) {
                view.findViewById<TextView>(R.id.short_description_enter).error = "Please enter description"
                view.findViewById<TextView>(R.id.short_description_enter).requestFocus()
            }
            else {
                categoryName = view.findViewById<TextView>(R.id.section_name_enter).text.toString()
                writeNewCategory(view.findViewById<TextView>(R.id.section_name_enter).text.toString(), view.findViewById<TextView>(R.id.short_description_enter).text.toString())
                val bundle = Bundle()
                bundle.putString("name", forumName)
                view.findNavController().navigate(R.id.action_createCategoryFragment_to_forumHomeFragment, bundle)
            }
        }
    }

    private fun writeNewCategory(name: String, description: String) {
        val category = Category(name, description)

        println("Forum name" + forumName)
        database.child("Forums").child(forumName).child("Categories").child(name).setValue(category)
    }
}