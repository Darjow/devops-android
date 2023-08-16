package com.hogent.android.ui.vms.aanvraag

import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hogent.android.data.entities.BackupType
import com.hogent.android.data.entities.OperatingSystem
import com.hogent.android.data.repositories.VmAanvraagRepository
import com.hogent.android.data.entities.VirtualMachineModus
import com.hogent.android.network.dtos.responses.ProjectOverView
import com.hogent.android.network.dtos.responses.ProjectOverViewItem
import com.hogent.android.ui.components.forms.RequestForm
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import java.time.LocalDate

class VmAanvraagViewModel(val repo : VmAanvraagRepository): ViewModel() {

    private val _navToList = MutableLiveData(false)
    private val _vmNaamBestaatAl = MutableLiveData(false)
    private val _closeKeyBoard = MutableLiveData(false)
    private val _projecten = MutableLiveData<ProjectOverView>()
    private val _form = MutableLiveData(RequestForm())
    private val _errorToast = MutableLiveData(false)
    private val _success = MutableLiveData(false)
    private val _naamCreatedProject = MutableLiveData<String>("")
    private val _projectNaamCheck = MutableLiveData(false)


    init {
        Timber.d("INIT IN VMAANVRAAG VIEWMODEL")
        runBlocking {
            refreshProjects()
        }
    }

    suspend fun refreshProjects(){
            _projecten.postValue(repo.getProjecten())
            Timber.d("Grootte lijst van projects: ", projecten.value!!.total)
    }

    val navToList : LiveData<Boolean>
        get() = _navToList
    val closeKeyBoard : LiveData<Boolean>
        get() = _closeKeyBoard
    val projecten : LiveData<ProjectOverView>
        get() = _projecten
    val form : LiveData<RequestForm>
        get() = _form
    val errorToast:  LiveData<Boolean>
        get() = _errorToast
    val success: LiveData<Boolean>
        get() = _success
    val naamCreatedProject : LiveData<String>
        get() = _naamCreatedProject
    val projectNaamCheck : LiveData<Boolean>
        get()  = _projectNaamCheck
    val vmNaamBestaatAl : LiveData<Boolean>
        get() =  _vmNaamBestaatAl

    fun setProjectNaam(v : Editable){
        val naam = v.toString()
        _naamCreatedProject.postValue(naam)
    }

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

        if(projecten.value?.total == 0 || naam.equals("+ Project toevoegen")){
            __form.project_id = -1
        }

        else if(projecten.value!!.projects.any() { it.name == naam }) {
            __form.project_id = 0
        }
        else {
            val project: ProjectOverViewItem = projecten.value!!.projects.filter { it.name == naam }[0]
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

        if(!_form.value!!.isValid()){
            _errorToast.postValue(true)
        }
        
        else{
            runBlocking {
                val vm = repo.getVmsByProjectId(_form.value!!.project_id!!)

                if (vm == null) {
                    _errorToast.postValue(true)
                    return@runBlocking
                }

                if (vm.isEmpty()) {
                    handleSuccessfulResponse()
                    _navToList.postValue(true)
                    return@runBlocking
                }

                val vms = vm!!.filter { vm -> vm.name.equals(_form.value!!.naamVm, true) }

                if (vms!!.isNotEmpty()) {
                    _vmNaamBestaatAl.postValue(true);
                    return@runBlocking
                } else {
                    handleSuccessfulResponse()
                    _navToList.postValue(true)
                }
            }
        }

    }

    private fun handleSuccessfulResponse() {
        runBlocking {
            repo.create(form.value!!)
            _form.postValue(RequestForm())
            _success.postValue(true)
        }
    }

    fun projectMaken(){
        runBlocking {
            var proj = repo.getProjecten()?.projects?.filter { p -> p.name.equals(naamCreatedProject.value, true)}
            if(proj.isNullOrEmpty() && !naamCreatedProject.value.isNullOrEmpty()){
                repo.createProject(naamCreatedProject.value.toString())
                _closeKeyBoard.postValue(true);

            }
            else{
                _projectNaamCheck.postValue(true)
            }
        }
        runBlocking {
            refreshProjects()
        }
    }



    fun doneToastingError(){
        _errorToast.postValue(false)
    }

    fun doneSuccess(){
        _success.postValue(false)
    }
    fun naamCheckProjectReset(){
        _projectNaamCheck.postValue(false)
    }

    fun keyboardHidden(){
        _closeKeyBoard.postValue(false);
    }

    fun naamCheckVmReset(){
        _vmNaamBestaatAl.postValue(false)
    }
    fun doneNavToList(){
        _navToList.postValue(false)
    }
}