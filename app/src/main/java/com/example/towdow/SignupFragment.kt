package com.example.towdow

import android.content.ContentValues.TAG
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import com.example.towdow.databinding.SignupFragmentBinding
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class SignupFragment : Fragment() {

    private var _binding: SignupFragmentBinding? =null
    private val binding get() =_binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = SignupFragmentBinding.inflate(inflater,container, false)
        val v = binding.root
        binding.submitButton.setOnClickListener{

            signUpUser()

            //view?.findNavController()?.navigate(R.id.action_signupFragment_to_homeFragment)
        }

        val bottomNavigationView: BottomNavigationView = requireActivity().findViewById(com.example.towdow.R.id.bottomNavigationView)
        bottomNavigationView.visibility = View.GONE
        auth = Firebase.auth

        return v
    }

    private fun signUpUser() {

//        if (!Patterns.EMAIL_ADDRESS.matcher(binding.createEmailEnter.toString()).matches()) {
//            binding.createEmailEnter.error = "Please enter valid email"
//            binding.createEmailEnter.requestFocus()
//            return
//        }

        if (binding.createEmailEnter.text.toString().isEmpty()) {
            binding.createEmailEnter.error = "Please enter email"
            binding.createEmailEnter.requestFocus()
            return
        }

        if (binding.createPasswordEnter.text.toString().isEmpty()) {
            binding.createPasswordEnter.error = "Please enter password"
            binding.createPasswordEnter.requestFocus()
            return
        }

        activity?.let {
            auth.createUserWithEmailAndPassword(binding.createEmailEnter.text.toString(), binding.createPasswordEnter.text.toString())
                .addOnCompleteListener(it) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success")
                        val user = auth.currentUser
                        user?.sendEmailVerification()
                            ?.addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    view?.findNavController()?.navigate(R.id.action_signupFragment_to_loginFragment)
                                }
                            }
                    } else {
                        Log.d("Email", binding.createEmailEnter.text.toString())
                        Log.d("THIS IS TAG", task.exception.toString())
                        // If sign in fails, display a message to the user.
                        Toast.makeText(context, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        }

    }
}