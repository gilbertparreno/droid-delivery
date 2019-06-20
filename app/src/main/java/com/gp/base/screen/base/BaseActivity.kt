package com.gp.base.screen.base

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.Bundle
import java.lang.reflect.ParameterizedType


abstract class BaseActivity<T : ViewModel> : AppCompatActivity() {

    protected lateinit var viewModel: T

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(
            (javaClass
                .genericSuperclass as ParameterizedType)
                .actualTypeArguments[0] as Class<T>
        )
        lifecycle.addObserver(viewModel as LifecycleObserver)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initViewModel()
    }

    abstract fun initView()
}