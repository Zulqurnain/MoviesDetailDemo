package com.jutt.citiessearchmvvm.core

import android.content.Context
import androidx.fragment.app.FragmentManager

interface AlertDialogInterface<C : AlertDialogInterface.Callback> {

    interface Callback

    val viewResourceId: Int?
    var callback: C?

    fun onShowDialog(manager: FragmentManager, tag: String)

    fun showDialog(manager: FragmentManager, context: Context) {
        @Suppress("UNCHECKED_CAST")
        showDialog(manager = manager, callback = context as? C)
    }

    fun showDialog(manager: FragmentManager, callback: C? = null) {
        try {
            onShowDialog(manager, javaClass.simpleName)
            this.callback = callback
        }catch (e:Exception){ }
    }

}
