package com.jutt.citiessearchmvvm.architecture

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.jutt.citiessearchmvvm.R
import com.jutt.citiessearchmvvm.helper.AlertDialogParams
import com.jutt.citiessearchmvvm.view.dialogs.AlertMessageDialog

class ErrorMessageObserver(
    private val activity: AppCompatActivity,
    private val onOkListener: (() -> Unit)? = null,
    private val onCancelListener: (() -> Unit)? = null,
    private val fallbackErrorMessage: String = activity.getString(R.string.error_message_server_error)
) : Observer<Event<AlertDialogParams>> {
    override fun onChanged(event: Event<AlertDialogParams>?) {
        val params = event?.getContentIfNotHandled() ?: return
        val message: String =
            if (!params.message.isNullOrBlank()) params.message
            else fallbackErrorMessage

        AlertMessageDialog.newInstance(
            title = params.title,
            message = message,
            isCancelable = params.isCancelable,
            positiveButton = if (params.showPositiveButton) {
                if (params.positiveButtonText.isNullOrBlank()) {
                    activity.getString(R.string.ok)
                } else {
                    params.positiveButtonText
                }
            } else null,
            negativeButton = if (params.showNegativeButton) {
                if (params.negativeButtonText.isNullOrBlank()) {
                    activity.getString(R.string.cancel)
                } else {
                    params.negativeButtonText
                }
            } else null
        ).showDialog(activity.supportFragmentManager, object : AlertMessageDialog.Callback {
            override fun onSuccess() {
                onOkListener?.invoke()
            }

            override fun onCancel() {
                onCancelListener?.invoke()
            }

            override fun onNeutral() {
            }
        })
    }
}
