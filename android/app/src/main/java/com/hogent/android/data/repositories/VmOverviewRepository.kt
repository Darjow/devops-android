package com.hogent.android.data.repositories

import com.hogent.android.data.entities.VirtualMachine
import com.hogent.android.network.dtos.responses.ProjectDetails
import com.hogent.android.network.dtos.responses.ProjectOverView
import com.hogent.android.network.services.ProjectApi
import com.hogent.android.util.AuthenticationManager
import timber.log.Timber

class VmOverviewRepository {

    private val projectApi = ProjectApi.retrofitService;


    suspend fun getProjects(): ProjectOverView?{
        val response = projectApi.getAll()

        if(response.isSuccessful){
            return response.body()!!;
        }
        return null;

    }

    suspend fun getById(id: Int): ProjectDetails?{
        Timber.d("Retrieving vms with project id: %d", id)
        val output = projectApi.getById(id)
        if(output.isSuccessful && output.body() != null){
            Timber.d("Request was succesfull, retrieved: %n%s", output.body())
            return output.body()!!;
        }
        Timber.d("Request sent an empty list: %n%s", output.body())
        return null;
    }
}