package com.hogent.android.data.repositories

import com.hogent.android.network.dtos.responses.ProjectDetails
import com.hogent.android.network.dtos.responses.ProjectOverView
import com.hogent.android.network.services.ProjectApi.projectApi
import com.hogent.android.util.TimberUtils

class VmOverviewRepository {

    suspend fun getProjects(): ProjectOverView?{
        val response = projectApi.getAll()
        TimberUtils.logRequest(response)

        if(response.isSuccessful){
            return response.body()!!;
        }
        return null;

    }

    suspend fun getById(id: Int): ProjectDetails?{
        val output = projectApi.getById(id)
        TimberUtils.logRequest(output);

        if(output.isSuccessful && output.body() != null){
            return output.body()!!;
        }
        return null;
    }
}