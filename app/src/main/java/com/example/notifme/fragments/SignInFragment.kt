package com.example.notifme.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.notifme.R
import com.example.notifme.databinding.FragmentSignInBinding
import com.example.notifme.databinding.FragmentSignUpFragmentBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth

class SignInFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var navControl: NavController
    private lateinit var binding: FragmentSignInBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init(view)
        registerEvents()
    }

    private fun init(view: View) {
        navControl = Navigation.findNavController(view)
        auth = FirebaseAuth.getInstance()
    }


    private fun registerEvents() {

        binding.txtRegged.setOnClickListener{
            navControl.navigate(R.id.action_signInFragment_to_signUpfragment)
        }

        binding.btnNext.setOnClickListener{
            val email = binding.edtEmail.text.toString().trim()
            val password = binding.edtPassword.text.toString().trim()

            if(email.isNotEmpty() && password.isNotEmpty()){
                binding.progressBar.visibility = View.VISIBLE
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(
                    OnCompleteListener {
                        if (it.isSuccessful){
                            Toast.makeText(context, "Login successful!", Toast.LENGTH_SHORT).show()
                            navControl.navigate(R.id.action_signInFragment_to_homeFragment)
                        } else {
                            Toast.makeText(context, "You may not be a registered user or have entered invalid credentials.", Toast.LENGTH_LONG).show()
                            }
                        binding.progressBar.visibility = View.GONE
                    })
            } else {
                Toast.makeText(context, "Please fill up the necessary fields.", Toast.LENGTH_SHORT).show()
            }
        }
    }

}