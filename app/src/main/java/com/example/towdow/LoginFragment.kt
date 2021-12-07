package com.example.towdow

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.towdow.databinding.LoginFragmentBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class LoginFragment : Fragment() {
    private var _binding: LoginFragmentBinding? =null
    private val binding get() =_binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = LoginFragmentBinding.inflate(inflater,container, false)
        val v = binding.root

     //   val bottomNavigationView: BottomNavigationView = requireActivity().findViewById(com.example.towdow.R.id.bottomNavigationView)
    //    bottomNavigationView.visibility = View.GONE

        auth = Firebase.auth

        binding.towDowImage.setImageResource(R.drawable.logo)
        binding.signinButton.setOnClickListener{
            doLogin()
        }

        binding.signupButton.setOnClickListener{
            view?.findNavController()?.navigate(R.id.action_loginFragment_to_signupFragment)
        }


        return v
    }

    private fun doLogin() {
        if (binding.emailEnter.text.toString().isEmpty()) {
            binding.emailEnter.error = "Please enter email"
            binding.emailEnter.requestFocus()
            return
        }

        if (binding.passwordEnter.text.toString().isEmpty()) {
            binding.passwordEnter.error = "Please enter password"
            binding.passwordEnter.requestFocus()
            return
        }

        activity?.let {
            auth.signInWithEmailAndPassword(binding.emailEnter.text.toString(), binding.passwordEnter.text.toString())
                .addOnCompleteListener(it) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("TAG", "signInWithEmail:success")
                        val user = auth.currentUser
                        updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("TAG", "signInWithEmail:failure", task.exception)
                        Toast.makeText(context, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                        updateUI(null)
                    }
                }
        }

    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null) {
            if (currentUser.isEmailVerified) {
                Log.d("Is Verified", currentUser.isEmailVerified.toString())
                view?.findNavController()?.navigate(R.id.action_loginFragment_to_homeFragment)
            }
        }
        else {
            Toast.makeText(context, "Login failed.",
                Toast.LENGTH_SHORT).show()
        }
    }

}

