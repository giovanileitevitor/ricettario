package br.applabbs.ricettario.uiInventtario.add

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import br.applabbs.ricettario.aux.PermissionUtils
import br.applabbs.ricettario.databinding.ActivityAddRegistroBinding
import br.applabbs.ricettario.domain.local.models.Registro
import br.applabbs.ricettario.uiInventtario.home.InventarioViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.Calendar

class AddRegistroActivity: AppCompatActivity() {

    private lateinit var binding: ActivityAddRegistroBinding
    private val viewModel : InventarioViewModel by viewModel()
    private val REQUEST_CODE = 999
    private var globalDate: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupPermissions()
        setupView()
        setupListeners()
        setupObservers()
    }

    override fun onResume() {
        super.onResume()
    }

    private fun setupPermissions(){
        PermissionUtils.verifyPermissions(this, 1)
    }

    private fun setupView(){
        binding.edtNome.isFocusable = true
        val time = Calendar.getInstance().time
        val formatter = SimpleDateFormat("MM-dd-yyyy - HH:mm")
        val current = formatter.format(time)
        globalDate = current
        binding.edtDateRegister.setText(current)
        binding.edtDateRegister.isEnabled = false
    }

    private fun setupListeners(){
        binding.btnExit.setOnClickListener {
            super.onBackPressed()
        }

        binding.btnFoto.setOnClickListener{
            dialogAddFoto()
        }

        binding.btnAdd.setOnClickListener {
            val nameProduct = binding.edtNome.text.trim() ?: ""
            val brandProduct = binding.edtBrand.text.trim() ?: ""
            val quantity = binding.edtQtd.text.trim() ?: ""
            val validateProduct = binding.edtValidate.text.trim() ?: ""
            val dateRegister = binding.edtDateRegister.text.trim() ?: ""

            if(nameProduct.isNullOrEmpty() || brandProduct.isNullOrEmpty() || quantity.isNullOrEmpty()
                || validateProduct.isNullOrEmpty() || dateRegister.isNullOrEmpty()){
                showToast(msg = "All fields must be filled in.")
            }else{
                val registro = Registro(
                    idRegistro = 0,
                    productName = nameProduct.toString(),
                    productBrand = brandProduct.toString(),
                    qtd = quantity.toString(),
                    productVality = validateProduct.toString(),
                    dateRegister = dateRegister.toString(),
                    hasImage = false,
                    imageAddress = ""
                )
                viewModel.addRegistro(registro = registro)
            }
        }
    }

    private fun setupObservers(){
        viewModel.addedWithSuccess.observe(this){
            if(it){
                showToast("Item saved with success !!")
            }else{

            }
        }

        viewModel.isError.observe(this){
                showToast("$it")
        }
    }

    private fun dialogAddFoto(){
        if(!PermissionUtils.verifyPermissions(this, 999)){
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraIntent, REQUEST_CODE)
        } else{
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 999)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE && data != null){
            val imageFile = data.extras?.get("data") as Bitmap
            //viewModel.addNewFoto(imgAddress = viewModel.createImageFile(imageFile))
        }
    }

    private fun showToast(msg: String){
        Toast.makeText(this, "$msg", Toast.LENGTH_SHORT).show()
    }

}