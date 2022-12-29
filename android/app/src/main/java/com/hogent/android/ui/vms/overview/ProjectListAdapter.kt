package com.hogent.android.ui.vms.overview

import android.app.Application
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hogent.android.R
import com.hogent.android.database.entities.Project
import com.hogent.android.database.entities.VirtualMachine
import timber.log.Timber


class ProjectListAdapter(
    private val projectList: List<Project>,
    private val virtualmachineList: List<VirtualMachine>?,
    private val context: Context?,
    private val application: Application
) : RecyclerView.Adapter<ProjectListAdapter.ViewHolder>() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var view: View
    private var newvirtualMachineList = mutableListOf<VirtualMachine>()


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val textView1: TextView = itemView.findViewById(R.id.textView1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        view = LayoutInflater.from(parent.context).inflate(R.layout.project_container, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val project = projectList[position]

        recyclerView = view.findViewById(R.id.virtual_machine_recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(context);
        Timber.i("ProjectAdapter:")
        Timber.i(virtualmachineList.isNullOrEmpty().toString())
        filterVirtualMachines(project.id!!)
        recyclerView.adapter = VirtualMachineListAdapter(newvirtualMachineList, application)

        holder.textView1.text = project.name
    }

    override fun getItemCount(): Int {
        return projectList.size
    }


    private fun filterVirtualMachines(projectId: Long) {
        newvirtualMachineList.clear()
        Timber.i("filterVirtualMachines Project ID:")
        Timber.i(projectId.toString())
        virtualmachineList?.forEach { i ->
            if (i.projectId == projectId) {
                newvirtualMachineList.add(i)
            }
        }
        Timber.i("filterVirtualMachines:")
        Timber.i(newvirtualMachineList.toString())

    }
}

