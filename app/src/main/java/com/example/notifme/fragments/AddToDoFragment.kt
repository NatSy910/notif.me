package com.example.notifme.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.notifme.databinding.FragmentAddToDoBinding
import com.google.firebase.FirebaseApp


class AddToDoFragment : DialogFragment() {

    private lateinit var binding : FragmentAddToDoBinding
    private lateinit var listener: DiaglogSaveBtnClickListener

    fun setListener(listener: HomeFragment) {
        this.listener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        FirebaseApp.initializeApp(requireContext())

        // Inflate the layout for this fragment
        binding = FragmentAddToDoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerEvents()
    }

    private fun registerEvents() {
        binding.btnSaveToDo.setOnClickListener {
            val taskName = binding.edtTaskName.text.toString()
            if (taskName.isNotEmpty()) {
                listener.onSaveTask(taskName, binding.edtTaskName)
            } else  {
                Toast.makeText(context, "Please enter task name", Toast.LENGTH_SHORT).show()
            }
        }
        binding.btnCancel.setOnClickListener {
            dismiss()
        }
    }

    interface DiaglogSaveBtnClickListener {
        fun onSaveTask(todo : String , edtTaskName : EditText)
    }


}