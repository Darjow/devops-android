package com.hogent.android.ui.vms.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hogent.android.data.entities.*
import com.hogent.android.data.repositories.VmOverviewRepository
import com.hogent.android.util.AuthenticationManager
import kotlinx.coroutines.runBlocking
import timber.log.Timber


class VMListViewModel(val repo: VmOverviewRepository) : ViewModel() {

    private val _projecten = MutableLiveData<List<Project>>()
    private var _virtualmachine = MutableLiveData<List<VirtualMachine>>()


    val projecten: LiveData<List<Project>>
        get() = _projecten;

    val virtualmachine: LiveData<List<VirtualMachine>>
        get() = _virtualmachine;

    init {
        runBlocking {
            refreshProjects();
        }
    }

    suspend fun refreshProjects() {
        val customerId = AuthenticationManager.getCustomer()!!.id
        val virtualMachineList = mutableListOf<VirtualMachine>()

        _projecten.value = repo.getByCustomerId(customerId)
        Timber.d(
            String.format(
                "Landed on vmlist viewmodel page, this user has %d projects",
                projecten.value?.size ?: 0
            )
        )

        Timber.wtf(_projecten.value.toString())


        if(_projecten.value == null || _projecten.value!!.isEmpty()){
            _projecten.postValue(listOf( Project("Geen projecten", customerId, -1)))
            return
        }

        _projecten.value?.forEach { project ->
            val projectVMs = repo.getByProjectId(project.id)

            Timber.d(
                String.format(
                    "project: %s, has %d virtual machine(s)",
                    project.name,
                    projectVMs?.size ?: 0
                )
            )

            if (projectVMs != null) {
                virtualMachineList.addAll(projectVMs)
            }

        }
        _virtualmachine.value = virtualMachineList
    }
}


