package br.applabbs.ricettario.aux

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import kotlin.collections.ArrayList

class PermissionUtils {

    companion object{

        private fun hasPermission(activity: Activity, permission: String) : Boolean{
            return ActivityCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED
        }

        private fun hasPermission(context: Context, permission: String) : Boolean {
            return ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
        }

        fun verifyPermissions(activity: Activity, requestCode: Int) : Boolean {

            var permissions: ArrayList<String> = ArrayList()

            if(!hasPermission(activity, Manifest.permission.INTERNET)) {
                permissions.add(Manifest.permission.INTERNET)
            }

            if(!hasPermission(activity, Manifest.permission.ACCESS_NETWORK_STATE)) {
                permissions.add(Manifest.permission.ACCESS_NETWORK_STATE)
            }

            if(!hasPermission(activity, Manifest.permission.WAKE_LOCK)){
                permissions.add(Manifest.permission.WAKE_LOCK)
            }

            if(!hasPermission(activity, Manifest.permission.CALL_PHONE)){
                permissions.add(Manifest.permission.CALL_PHONE)
            }

            if(!hasPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE)){
                permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE)
            }

            if(!hasPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }

            if(!hasPermission(activity, Manifest.permission.CAMERA)){
                permissions.add(Manifest.permission.CAMERA)
            }

            if(!hasPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)) {
                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION)
            }

            if(!hasPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION)
            }

            /*
            Added all permissions conditions here
             */

            if (!permissions.isEmpty()) {
                ActivityCompat.requestPermissions(
                    activity,
                    permissions.toArray(arrayOfNulls<String>(permissions.size)),
                    requestCode
                )
                return false
            }

            return true
        }
    }

}
