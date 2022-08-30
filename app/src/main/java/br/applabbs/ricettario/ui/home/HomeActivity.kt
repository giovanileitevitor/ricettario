package br.applabbs.ricettario.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import br.applabbs.ricettario.aux.onDebouncedListener
import br.applabbs.ricettario.databinding.ActivityMainBinding
import org.koin.android.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity() {

    private val viewModel: HomeViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        setupListeners()
        setupObservers()
    }

    private fun setupView(){
        viewModel.getAllReceitas()
        viewModel.getAllSuggestedReceitas()
    }

    private fun setupListeners(){
        binding.btnHomeIc.onDebouncedListener {
            showToast("botao de home")
        }

        binding.btnAddIc.onDebouncedListener {
            showToast("botao de adicionar")
        }

        binding.btnConfigIc.onDebouncedListener {
            showToast("botao de configurar")
        }

    }

    private fun setupObservers(){

    }

    private fun showToast(msg: String){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    private fun showMessageBottomBar(msg: String){
        binding.txtBottomBar.text = msg
    }
}