package com.hogent.android.ui.vms.overview


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.hogent.android.R
import com.hogent.android.network.dtos.responses.VMIndex
import com.hogent.android.util.AuthenticationManager


class VirtualMachineListAdapter (
    private var virtualmachineList: List<VMIndex>?,
) : RecyclerView.Adapter<VirtualMachineListAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView1: TextView = itemView.findViewById(R.id.status)
        val textView2: TextView = itemView.findViewById(R.id.naam)
        val textView3: TextView = itemView.findViewById(R.id.klant)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.virtual_machine_container, parent, false)

        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position == 0) {
            holder.textView1.textSize = 15F
            holder.textView2.textSize = 15F
            holder.textView3.textSize = 15F
        } else {
            val virtualmachine = virtualmachineList!![position - 1]
            holder.textView1.text = virtualmachine?.mode.toString()
            holder.textView2.text = virtualmachine?.name
            holder.textView3.text = AuthenticationManager.getCustomer()!!.email

            holder.itemView.setOnClickListener {
                Navigation.findNavController(it).navigate(VMListFragmentDirections.actionFromVmlistToDetail(virtualmachine!!.id))
            }

        }
    }

    override fun getItemCount(): Int {
        return virtualmachineList?.size!! + 1
    }
}

