package com.gp.base.screen.deliveries

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.gp.base.R
import com.gp.base.app.App
import com.gp.base.network.model.Delivery
import com.gp.base.screen.base.BaseFragment
import com.gp.base.screen.main.DaggerDeliveryDetailsComponent
import com.gp.base.screen.main.DeliveryDetailsModule
import com.gp.base.screen.main.DeliveryDetailsViewModel
import com.gp.base.utils.CircleTransform
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_delivery_details.*
import timber.log.Timber
import java.util.*
import javax.inject.Inject


class FragmentDeliveryDetails : BaseFragment<DeliveryDetailsViewModel>(), OnMapReadyCallback {

    private lateinit var delivery: Delivery

    private lateinit var viewModel: DeliveryDetailsViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context as AppCompatActivity

        DaggerDeliveryDetailsComponent.builder()
            .appComponent((context.application as App).appComponent)
            .deliveryDetailsModule(DeliveryDetailsModule())
            .build()
            .inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments == null || !arguments!!.containsKey(ARG_DELIVERY)) {
            throw IllegalAccessException("Call FragmentDeliveryDetails.newInstance() to initialize properly.")
        }

        delivery = arguments!!.getParcelable(ARG_DELIVERY)

        viewModel = ViewModelProviders.of(this)[DeliveryDetailsViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return container?.inflate(R.layout.fragment_delivery_details)
    }

    override fun initView() {
        val mapFragment = SupportMapFragment.newInstance()
        mapFragment.getMapAsync(this)
        childFragmentManager.beginTransaction().apply {
            replace(R.id.mapContainer, mapFragment)
                .commit()
        }
        val sheetBehavior = BottomSheetBehavior.from(bottom_sheet)

        Picasso.get().load(delivery.imageUrl).transform(CircleTransform()).resize(100, 100).centerCrop().into(imgDelivery)
    }

    override fun onMapReady(map: GoogleMap?) {
        val latLng = LatLng(delivery.location!!.lat, delivery.location!!.lng)
        map?.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))

        val addresses: List<Address>
        val geocoder = Geocoder(context, Locale.getDefault())

        addresses = geocoder.getFromLocation(
            latLng.latitude,
            latLng.longitude,
            1
        )

        val address = addresses[0].getAddressLine(0)

        tvAddress.text = address
        tvDeliveryDetails.text = delivery.description
        map?.addMarker(MarkerOptions().position(latLng).title(address))
    }

    companion object {

        val TAG = this::class.java.simpleName
        val ARG_DELIVERY = "delivery"

        fun newInstance(delivery: Delivery): FragmentDeliveryDetails {
            return FragmentDeliveryDetails().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_DELIVERY, delivery)
                }
            }
        }
    }
}