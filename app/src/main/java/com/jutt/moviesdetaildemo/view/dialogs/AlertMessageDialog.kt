package com.jutt.moviesdetaildemo.view.dialogs

import android.app.Dialog
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.core.view.isVisible
import androidx.core.widget.ImageViewCompat
import com.jutt.moviesdetaildemo.R
import com.jutt.moviesdetaildemo.core.AlertDialogFragment
import com.jutt.moviesdetaildemo.core.AlertDialogInterface
import com.jutt.moviesdetaildemo.databinding.DialogFragmentAlertMessageBinding

class AlertMessageDialog : AlertDialogFragment<AlertMessageDialog.Callback>() {
    private lateinit var binding: DialogFragmentAlertMessageBinding

    interface Callback : AlertDialogInterface.Callback {
        fun onSuccess()
        fun onCancel()
        fun onNeutral()
    }

    companion object {
        private const val ARG_TITLE: String = "ARG_TITLE"
        private const val ARG_MESSAGE: String = "ARG_MESSAGE"
        private const val ARG_POSITIVE_BUTTON: String = "ARG_POSITIVE_BUTTON"
        private const val ARG_NEGATIVE_BUTTON: String = "ARG_NEGATIVE_BUTTON"
        private const val ARG_NEUTRAL_BUTTON: String = "ARG_NEUTRAL_BUTTON"
        private const val ARG_CANCELABLE: String = "ARG_CANCELABLE"
        private const val ARG_SHOW_TOP_ICON: String = "ARG_SHOW_TOP_ICON"
        private const val ARG_TOP_ICON_RES: String = "ARG_TOP_ICON_RES"
        private const val ARG_TOP_ICON_TINT: String = "ARG_TOP_ICON_TINT"

        fun newInstance(
                title: String? = null,
                message: String? = null,
                positiveButton: String? = null,
                negativeButton: String? = null,
                neutralButton: String? = null,
                isCancelable: Boolean = true,
                showTopIcon: Boolean = false,
                @DrawableRes topIconRes: Int? = null,
                @ColorInt topIconTint: Int? = null
        ): AlertMessageDialog = AlertMessageDialog().apply {
            arguments = Bundle().apply {
                putString(ARG_TITLE, title)
                putString(ARG_MESSAGE, message)
                putString(ARG_POSITIVE_BUTTON, positiveButton)
                putString(ARG_NEGATIVE_BUTTON, negativeButton)
                putString(ARG_NEUTRAL_BUTTON, neutralButton)
                putBoolean(ARG_CANCELABLE, isCancelable)
                putBoolean(ARG_SHOW_TOP_ICON, showTopIcon)

                topIconRes?.let {
                    putInt(ARG_TOP_ICON_RES, it)
                }

                topIconTint?.let {
                    putInt(ARG_TOP_ICON_TINT, it)
                }
            }
        }
    }

    override val viewResourceId: Int get() = R.layout.dialog_fragment_alert_message

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = DialogFragmentAlertMessageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).also {
            it.window?.apply {
                setBackgroundDrawableResource(R.drawable.background_alert_dialog)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = arguments?.getBoolean(ARG_CANCELABLE) ?: true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.topImage.isVisible = arguments?.getBoolean(ARG_SHOW_TOP_ICON) ?: false
        binding.topImage.setImageResource(
                arguments?.getInt(ARG_TOP_ICON_RES) ?: R.drawable.ic_rounded_tick
        )

        arguments?.getInt(ARG_TOP_ICON_TINT)?.let {
            ImageViewCompat.setImageTintList(view.findViewById(R.id.top_image), ColorStateList.valueOf(it))
        }


        binding.title.text = arguments?.getString(ARG_TITLE)
        binding.title.isVisible = binding.title.text.isNotEmpty()
        binding.message.text = arguments?.getString(ARG_MESSAGE)

        binding.positiveButton.text = arguments?.getString(ARG_POSITIVE_BUTTON)
        binding.positiveButton.isVisible = binding.positiveButton.text.isNotEmpty()
        binding.positiveButton.setOnClickListener {
            dismiss()
            callback?.onSuccess()
        }

        binding.negativeButton.text = arguments?.getString(ARG_NEGATIVE_BUTTON)
        binding.negativeButton.isVisible = binding.negativeButton.text.isNotEmpty()
        binding.negativeButton.setOnClickListener {
            dismiss()
        }

        binding.neutralButton.text = arguments?.getString(ARG_NEUTRAL_BUTTON)
        binding.neutralButton.isVisible = binding.neutralButton.text.isNotEmpty()
        binding.neutralButton.setOnClickListener {
            dismiss()
        }
    }

}
