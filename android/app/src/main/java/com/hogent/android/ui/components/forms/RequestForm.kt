package com.hogent.android.ui.components.forms

import androidx.lifecycle.MutableLiveData
import com.hogent.android.database.entities.BackupType
import com.hogent.android.database.entities.OperatingSystem
import com.hogent.android.database.entities.VirtualMachineModus
import java.text.Normalizer.Form
import java.time.LocalDate
import java.util.*

data class RequestForm(
     var naamVm : String? = null,
     var hostnameVm : String? = null,
     var os : OperatingSystem? = null,
     var cpuCoresValue : Int? = null,
     var memory : Int? = null,
     var backUpType : BackupType? = null,
     var modeVm : VirtualMachineModus? = null,
     var storage : Int? = null,
     var startDate : LocalDate?= null,
     var endDate : LocalDate? = null
): IFormValidation {

    var error_message = ""

    override fun isValid(): Boolean {
        if(hostnameVm == null || naamVm == null || cpuCoresValue == null || memory == null || storage == null  || startDate == null || endDate == null || os == null || modeVm == null || backUpType == null){
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

        return true;
    }

    override fun getError(): String? {
        return error_message;
    }
}