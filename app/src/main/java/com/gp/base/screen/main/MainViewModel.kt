package com.gp.base.screen.main

import androidx.lifecycle.*
import com.gp.base.app.App
import com.gp.base.network.model.Project
import com.gp.base.network.repository.ProjectRepository
import retrofit2.Retrofit
import timber.log.Timber
import javax.inject.Inject

class MainViewModel : ViewModel(),
    LifecycleObserver {

    @Inject
    lateinit var projectRepository: ProjectRepository

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        // TODO onCreate()
    }

    fun init(app: App) {
        DaggerMainComponent.builder()
            .appComponent(app.appComponent)
            .mainModule(MainModule())
            .build()
            .inject(this)
    }

    fun getProjectList() : LiveData<List<Project>> {
        return projectRepository.getProjectList("JakeWharton")
    }
}
