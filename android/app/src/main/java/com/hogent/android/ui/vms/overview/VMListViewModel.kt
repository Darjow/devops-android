package com.hogent.android.ui.vms.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hogent.android.data.entities.User
import com.hogent.android.data.repositories.VmOverviewRepository
import com.hogent.android.data.entities.VirtualMachine
import com.hogent.android.network.dtos.responses.ProjectOverView
import com.hogent.android.network.dtos.responses.ProjectOverViewItem
import com.hogent.android.network.dtos.responses.VMIndex
import kotlinx.coroutines.runBlocking
import timber.log.Timber


class VMListViewModel(val repo: VmOverviewRepository) : ViewModel() {

    private val _projecten = MutableLiveData<ProjectOverView>()
    private var _virtualmachine = MutableLiveData<List<VMIndex>>()


    val projecten: LiveData<ProjectOverView>
        get() = _projecten;

    val virtualmachine: LiveData<List<VMIndex>>
        get() = _virtualmachine;

    init {
        runBlocking {
            refreshProjects();
        }
    }

    suspend fun refreshProjects() {
        val virtualMachineList = mutableListOf<VMIndex>()

        _projecten.value = repo.getProjects()
        Timber.d("Landed on vmlist viewmodel page, this user has %d projects",
                projecten.value?.total
                    ?: 0)

        Timber.wtf(_projecten.value.toString())


        if(_projecten.value == null || _projecten.value?.total == 0){
            _projecten.postValue(ProjectOverView(listOf(ProjectOverViewItem(0,"Geen Projecten", User(0,"","","",""))), 0))
            return
        }

        _projecten.value?.projects?.forEach { project ->
            val projectVMs = repo.getById(project.id)

            Timber.d(
                String.format(
                    "project: %s, has %d virtual machine(s)",
                    project.name,
                    projectVMs?.virtualMachines?.size
                )
            )

            projectVMs?.virtualMachines?.let { vms ->
                val updatedVMs = vms.map { vm ->
                    VMIndex(vm.id, vm.name, vm.mode, project.id)
                }
                virtualMachineList.addAll(updatedVMs)
            }

        }
        _virtualmachine.value = virtualMachineList
    }
}


