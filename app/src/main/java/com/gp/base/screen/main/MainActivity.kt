package com.gp.base.screen.main

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.gp.base.R
import com.gp.base.app.App
import com.gp.base.screen.base.BaseActivity
import com.gp.base.screen.deliveries.FragmentDeliveries
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

class MainActivity : BaseActivity<MainViewModel>() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DaggerMainComponent.builder()
            .appComponent((application as App).appComponent)
            .mainModule(MainModule())
            .build()
            .inject(this)

        val viewModel = ViewModelProviders.of(this)[DeliveryDetailsViewModel::class.java]

        setSupportActionBar(toolbar)
        supportFragmentManager.apply {
            beginTransaction().apply {
                replace(R.id.fragmentContainer, FragmentDeliveries(), FragmentDeliveries.TAG)
                    .commit()

            }
            addOnBackStackChangedListener {
                val count = backStackEntryCount
                if (count > 0) {
                    toolbar.setTitle(R.string.title_delivery_details)
                } else {
                    toolbar.setTitle(R.string.app_name)
                }

                supportActionBar?.setDisplayHomeAsUpEnabled(count > 0)
                supportActionBar?.setDisplayShowHomeEnabled(count > 0)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}