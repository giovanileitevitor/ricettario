package br.applabbs.ricettario.uiInventtario.home

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.applabbs.ricettario.databinding.ActivityInventarioBinding
import br.applabbs.ricettario.domain.local.models.Registro
import br.applabbs.ricettario.uiInventtario.add.AddRegistroActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class InventarioActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInventarioBinding
    private val viewModel : InventarioViewModel by viewModel()
    private lateinit var registroAdapter: RegistroAdapter

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

    private fun setupView(){
        viewModel.getAllRegistros()
    }

    private fun setupListeners(){
        binding.btnAdd.setOnClickListener {
            val intent = Intent(this, AddRegistroActivity::class.java)
            startActivity(intent)
        }

        binding.btnSearch.setOnClickListener {

        }

        binding.btnExport.setOnClickListener {
            //val intent = Intent(this, ExportActivity::class.java)
            //startActivity(intent)
        }
    }

    private fun setupObservers(){
        viewModel.registros.observe(this){
            if(it.isEmpty()){
                binding.txtBottomBar.text = "Nenhum registro encontrado !!!"
            }else{
                setupRegistroRV(registros = it)
                binding.txtBottomBar.text = it.size.toString() + " registros"
            }
        }

        viewModel.isError.observe(this){
            showToast(msg = it)
        }
    }

    private fun setupRegistroRV(registros: List<Registro>){
        val divider = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        binding.rvRegistros.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.rvRegistros.addItemDecoration(divider)
        registroAdapter = RegistroAdapter(data = registros, singleClickStep)
        binding.rvRegistros.adapter = registroAdapter
    }

    private val singleClickStep = { registro: Registro ->
        showToast(msg = registro.productName)
    }

    private fun showToast(msg: String){
        Toast.makeText(this, "$msg", Toast.LENGTH_SHORT).show()
    }
}