package com.hogent.android.database.repositories

import com.hogent.android.database.entities.Contract
import com.hogent.android.database.entities.Project
import com.hogent.android.database.entities.VirtualMachine
import com.hogent.android.network.services.ContractApi
import com.hogent.android.network.services.ProjectApi
import com.hogent.android.network.services.VirtualMachineApi

class VmOverviewRepository {

    private val vmApi = VirtualMachineApi.retrofitService;
    private val projectApi = ProjectApi.retrofitService;


    suspend fun getByCustomerId(id: Long): List<Project>?{
        return projectApi.getByCustomerId(id)

    }

    suspend fun getByProjectId(id: Long): List<VirtualMachine>?{
        return vmApi.getByProjectId(id)
    }
}