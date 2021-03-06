package com.jutt.moviesdetaildemo.core

import android.content.Context
import android.os.Bundle
import androidx.annotation.NonNull
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.jutt.moviesdetaildemo.R
import com.jutt.moviesdetaildemo.databinding.LayoutToolbarBinding
import com.jutt.moviesdetaildemo.extensions.setupActionBar
import com.jutt.moviesdetaildemo.helper.InternetConnectivityListener
import com.jutt.moviesdetaildemo.utils.SnackBarAnchorViewInterface
import com.jutt.moviesdetaildemo.utils.SnackBarInterface
import com.jutt.moviesdetaildemo.utils.SnackBarViewHandler
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.disposables.CompositeDisposable
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import timber.log.Timber
abstract class AppSupportActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks,
    SnackBarInterface, SnackBarAnchorViewInterface by SnackBarAnchorViewInterface.RootViewSnackBar,
    SnackBarViewHandler by SnackBarViewHandler.NoInternetSnackBar {

    private var toolbarBinding: LayoutToolbarBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerConnectivityListener()

    }

    override fun setTitle(title: CharSequence?) {
        super.setTitle(title)
        toolbarBinding?.toolbarTitle?.text = title
    }

    override fun setTitle(titleId: Int) {
        super.setTitle(titleId)
        toolbarBinding?.toolbarTitle?.text = getString(titleId)
    }

    private fun registerConnectivityListener() {
        InternetConnectivityListener(applicationContext).apply {
            lifecycle.addObserver(this)
        }.networkStateObserver.observe(this, { isConnected ->
            if (!isConnected) {
                showSnackbar(this)
            } else {
                dismissSnackbar()
            }
        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    fun getActivity(): AppCompatActivity {
        return this
    }

    fun getContext(): Context {
        return this
    }

    override fun onPermissionsGranted(requestCode: Int, @NonNull perms: List<String>) {
        Timber.i("Following permissions have been granted: $perms")
    }

    override fun onPermissionsDenied(requestCode: Int, @NonNull perms: List<String>) {
        Timber.i("Following permissions have been denied: $perms")
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        }
    }

    protected fun setUpToolBar(
        toolbarBinding: LayoutToolbarBinding,
        showDefaultTitle: Boolean = false,
        showBackArrowOrHome: Boolean = false,
        @StringRes titleRes: Int = R.string.empty
    ) {
        this.toolbarBinding = toolbarBinding
        setupActionBar(toolbarBinding.toolbar) {
            setDisplayShowTitleEnabled(showDefaultTitle)
            setDisplayHomeAsUpEnabled(showBackArrowOrHome)
        }.apply {
            setTitle(titleRes)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}