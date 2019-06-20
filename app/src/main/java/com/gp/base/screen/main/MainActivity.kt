package com.gp.base.screen.main

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gp.base.R
import com.gp.base.app.App
import com.gp.base.network.model.ApiResponse
import com.gp.base.network.model.Delivery
import com.gp.base.screen.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<MainViewModel>() {

    private val adapter = DeliveryAdapter()
    private var isFetchingData = false
    private var endReached = false

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_main)
        super.onCreate(savedInstanceState)
        viewModel.init(application as App)
        getDeliveries()
    }

    override fun initView() {
        rvDeliveries.adapter = adapter
        rvDeliveries.layoutManager = LinearLayoutManager(this)
        setRecyclerViewScrollListener()
    }

    private val lastVisibleItemPosition: Int
        get() = (rvDeliveries.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()

    private fun setRecyclerViewScrollListener() {
        rvDeliveries.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val totalItemCount = recyclerView.layoutManager!!.itemCount
                if (!endReached && !isFetchingData && totalItemCount == lastVisibleItemPosition + 1) {
                    getDeliveries(lastVisibleItemPosition)
                }
            }
        })
    }

    private fun getDeliveries(offset: Int = 0) {
        isFetchingData = true
        viewModel.getProjectList(offset).observe(
            this,
            Observer<ApiResponse<List<Delivery>>> {
                response ->
                isFetchingData = false
                if (response.throwable != null) {
                    Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show()
                } else {
                    endReached = response.data!!.isEmpty()
                    adapter.addItems(response.data!!)
                }
            }
        )
    }
}