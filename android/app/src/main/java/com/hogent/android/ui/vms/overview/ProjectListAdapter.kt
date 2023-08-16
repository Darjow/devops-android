package com.hogent.android.ui.vms.overview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hogent.android.R
import com.hogent.android.data.entities.VirtualMachine
import com.hogent.android.network.dtos.responses.ProjectOverView
import com.hogent.android.network.dtos.responses.VMIndex
import com.hogent.devOps_Android.ui.vms.overview.VirtualMachineListAdapter
import timber.log.Timber

class ProjectListAdapter(
    private val projectList: ProjectOverView,
    private val virtualmachineList: List<VMIndex>?,
    private val context: Context?,
) : RecyclerView.Adapter<ProjectListAdapter.ViewHolder>() {


    private var newvirtualMachineList = mutableListOf<VirtualMachine>()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val textView1: TextView = itemView.findViewById(R.id.textView1)
        val recyclerView: RecyclerView = itemView.findViewById(R.id.virtual_machine_recyclerview)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.project_container, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val project = projectList.projects[position]
        holder.textView1.text = project.name;

        if(project.id > 0) {
            holder.recyclerView.layoutManager = LinearLayoutManager(context)
            //filterVirtualMachines(project.id)
            holder.recyclerView.adapter = VirtualMachineListAdapter(newvirtualMachineList)
            holder.textView1.text = project.name
        }
    }

    override fun getItemCount(): Int {
        return projectList.total
    }


    private fun filterVirtualMachines(projectId: Int) {
        Timber.e("Previous length of vms inside project with id: %d   =  %d",projectId, newvirtualMachineList.size)
        newvirtualMachineList.clear()

        //de nieuw gecreërde VM zit niet in deze lijst
        virtualmachineList?.forEach { i ->
            //if (i.projectId == projectId) {
            //    newvirtualMachineList.add(i)
            //}
        }
        Timber.e("After refreshing project with id: %d   =  %d", projectId, newvirtualMachineList.size)

    }
}
