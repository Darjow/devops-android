package com.hogent.android.data.repositories

import com.hogent.android.data.entities.Project
import com.hogent.android.data.entities.VirtualMachine
import com.hogent.android.network.services.ProjectApi
import com.hogent.android.network.services.VirtualMachineApi

class VmOverviewRepository {

    private val vmApi = VirtualMachineApi.retrofitService;
    private val projectApi = ProjectApi.retrofitService;


    suspend fun getByCustomerId(id: Int): List<Project>?{
        return projectApi.getByCustomerId(id)

    }

    suspend fun getByProjectId(id: Int): List<VirtualMachine>?{
        return vmApi.getByProjectId(id)
    }
}