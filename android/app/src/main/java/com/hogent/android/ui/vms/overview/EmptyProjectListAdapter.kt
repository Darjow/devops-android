package com.hogent.android.ui.vms.overview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hogent.android.R

class EmptyProjectListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.fragment_no_projects,
            parent,
            false
        )
        return EmptyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {}

    override fun getItemCount(): Int { return 0 }

    inner class EmptyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
