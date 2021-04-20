package com.jutt.citiessearchmvvm.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

abstract class AlertDialogFragment<C : AlertDialogInterface.Callback> : DialogFragment(),
    AlertDialogInterface<C> {

    override var callback: C? = null

    override fun onShowDialog(manager: FragmentManager, tag: String) {
        super.show(manager, tag)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = viewResourceId?.let { inflater.inflate(it, container, false) }
        ?: super.onCreateView(inflater, container, savedInstanceState)

}
