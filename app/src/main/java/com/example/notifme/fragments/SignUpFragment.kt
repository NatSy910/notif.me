package com.example.notifme.fragments

import android.os.Bundle
import android.view.AttachedSurfaceControl
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.notifme.R
import com.example.notifme.databinding.FragmentSignUpFragmentBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth


class SignUpFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var navControl: NavController
    private lateinit var binding: FragmentSignUpFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentSignUpFragmentBinding.inflate(inflater, container, false)
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
            navControl.navigate(R.id.action_signUpfragment_to_signInFragment)
        }

        binding.btnNext.setOnClickListener{
            val email = binding.edtEmail.text.toString().trim()
            val password = binding.edtPassword.text.toString().trim()
            val confirm_password = binding.edtConfirmPassword.text.toString().trim()

            if(email.isNotEmpty() && password.isNotEmpty() && confirm_password.isNotEmpty()){
                if(password == confirm_password){
                    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(
                        OnCompleteListener {
                            if (it.isSuccessful){
                                Toast.makeText(context, "Registered successfully!", Toast.LENGTH_SHORT).show()
                                navControl.navigate(R.id.action_signUpfragment_to_homeFragment)
                            } else {
                                Toast.makeText(context, it.exception?.message, Toast.LENGTH_SHORT).show()
                            }
                        })
                }
            }
        }
    }
}