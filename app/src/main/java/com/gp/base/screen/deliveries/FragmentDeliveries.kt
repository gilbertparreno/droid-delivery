package com.gp.base.screen.deliveries

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gp.base.R
import com.gp.base.app.App
import com.gp.base.network.model.ApiResponse
import com.gp.base.network.model.Delivery
import com.gp.base.screen.base.BaseFragment
import com.gp.base.screen.main.DaggerDeliveriesComponent
import com.gp.base.screen.main.DeliveriesModule
import com.gp.base.screen.main.DeliveriesViewModel
import io.reactivex.functions.Action
import kotlinx.android.synthetic.main.fragment_deliveries.*
import timber.log.Timber
import javax.inject.Inject

class FragmentDeliveries : BaseFragment<DeliveriesViewModel>(), DeliveryAdapter.OnItemClickListener {

    private val adapter = DeliveryAdapter(this)
    private var isFetchingData = false
    private var endReached = false

    private lateinit var viewModel: DeliveriesViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private var offset = 0

    override fun onAttach(context: Context) {
        super.onAttach(context)

        val activity = context as Activity

        DaggerDeliveriesComponent.builder()
            .appComponent((activity.application as App).appComponent)
            .deliveriesModule(DeliveriesModule())
            .build()
            .inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory)[DeliveriesViewModel::class.java]
        Timber.d(viewModel.toString())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return container?.inflate(R.layout.fragment_deliveries)
    }

    override fun initView() {
        rvDeliveries.adapter = adapter
        rvDeliveries.layoutManager = LinearLayoutManager(context)
        setRecyclerViewScrollListener()

        viewModel.liveDataProjectList.observe(
            this,
            Observer<ApiResponse<List<Delivery>>> { response ->
                isFetchingData = false
                if (response.throwable != null) {
                    showErrorDialog(positive = Action {
                        getDeliveries(offset)
                    }, negative = Action {
                        activity?.finish()
                    }, cancellable = false)
                } else {
                    endReached = response.data!!.isEmpty()
                    adapter.addItems(response.data!!)
                }
            }
        )

        getDeliveries()
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
        this.offset = offset
        viewModel.getProjectList(offset)
    }

    override fun onItemClick(item: Delivery) {
        activity?.supportFragmentManager!!.beginTransaction().apply {
            add(R.id.fragmentContainer, FragmentDeliveryDetails.newInstance(item), FragmentDeliveryDetails.TAG)
                .addToBackStack(FragmentDeliveryDetails.TAG)
                .commit()
        }
    }

    companion object {
        val TAG = this::class.java.simpleName
    }
}