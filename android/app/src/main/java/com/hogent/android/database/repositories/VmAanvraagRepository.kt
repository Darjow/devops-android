package com.hogent.android.database.repositories

import android.app.Application
import com.hogent.android.database.DatabaseImp
import com.hogent.android.database.daos.ContractDao
import com.hogent.android.database.daos.ProjectDao
import com.hogent.android.database.daos.VirtualMachineDao
import com.hogent.android.database.entities.*
import com.hogent.android.ui.components.forms.RequestForm
import com.hogent.android.util.AuthenticationManager


class VmAanvraagRepository(private val databaseImp: DatabaseImp) {

    private val customerId = AuthenticationManager.getCustomer()!!.id!!

    private val vmDao: VirtualMachineDao = databaseImp.virtualMachineDao
    private val contractDao: ContractDao = databaseImp.contractDao
    private val projectenDao: ProjectDao = databaseImp.projectDao

    suspend fun create(form: RequestForm){
        val hardware = HardWare(form.memory!!, form.storage!!, form.cpuCoresValue!!)
        val backup = Backup(form.backUpType, null)

        val c_id = contractDao.insert(Contract(startDate = form.startDate!!, endDate = form.endDate!!))
        val vm = VirtualMachine(name = form.naamVm!!, status = VirtualMachineStatus.AANGEVRAAGD, mode = form.modeVm!!, hardware = hardware, backup = backup, operatingSystem = form.os!!, contractId = c_id, projectId = form.project_id!!)
        vmDao.insert(vm);
    }

    suspend fun getProjecten(): List<Project>?{
        return projectenDao.getByCustomerId(customerId)
    }
}