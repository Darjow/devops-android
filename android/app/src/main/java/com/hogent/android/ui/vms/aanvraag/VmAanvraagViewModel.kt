package com.hogent.android.ui.vms.aanvraag

import android.text.Editable
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hogent.android.R
import com.hogent.android.database.entities.*
import com.hogent.android.database.repositories.VmAanvraagRepository
import com.hogent.android.ui.components.forms.RequestForm
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import java.time.LocalDate

class VmAanvraagViewModel(val repo : VmAanvraagRepository): ViewModel() {



    private val _projecten = MutableLiveData<List<Project>>()
    private val _form = MutableLiveData(RequestForm())
    private val _errorToast = MutableLiveData(false)
    private val _success = MutableLiveData(false)


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
            Integer.parseInt(e.toString()) * 1000
        }catch (e: java.lang.Exception){
            0
        }
        _form.postValue(__form)
    }


    fun projectChanged(naam: String){
        val __form = _form.value!!;

        if(projecten.value.isNullOrEmpty()){
            __form.project_id = -1
        }


        if(projecten.value!!.none { it.name == naam }){
            __form.project_id = 0
        }
        else {
            val project: Project = projecten.value!!.filter { it.name == naam }[0]
            __form.project_id = project.id
        }
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
            Integer.parseInt(gb.split("GB")[0]) * 1000
        }catch (e: java.lang.Exception){
            0
        }
        _form.postValue(__form)
    }
    fun backupTypeChanged(type : String){
        val __form = _form.value
        if(type.isNullOrBlank()){
            __form!!.backUpType == null;
        }else{
            __form!!.backUpType = BackupType.valueOf(type.uppercase());
        }
        _form.postValue(__form)
    }
    fun modeChanged(type: String){
        val __form = _form.value

        if(type == "null"){
            __form!!.modeVm = null;
        }else{
            __form!!.modeVm = VirtualMachineModus.valueOf(type.uppercase().split(" ")[0])
        }
        _form.postValue(__form)
    }
    fun osChanged(type: String){
        val __form = _form.value

        //nullcheck voor form reset
        if(type == "null"){
            __form!!.os = null;
        }else{
            __form!!.os = OperatingSystem.valueOf(type.split(" ").joinToString('_'.toString()).uppercase())
        }
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

    fun aanvragen(){
        Timber.d("vmaanvraag is binnengekomen: " + form.value.toString())
        if(_form.value!!.isValid()){
            runBlocking {
                repo.create(form.value!!)
                _form.postValue(RequestForm())
                _success.postValue(true)
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