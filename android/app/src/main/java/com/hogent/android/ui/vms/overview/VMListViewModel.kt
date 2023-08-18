package com.hogent.android.ui.vms.overview

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hogent.android.data.entities.User
import com.hogent.android.data.repositories.VmOverviewRepository
import com.hogent.android.network.dtos.responses.ProjectOverView
import com.hogent.android.network.dtos.responses.ProjectOverViewItem
import com.hogent.android.network.dtos.responses.VMIndex
import kotlinx.coroutines.runBlocking
import timber.log.Timber


class VMListViewModel(val repo: VmOverviewRepository) : ViewModel() {

    private val _projecten = MutableLiveData<ProjectOverView>()
    private var _virtualmachine = MutableLiveData<List<VMIndex>>()
    private var _showProjectList = MutableLiveData(View.GONE)
    private var _showNoProjects = MutableLiveData(View.VISIBLE)

    val showProjectList: LiveData<Int>
        get() = _showProjectList

    val showNoProjects: LiveData<Int>
        get() = _showNoProjects

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
        _showProjectList.postValue(View.GONE)
        _showNoProjects.postValue(View.GONE)

        _projecten.value = repo.getProjects()

        if(projecten.value?.total != null && projecten.value?.total!! > 0){
            _showProjectList.postValue(View.VISIBLE)
        }else{
            _showNoProjects.postValue(View.VISIBLE)
        }

        Timber.wtf((showNoProjects?.value == View.VISIBLE).toString());

        if(_projecten.value == null || _projecten.value?.total == 0){
            _projecten.postValue(ProjectOverView(listOf(ProjectOverViewItem(0,"Geen Projecten", User(0,"","","",""))), 0))
            return
        }

        _projecten.value?.projects?.forEach { project ->
            val projectVMs = repo.getById(project.id)

            projectVMs?.virtualMachines?.let { vms ->
                val updatedVMs = vms.map { vm ->
                    VMIndex(vm.id, vm.name, vm.mode, project.id)
                }
                virtualMachineList.addAll(updatedVMs)
            }

        }
        _virtualmachine.postValue(virtualMachineList)
    }
}


