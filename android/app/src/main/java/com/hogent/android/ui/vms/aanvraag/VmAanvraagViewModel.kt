package com.hogent.android.ui.vms.aanvraag

import android.app.Application
import android.text.Editable
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hogent.android.database.DatabaseImp
import com.hogent.android.database.daos.VirtualMachineDao
import com.hogent.android.database.entities.BackupType
import com.hogent.android.database.entities.OperatingSystem
import com.hogent.android.database.entities.VirtualMachine
import com.hogent.android.database.entities.VirtualMachineModus
import com.hogent.android.ui.components.forms.RegisterForm
import com.hogent.android.ui.components.forms.RequestForm
import timber.log.Timber
import java.time.LocalDate
import java.util.Date

class VmAanvraagViewModel(app : Application): ViewModel() {


    private val vmDao: VirtualMachineDao = DatabaseImp.getInstance(app).virtualMachineDao


    private val _form = MutableLiveData(RequestForm())
    private val _errorToast = MutableLiveData(false)
    private val _success = MutableLiveData(false)
    private val _navigateToVmList = MutableLiveData(false)



    val form : LiveData<RequestForm>
            get() = _form
    val errorToast:  LiveData<Boolean>
        get() = _errorToast
    val success:  LiveData<Boolean>
        get() = success


    fun setNaamVm(v : Editable){
        val __form = _form.value
        __form!!.naamVm = v.toString()
        _form.postValue(__form)
    }
    fun setHostnameVm(v : Editable){
        val __form = _form.value
        __form!!.hostnameVm = v.toString()
        _form.postValue(__form)
    }
    /*fun setStorage(v : Unit){
        val __form = _form.value
        __form!!.storage = v.toString()
        _form.postValue(__form)    }*/


    fun coresCpuChanged(progress : Int){
        val __form = _form.value
        __form!!.cpuCoresValue = progress
        _form.postValue(__form)    }
    fun memoryGBChanged(gb :String){
        val __form = _form.value
        __form!!.memory = try {
            Integer.parseInt(gb)
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
        __form!!.modeVm = VirtualMachineModus.valueOf(type.uppercase())
        _form.postValue(__form)
    }
    fun osChanged(type: String){
        //hier zal wrs methode moeten komen die de string split op " " en dan joined op een "_" en dan in capital zet
        //daarna met deze value: OperatingSystem.valueOf(value)

        val __form = _form.value
        __form!!.os = OperatingSystem.valueOf(type.uppercase())
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
            val vm: VirtualMachine = VirtualMachine()

            _navigateToVmList.postValue(true)
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