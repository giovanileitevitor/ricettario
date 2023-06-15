package br.applabbs.ricettario.uiInventtario.home

import android.content.Intent
import android.os.Bundle
import android.provider.SyncStateContract
import android.text.TextUtils.isEmpty
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.applabbs.ricettario.BuildConfig
import br.applabbs.ricettario.aux.PermissionUtils
import br.applabbs.ricettario.aux.onDebouncedListener
import br.applabbs.ricettario.databinding.ActivityInventarioBinding
import br.applabbs.ricettario.domain.local.models.Registro
import br.applabbs.ricettario.uiInventtario.add.AddRegistroActivity
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import kotlin.math.exp

class InventarioActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInventarioBinding
    private val viewModel : InventarioViewModel by viewModel()
    private lateinit var registroAdapter: RegistroAdapter
    private var exportedList = arrayListOf<Registro>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInventarioBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        setupListeners()
        setupObservers()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllRegistros()
    }

    private fun setupPermissions(){
        PermissionUtils.verifyPermissions(this, 1)
    }

    private fun setupView(){
        viewModel.getAllRegistros()
    }

    private fun setupListeners(){
        binding.btnAdd.onDebouncedListener {
            val intent = Intent(this, AddRegistroActivity::class.java)
            startActivity(intent)
        }

        binding.btnSearch.onDebouncedListener {
            showToast(msg = "Function 'Search' not available yet !!!")
        }

        binding.btnExport.onDebouncedListener() {
            if(!exportedList.isNullOrEmpty() && exportedList.size != 0){
                createAndShareCSVfile(report = exportedList)
            }else{
                showToast(msg = "There is no data to export !!!")
            }
        }
    }

    private fun setupObservers(){
        viewModel.registros.observe(this){
            if(it.isNullOrEmpty()){
                binding.txtBottomBar.text = "Nenhum registro encontrado !!!"
            }else{
                setupRegistroRV(registros = it)
                binding.txtBottomBar.text = "Total de Registros: ${it.size.toString()}"
                exportedList = it
            }
        }

        viewModel.isError.observe(this){
            showToast(msg = it)
        }

        viewModel.isLoading.observe(this, Observer { isLoading ->
            binding.loadingBox.root.isVisible = isLoading
        })

        viewModel.isDeleted.observe(this){isDeleted ->
            if(isDeleted){
                viewModel.getAllRegistros()
            }
        }
    }

    private fun setupRegistroRV(registros: List<Registro>){
        val divider = DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL)
        binding.rvRegistros.addItemDecoration(divider)
        binding.rvRegistros.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        registroAdapter = RegistroAdapter(
            data = registros,
            mGlide = Glide.with(this),
            itemListener = singleClickStep,
            itemLongListener = singleLongClickStep
        )
        binding.rvRegistros.adapter = registroAdapter
    }

    private val singleClickStep = { registro: Registro ->
        showToast(msg = registro.productName)
    }

    private val singleLongClickStep = { registro: Registro ->
        viewModel.deleteRegistro(registro = registro)
    }

    private fun createAndShareCSVfile(report: List<Registro>){
        val authority = BuildConfig.APPLICATION_ID + PROVIDER
        val header = "ProductName, Brand, Qtd, Valid To, InputDate"
        val filename = "Inventtario.csv"
        val path = getExternalFilesDir(null)
        val fileOut = File(path, filename)
        fileOut.delete()
        fileOut.createNewFile()
        fileOut.appendText(header)
        fileOut.appendText("\n")

        for (i in report.indices){
            val productName = report[i].productName
            val productBrand = report[i].productBrand
            val qtdItens = report[i].qtd.toString()
            val validTo = report[i].productVality
            val inputDate = report[i].dateRegister
            val data = "$productName, $productBrand, $qtdItens, $validTo, $inputDate"
            fileOut.appendText(data)
            fileOut.appendText("\n")
        }

        val sendIntent = Intent(Intent.ACTION_SEND)
        val fileUri = FileProvider.getUriForFile(this, authority,fileOut )
        sendIntent.putExtra(Intent.EXTRA_STREAM, fileUri)
        sendIntent.type = "text/csv"
        startActivity(Intent.createChooser(sendIntent, "Export DataSheet"))
    }

    private fun showToast(msg: String){
        Toast.makeText(this, "$msg", Toast.LENGTH_SHORT).show()
    }

    companion object{
        private const val  PROVIDER = ".provider"
    }
}