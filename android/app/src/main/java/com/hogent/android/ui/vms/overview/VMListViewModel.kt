package com.hogent.android.ui.vms.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hogent.android.data.repositories.VmOverviewRepository
import com.hogent.android.network.dtos.responses.ProjectOverView
import com.hogent.android.network.dtos.responses.VMIndex
import kotlinx.coroutines.runBlocking
import timber.log.Timber

class VMListViewModel(val repo: VmOverviewRepository) : ViewModel() {

    private val _projecten = MutableLiveData<ProjectOverView>(null)
    private var _virtualmachine = MutableLiveData<List<VMIndex>>(null)

    val projecten: LiveData<ProjectOverView>
        get() = _projecten

    val virtualmachine: LiveData<List<VMIndex>>
        get() = _virtualmachine

    init {
        runBlocking {
            refreshProjects()
        }
    }

    suspend fun refreshProjects() {
        val virtualMachineList = mutableListOf<VMIndex>()

        val response = repo.getProjects()

        if (response == null || response.total == 0) {
            _projecten.postValue(null)
            return
        }

        response.projects?.forEach { project ->
            val projectVMs = repo.getById(project.id)

            projectVMs?.virtualMachines?.let { vms ->
                val updatedVMs = vms.map { vm ->
                    VMIndex(vm.id, vm.name, vm.mode, project.id)
                }
                virtualMachineList.addAll(updatedVMs)
            }
        }
        Timber.wtf("RETURNING " + virtualMachineList.size + " VMS")
        _virtualmachine.postValue(virtualMachineList)
        // return after vms as projects is being observed and needs virtualmachines
        _projecten.postValue(response)
    }
}
