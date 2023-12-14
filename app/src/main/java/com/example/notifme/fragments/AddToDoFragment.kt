package com.example.notifme.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.notifme.databinding.FragmentAddToDoBinding
import com.example.notifme.utils.ToDoData
import com.google.firebase.FirebaseApp
import java.util.Calendar


class AddToDoFragment : DialogFragment() {

    private lateinit var binding : FragmentAddToDoBinding
    private lateinit var listener: DiaglogSaveBtnClickListener
    private var toDoData : ToDoData? = null

    fun setListener(listener: HomeFragment) {
        this.listener = listener
    }

    companion object {
        const val TAG = "AddToDoFragment"
        @JvmStatic
        fun newInstance(taskID : String, task:String) = AddToDoFragment().apply {
            arguments = Bundle().apply {
                putString("taskID", taskID)
                putString("task", task)
            }
        }
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

        if (arguments != null) {
            toDoData = ToDoData(
                arguments?.getString("taskID").toString(),
                arguments?.getString("task").toString(),
                arguments?.getString("taskDueDate").toString()) //add


            binding.edtTaskName.setText(toDoData?.task)
        }
        registerEvents()

        binding.edtDueDate.setOnClickListener() {
            val calendar: Calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            context?.let { it1 ->
                DatePickerDialog(it1, { it, year, month, day ->
                    val dat = (day.toString() + '-' + (month + 1) + '-' + year)
                    binding.edtDueDate.setText(dat)
                }, year, month, day)
            }?.show()
        }
    }

    private fun registerEvents() {
        binding.btnSaveToDo.setOnClickListener {
            val taskName = binding.edtTaskName.text.toString()
            val taskDueDate = binding.edtDueDate.text.toString()
            if (taskName.isNotEmpty()) {
                if (toDoData == null) {
                    listener.onSaveTask(taskName, binding.edtTaskName, binding.edtDueDate) //add
                } else {
                    toDoData?.task = taskName
                    toDoData?.taskDueDate = taskDueDate
                    listener.onUpdateTask(toDoData!!, binding.edtTaskName, binding.edtDueDate) //add
                }
                listener.onSaveTask(
                    taskName,
                    binding.edtTaskName,
                    binding.edtDueDate //add
                )
            } else  {
                Toast.makeText(context, "Please enter task name", Toast.LENGTH_SHORT).show()
            }
        }
        binding.btnCancel.setOnClickListener {
            dismiss()
        }
    }

    interface DiaglogSaveBtnClickListener {
        fun onSaveTask(
            todo: String,
            edtTaskName: EditText,
            edtDueDate: EditText
        )
        fun onUpdateTask(
            toDoData: ToDoData,
            edtTaskName: EditText,
            edtDueDate: EditText
        )
    }


}