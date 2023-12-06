package com.example.notifme.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notifme.databinding.FragmentHomeBinding
import com.example.notifme.utils.ToDoAdapter
import com.example.notifme.utils.ToDoData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener


class HomeFragment : DialogFragment(), AddToDoFragment.DiaglogNextBtnClickListener,
    ToDoAdapter.ToDoAdapterClicksInterface {

    companion object {
        fun newInstance(): AddToDoFragment {
            return AddToDoFragment()
        }
    }

//    private lateinit var auth : FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
//    private lateinit var navController: NavController
    private lateinit var binding: FragmentHomeBinding
    private lateinit var taskPopUp: AddToDoFragment
    private lateinit var adapter: ToDoAdapter
    private lateinit var mList:MutableList<ToDoAdapter>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init(view)
        getDataFromFirebase()
        registerEvents()

    }

    private fun registerEvents() {
        binding.floatBtnAdd.setOnClickListener {
            taskPopUp = AddToDoFragment()
            taskPopUp.setListener(this)
            taskPopUp.show(childFragmentManager, "AddToDoFragment")
        }
     }

    private fun init(view: View) {
//        navController = Navigation.findNavController(view)
//        auth = FirebaseAuth.getInstance()
//        databaseRef = FirebaseDatabase.getInstance().reference.child("Tasks").child(auth.currentUser?.uid.toString())

        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        mList = mutableListOf()
        adapter = ToDoAdapter(mList)
        adapter.setListener(this)
        binding.recyclerView.adapter = adapter
    }

    private fun getDataFromFirebase(){
        databaseRef.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                mList.clear()
                for (taskSnapshot in snapshot.children){
                    val toDoTask = taskSnapshot.key?.let{
                        ToDoData(it, taskSnapshot.value.toString())
                    }

                    if (toDoTask != null){
                        mList.add(toDoTask)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
            }

        })
    }

    override fun onSaveTask(task: String, edtTaskName: EditText) {
        databaseRef.push().setValue(task).addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(context, "Successfully saved", Toast.LENGTH_SHORT).show()
                edtTaskName.setText("null")
            } else {
                Toast.makeText(context, it.exception?.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDeleteTaskBtnClicked(toDoData: ToDoData) {
        databaseRef.child(toDoData.taskId).removeValue().addOnCompleteListener {
            if (it.isSuccessful){
                Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_SHORT).show()
            }else{

            }
        }
    }

    override fun onEditTaskBtnCLicked(toDoData: ToDoData) {
        TODO("Not yet implemented")
    }
}