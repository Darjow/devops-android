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
        _projecten.postValue(db_projecten.getByCustomerId(AuthenticationManager.getCustomer()!!.id!!))
        var templist = mutableListOf<VirtualMachine>()
        projecten.value!!.forEach { i ->
            var listvirtualmachine = db_vms.getByProjectId(i.id!!)
            Timber.i("List from database:")
            Timber.i(listvirtualmachine.isNullOrEmpty().toString())
            listvirtualmachine?.forEach { j ->
                Timber.i(j.toString())
                templist.add(j)
                Timber.i("templist plus:")
                Timber.i(templist.toString())
            }
        }
        Timber.i("templist:")
        Timber.i(templist.toString())
        _virtualmachine.value = templist

        Timber.i("Virtual Machine:")
        Timber.i(_virtualmachine.value.toString())


    }


}

