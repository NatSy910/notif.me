package com.example.notifme.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notifme.databinding.FragmentAddToDoBinding

class ToDoAdapter(private val list: MutableList<ToDoAdapter>) :
    RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder {

    private var listener:ToDoAdapterClicksInterface? = null
    fun setListener(listener:ToDoAdapterClicksInterface){
        this.listener = listener
    }
    inner class ToDoViewHolder(val binding: FragmentAddToDoBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
        val binding = FragmentAddToDoBinding.inflate(LayoutInflater.from(parent.context),parent, false )
        return ToDoViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface ToDoAdapterClicksInterface{
        fun onDeleteTaskBtnClicked(toDoData: ToDoData)
        fun onEditTaskBtnCLicked(toDoData: ToDoData)
    }

    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {
        with(holder){
            with(list[position]){
                binding.txtTaskName.text = this.task

                binding.btnSaveToDo.setOnClickListener{
                    listener?.onEditTaskBtnCLicked(this)
                }

                binding.btnCancel.setOnClickListener {
                    listener?.onDeleteTaskBtnClicked(this)
                }

            }
        }
    }

}
