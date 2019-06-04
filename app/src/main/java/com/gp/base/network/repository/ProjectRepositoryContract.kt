package com.gp.base.network.repository

import androidx.lifecycle.LiveData
import com.gp.base.network.model.Project

interface ProjectRepositoryContract {
    fun getProjectList(user: String): LiveData<List<Project>>
}