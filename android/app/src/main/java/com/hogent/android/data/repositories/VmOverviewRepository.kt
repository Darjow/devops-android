package com.hogent.android.data.repositories

import com.hogent.android.data.entities.Project
import com.hogent.android.data.entities.VirtualMachine
import com.hogent.android.network.services.ProjectApi
import com.hogent.android.network.services.VirtualMachineApi
import retrofit2.Response
import timber.log.Timber

class VmOverviewRepository {

    private val vmApi = VirtualMachineApi.retrofitService;
    private val projectApi = ProjectApi.retrofitService;


    suspend fun getByCustomerId(id: Int): List<Project>?{
        Timber.d("Retrieving projects with id: %d", id)
        return projectApi.getByCustomerId(id)

    }

    suspend fun getByProjectId(id: Int): List<VirtualMachine>?{
        Timber.d("Retrieving vms with project id: %d", id)
        val output : Response<List<VirtualMachine>?> = vmApi.getByProjectId(id)
        if(output.isSuccessful && output.body() != null){
            Timber.d("Request was succesfull, retrieved: %n%s", output.body())
            return output.body()!!;
        }
        Timber.d("Request sent an empty list: %n%s", output.body())
        return null;
    }
}