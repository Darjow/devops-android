package com.hogent.android.ui.vms.aanvraag

import android.app.Application
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.Spinner
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hogent.android.database.DatabaseImp
import com.hogent.android.database.daos.ContractDao
import com.hogent.android.database.daos.VirtualMachineDao
import com.hogent.android.database.entities.*
import com.hogent.android.database.repositories.VmAanvraagRepository
import com.hogent.android.ui.components.forms.RegisterForm
import com.hogent.android.ui.components.forms.RequestForm
import com.hogent.android.util.AuthenticationManager
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import java.time.LocalDate
import java.util.Date

class VmAanvraagViewModel(val repo : VmAanvraagRepository): ViewModel() {



    private val _projecten = MutableLiveData<List<Project>>()
    private val _form = MutableLiveData(RequestForm())
    private val _errorToast = MutableLiveData(false)
    private val _success = MutableLiveData(false)
    private val _navigateToVmList = MutableLiveData(false)


    init {
        runBlocking {
            _projecten.postValue(repo.getProjecten())
        }

    }

    val projecten : LiveData<List<Project>>
        get() = _projecten

    val form : LiveData<RequestForm>
            get() = _form
    val errorToast:  LiveData<Boolean>
        get() = _errorToast
    val success: LiveData<Boolean>
        get() = _success



    fun setNaamVm(v : Editable){
        val __form = _form.value
        __form!!.naamVm = v.toString()
        _form.postValue(__form)
    }

    fun setStorage(e: Editable){
        val __form = _form.value
        __form!!.storage = try{
            Integer.parseInt(e.toString())
        }catch (e: java.lang.Exception){
            0
        }
        _form.postValue(__form)
    }


    fun projectChanged(naam: String){
        if(projecten.value.isNullOrEmpty()){
            throw IllegalArgumentException("Selected a wrong type of project")
        }
        if(projecten.value!!.none { it.name == naam }){
            throw IllegalArgumentException("Selected a wrong type of project")
        }

        val project: Project = projecten.value!!.filter{ it.name == naam  }[0]
        val __form = _form.value!!;
        __form.project_id = project.id
        _form.postValue(__form)

    }

    fun coresCpuChanged(progress : Int){
        val __form = _form.value
        __form!!.cpuCoresValue = progress
        _form.postValue(__form)
    }
    fun memoryGBChanged(gb :String){
        val __form = _form.value
        __form!!.memory = try {
            Integer.parseInt(gb.split("GB")[0])
        }catch (e: java.lang.Exception){
            0
        }
        _form.postValue(__form)
    }
    fun backupTypeChanged(type : String){
        val __form = _form.value
        __form!!.backUpType = BackupType.valueOf(type.uppercase())
        _form.postValue(__form)
    }
    fun modeChanged(type: String){
        val __form = _form.value
        __form!!.modeVm = VirtualMachineModus.valueOf(type.uppercase().split(" ")[0])
        _form.postValue(__form)
    }
    fun osChanged(type: String){
        val __form = _form.value
        Timber.d(type)
        __form!!.os = OperatingSystem.valueOf(type.split(" ").joinToString('_'.toString()).uppercase())
        _form.postValue(__form)
    }

    fun startDateChanged(date: LocalDate){
        val __form = _form.value
        __form!!.startDate = date
        _form.postValue(__form)
    }
    fun endDateChanged(date: LocalDate){
        val __form = _form.value
        __form!!.endDate = date
        _form.postValue(__form)
    }

    //button clicked
    fun aanvragen(){
        Timber.d("vmaanvraag is binnengekomen: " + form.value.toString())
        if(_form.value!!.isValid()){
            runBlocking {
                repo.create(form!!.value!!)
                _navigateToVmList.postValue(true)

            }
        }else{
            _errorToast.postValue(true)
        }


    }

    fun doneToastingError(){
        _errorToast.postValue(false)
    }

    fun doneSuccess(){
        _success.postValue(false)
    }
}