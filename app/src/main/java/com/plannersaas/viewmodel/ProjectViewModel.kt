package com.plannersaas.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.plannersaas.data.datastore.ThemePreferences
import com.plannersaas.data.local.AppDatabase
import com.plannersaas.data.local.ProjectEntity
import com.plannersaas.data.repository.ProjectRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ProjectViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ProjectRepository
    private val themePreferences = ThemePreferences(application)

    // Estado dos Projetos
    private val _projects = MutableStateFlow<List<ProjectEntity>>(emptyList())
    val projects: StateFlow<List<ProjectEntity>> = _projects.asStateFlow()

    // Estado das Preferências (Tema)
    val darkMode = themePreferences.darkModeFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    val themeColor = themePreferences.themeColorFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = "Blue"
    )

    val dynamicColors = themePreferences.dynamicColorsFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = false
    )

    val language = themePreferences.languageFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = "pt"
    )

    init {
        val dao = AppDatabase.getDatabase(application).projectDao()
        repository = ProjectRepository(dao)
        
        // Coleta contínua dos projetos do banco de dados
        viewModelScope.launch {
            repository.allProjects.collect { 
                _projects.value = it 
            }
        }
    }

    // Operações de Projeto
    fun addProject(
        title: String,
        description: String,
        priority: String,
        status: String,
        imageUri: String? = null,
        dueDate: Long? = null
    ) {
        viewModelScope.launch {
            repository.insert(
                ProjectEntity(
                    title = title,
                    description = description,
                    priority = priority,
                    status = status,
                    imageUri = imageUri,
                    dueDate = dueDate
                )
            )
        }
    }

    fun updateProject(project: ProjectEntity) = viewModelScope.launch { 
        repository.update(project) 
    }

    fun deleteProject(project: ProjectEntity) = viewModelScope.launch { 
        repository.delete(project) 
    }
    
    suspend fun getProjectById(id: Int): ProjectEntity? {
        return repository.getProjectById(id)
    }

    // Operações de Tema
    fun setDarkMode(isDarkMode: Boolean?) {
        viewModelScope.launch {
            themePreferences.saveDarkMode(isDarkMode)
        }
    }

    fun setThemeColor(colorName: String) {
        viewModelScope.launch {
            themePreferences.saveThemeColor(colorName)
        }
    }

    fun setDynamicColors(enabled: Boolean) {
        viewModelScope.launch {
            themePreferences.saveDynamicColors(enabled)
        }
    }

    fun setLanguage(languageCode: String) {
        viewModelScope.launch {
            themePreferences.saveLanguage(languageCode)
        }
    }
}
