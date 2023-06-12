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

    val registros: LiveData<List<Registro>> get() = _registros
    private val _registros: MutableLiveData<List<Registro>> = MutableLiveData()

    val isError: LiveData<String> get() = _isError
    private val _isError: MutableLiveData<String> = MutableLiveData()

    val addedWithSuccess: LiveData<Boolean> get() = _addedWithSuccess
    private val _addedWithSuccess: MutableLiveData<Boolean> = MutableLiveData()


    fun getAllRegistros(){
        viewModelScope.launch {
            try {
                //_registros.postValue(inventarioUseCase.getAllRegistros())
                _registros.postValue(geradorRegistros(qtd = randomicInteger()))
            }catch (ex: Exception){
                _isError.postValue("Fail on DB Process")
            }
        }
    }

    fun addRegistro(registro: Registro){
        viewModelScope.launch {
            try {
                inventarioUseCase.addRegistro(registro = registro)
                _addedWithSuccess.postValue(true)
            }catch (ex: Exception){
                _isError.postValue("Fail on DB Process")
                _addedWithSuccess.postValue(false)
            }
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

}