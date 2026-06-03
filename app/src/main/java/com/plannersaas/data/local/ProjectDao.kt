package com.plannersaas.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ProjectDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProject(project: ProjectEntity)

    @Update
    suspend fun updateProject(project: ProjectEntity)

    @Delete
    suspend fun deleteProject(project: ProjectEntity)

    @Query("SELECT * FROM projects ORDER BY id DESC")
    fun getAllProjects(): Flow<List<ProjectEntity>>

    @Query("SELECT * FROM projects WHERE id = :id")
    suspend fun getProjectById(id: Int): ProjectEntity?
}
