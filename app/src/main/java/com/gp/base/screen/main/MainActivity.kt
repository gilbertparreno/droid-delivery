package com.gp.base.screen.main

import android.os.Bundle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.Observer
import com.gp.base.R
import com.gp.base.app.App
import com.gp.base.network.model.Project
import com.gp.base.network.service.GithubService
import com.gp.base.screen.base.BaseActivity
import timber.log.Timber

class MainActivity : BaseActivity<MainViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel.init(application as App)
        viewModel.getProjectList().observe(this,
            Observer<List<Project>> { t -> Timber.d(t.toString()) })
    }
}
