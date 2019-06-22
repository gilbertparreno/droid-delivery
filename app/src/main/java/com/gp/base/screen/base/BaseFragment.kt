package com.gp.base.screen.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.gp.base.R
import io.reactivex.functions.Action

abstract class BaseFragment : Fragment() {

    protected fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
        return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
    }

    abstract fun initView()

    final override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    protected fun showErrorDialog(
        message: String = getString(R.string.error_generic),
        positiveLabel: String = getString(android.R.string.yes),
        positive: Action,
        negativeLabel: String = getString(android.R.string.no),
        negative: Action? = null,
        cancellable: Boolean = true
    ) {
        val builder = AlertDialog.Builder(context!!)
        builder.setMessage(message)
        builder.setPositiveButton(positiveLabel) { dialog, _ ->
            dialog.dismiss()
            positive.run()
        }
        builder.setNegativeButton(negativeLabel) { dialog, _ ->
            dialog.dismiss()
            negative?.run()
        }
        builder.setCancelable(cancellable)
        builder.show()
    }
}