package com.plannersaas.data.repository

import com.plannersaas.data.local.ProjectDao
import com.plannersaas.data.local.ProjectEntity
import kotlinx.coroutines.flow.Flow

class ProjectRepository(private val projectDao: ProjectDao) {
    val allProjects: Flow<List<ProjectEntity>> = projectDao.getAllProjects()

    suspend fun insert(project: ProjectEntity) = projectDao.insertProject(project)
    suspend fun update(project: ProjectEntity) = projectDao.updateProject(project)
    suspend fun delete(project: ProjectEntity) = projectDao.deleteProject(project)
    suspend fun getProjectById(id: Int) = projectDao.getProjectById(id)
}
