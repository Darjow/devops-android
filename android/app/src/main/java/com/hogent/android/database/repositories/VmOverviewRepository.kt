package com.hogent.android.database.repositories

import com.hogent.android.entities.Contract
import com.hogent.android.entities.Project
import com.hogent.android.entities.VirtualMachine
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