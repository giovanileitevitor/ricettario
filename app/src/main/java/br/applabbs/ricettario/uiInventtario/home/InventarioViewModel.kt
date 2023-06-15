package br.applabbs.ricettario.uiInventtario.home

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.os.Environment
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.applabbs.ricettario.aux.geradorRegistros
import br.applabbs.ricettario.aux.randomicInteger
import br.applabbs.ricettario.domain.local.models.Registro
import br.applabbs.ricettario.domain.local.usecases.InventarioUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.lang.Exception
import java.util.UUID

class InventarioViewModel(
    private val context: Context,
    private val inventarioUseCase: InventarioUseCase
): ViewModel() {

    val registros: LiveData<ArrayList<Registro>> get() = _registros
    private val _registros: MutableLiveData<ArrayList<Registro>> = MutableLiveData()

    val isError: LiveData<String> get() = _isError
    private val _isError: MutableLiveData<String> = MutableLiveData()

    val isSuccess: LiveData<Boolean> get() = _isSuccess
    private val _isSuccess: MutableLiveData<Boolean> = MutableLiveData()

    val isDeleted: LiveData<Boolean> get() = _isDeleted
    private val _isDeleted: MutableLiveData<Boolean> = MutableLiveData()

    val isLoading: LiveData<Boolean> get() = _isLoading
    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()


    fun getAllRegistros(){
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _registros.value = inventarioUseCase.getAllRegistros()
                delay(MININUM_TIME)
            }catch (ex: Exception){
                _isError.value = "Fail on DB Process"
            }
            _isLoading.value = false
        }
    }

    fun addRegistro(registro: Registro){
        viewModelScope.launch {
            _isLoading.value = true
            try {
                inventarioUseCase.addRegistro(registro = registro)
                _isSuccess.value = true
                delay(MININUM_TIME)
            }catch (ex: Exception){
                _isError.value = "Fail on DB Process"
            }
            _isLoading.value = false
        }
    }

    fun deleteRegistro(registro : Registro){
        viewModelScope.launch {
            _isLoading.value = true
            try {
                inventarioUseCase.deleteRegistro(registro = registro)
                _isDeleted.value = true
                delay(MININUM_TIME)
            }catch (ex: Exception){
                _isError.value = "Fail on DB Process"
            }
            _isLoading.value = false
        }
    }

    fun createImageFile(imageId: Bitmap): String {
        val wrapper = ContextWrapper(context)
        val file = wrapper.getExternalFilesDir("/")?.absolutePath.toString()
        val arquive = File(file, "${UUID.randomUUID()}.jpg")

        try {
            val stream : OutputStream = FileOutputStream(arquive)
            imageId.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            stream.flush()
            stream.close()
        } catch (e: IOException){ e.printStackTrace() }
        return arquive.absolutePath
    }

    private fun deleteImage(path: String) {
        val fDelete = File(path)
        if (fDelete.exists()) {
            if (fDelete.delete()) {
                MediaScannerConnection.scanFile(context, arrayOf(Environment.getExternalStorageDirectory().toString()), null) { path, uri ->
                    Log.d("debug", "DONE")
                }
            }
        }
    }

    companion object{
        private const val MININUM_TIME = 500L
    }

}