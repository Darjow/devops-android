package com.hogent.android.ui.components.forms

import androidx.lifecycle.MutableLiveData
import com.hogent.android.database.entities.BackupType
import com.hogent.android.database.entities.OperatingSystem
import com.hogent.android.database.entities.VirtualMachineModus
import java.text.Normalizer.Form
import java.time.LocalDate
import java.util.*

data class RequestForm(
     var naamVm : String? = "",
     var os : OperatingSystem? = null,
     var cpuCoresValue : Int? = 0,
     var memory : Int? = 0,
     var backUpType : BackupType? = null,
     var modeVm : VirtualMachineModus? = null,
     var storage : Int? = 0,
     var startDate : LocalDate?= null,
     var endDate : LocalDate? = null,
     var project_id: Long? = 0L
): IResetableFormValidation {

    var error_message = ""

    override fun isValid(): Boolean {
        if(naamVm.isNullOrBlank() || cpuCoresValue == null || memory == null || storage == null  || startDate == null || endDate == null || os == null || modeVm == null || backUpType == null){
            error_message = "Alle velden zijn verplicht"
            return false;
        }

        if (endDate!! == startDate || endDate!!.isBefore(startDate)){
            error_message = "Einddatum moet na startdatum"
            return false;
        }

        if(cpuCoresValue!! < 1){
            error_message = "Cores moet positief zijn"
            return false;
        }
        if(memory!! < 1){
            error_message = "Geheugen moet positief zijn"
            return false;
        }
        if(storage!! < 1){
            error_message = "Opslag moet positief zijn"
            return false;
        }
        if(project_id == 0L){
            error_message = "Selecteer een project"
            return false;
        }

        return true;
    }

    override fun getError(): String? {
        return error_message;
    }

    override fun reset(): RequestForm {
        return RequestForm()
    }

}