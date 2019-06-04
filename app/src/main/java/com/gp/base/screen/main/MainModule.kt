package com.gp.base.screen.main

import com.gp.base.di.ActivityScope
import com.gp.base.network.repository.ProjectRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class MainModule {

    @Provides
    @ActivityScope
    fun providesProjectRepository(retrofit: Retrofit): ProjectRepository {
        return ProjectRepository(retrofit)
    }
}