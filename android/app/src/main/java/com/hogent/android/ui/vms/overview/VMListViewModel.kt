package com.hogent.android.ui.vms.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hogent.android.database.DatabaseImp
import com.hogent.android.database.entities.*
import com.hogent.android.util.AuthenticationManager
import kotlinx.coroutines.runBlocking
import timber.log.Timber


class VMListViewModel(db: DatabaseImp) : ViewModel() {

    private val db_projecten = db.projectDao;
    private val db_vms = db.virtualMachineDao;


    private val _projecten = MutableLiveData<List<Project>>()
    private var _virtualmachine = MutableLiveData<List<VirtualMachine>>()


    val projecten: LiveData<List<Project>>
        get() = _projecten;

    val virtualmachine: LiveData<List<VirtualMachine>>
        get() = _virtualmachine;

    init {
        val customerId = AuthenticationManager.getCustomer()!!.id
        val virtualMachineList = mutableListOf<VirtualMachine>()

        _projecten.value = db_projecten.getByCustomerId(customerId)
        Timber.d(String.format("Landed on vmlist viewmodel page, this user has %d projects", projecten.value?.size ?: 0))

        _projecten.value?.forEach { project ->
            val projectVMs = db_vms.getByProjectId(project.id)

            Timber.d(String.format("project: %s, has %d virtual machine(s)", project.name, projectVMs?.size ?: 0))

            if (projectVMs != null) {
                virtualMachineList.addAll(projectVMs)
                }
        }
        _virtualmachine.value = virtualMachineList
    }
}

