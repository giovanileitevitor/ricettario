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
import androidx.recyclerview.widget.GridLayoutManager
import br.applabbs.ricettario.aux.DateTextWatcher
import br.applabbs.ricettario.aux.PermissionUtils
import br.applabbs.ricettario.aux.hideKeyboard
import br.applabbs.ricettario.databinding.ActivityAddRegistroBinding
import br.applabbs.ricettario.domain.local.models.Registro
import br.applabbs.ricettario.ui.adicionar.AdicionarActivity
import br.applabbs.ricettario.ui.adicionar.FotosAdapter
import br.applabbs.ricettario.uiInventtario.home.InventarioViewModel
import com.bumptech.glide.Glide
import kotlinx.coroutines.delay
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.Calendar

class AddRegistroActivity: AppCompatActivity() {

    private lateinit var binding: ActivityAddRegistroBinding
    private val viewModel : InventarioViewModel by viewModel()
    private val REQUEST_CODE = 999
    private var globalDate: String = ""
    private var imageAddress: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupPermissions()
        setupView()
        setupListeners()
        setupObservers()
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
        binding.edtValidate.addTextChangedListener(DateTextWatcher(binding.edtValidate))
        binding.btnFoto.bringToFront()
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
            val validateProduct = binding.edtValidate.text ?: ""
            val dateRegister = binding.edtDateRegister.text.trim() ?: ""
            val imageAddress = imageAddress ?: ""

            if(nameProduct.isNullOrEmpty() || brandProduct.isNullOrEmpty()
                || quantity.isNullOrEmpty() || validateProduct.isNullOrEmpty()
                || dateRegister.isNullOrEmpty() || imageAddress.isNullOrEmpty() ){
                showToast(msg = "All fields must be filled in.")
            }else{
                val registro = Registro(
                    idRegistro = null,
                    productName = nameProduct.toString(),
                    productBrand = brandProduct.toString(),
                    qtd = quantity.toString(),
                    productVality = validateProduct.toString(),
                    dateRegister = dateRegister.toString(),
                    hasImage = true,
                    imageAddress = imageAddress
                )
                viewModel.addRegistro(registro = registro)
                hideKeyboard()
            }
        }
    }

    private fun setupObservers(){
        viewModel.isSuccess.observe(this){
            if(it){
                showToast("Item saved with success !!")
                finish()
            }
        }

        viewModel.isError.observe(this){
            showToast("$it")
            finish()
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
            imageAddress = viewModel.createImageFile(imageFile)
            binding.imgMainRegistro.setImageBitmap(imageFile)
            binding.btnFoto.bringToFront()
        }
    }

    private fun showToast(msg: String){
        Toast.makeText(this, "$msg", Toast.LENGTH_SHORT).show()
    }

}