package com.gp.base.network.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gp.base.network.model.Project
import com.gp.base.network.service.GithubService
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import timber.log.Timber

class ProjectRepository(retrofit: Retrofit) : ProjectRepositoryContract {

    private var githubService = retrofit.create(GithubService::class.java)

    private var disposable = CompositeDisposable()

    override fun getProjectList(user: String): LiveData<List<Project>> {
        var mutableLiveData = MutableLiveData<List<Project>>()
        disposable.add(
            githubService
                .getProjectList(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    mutableLiveData.postValue(it)
                }
        )

        return mutableLiveData
    }
}