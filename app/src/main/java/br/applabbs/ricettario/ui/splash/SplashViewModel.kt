package br.applabbs.ricettario.ui.splash

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.applabbs.ricettario.aux.PermissionUtils
import kotlinx.coroutines.launch

class SplashViewModel: ViewModel() {

    private val _next : MutableLiveData<Boolean> = MutableLiveData()
    val next: LiveData<Boolean> = _next

    private val _isPermissionsAccepted = MutableLiveData<Boolean>()
    val isPermissionAccepted : LiveData<Boolean> = _isPermissionsAccepted

    fun getWaitProcess(){
        viewModelScope.launch {
            android.os.Handler().postDelayed({
                _next.postValue(true)
            }, 100)
        }
    }

    fun isPermissionConceded(context: Activity, permissionCode: Int){
        viewModelScope.launch {
            _isPermissionsAccepted.postValue(
                PermissionUtils.verifyPermissions(context, permissionCode)
            )
        }
    }

}