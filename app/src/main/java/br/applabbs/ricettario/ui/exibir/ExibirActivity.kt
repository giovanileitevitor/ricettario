package br.applabbs.ricettario.ui.exibir

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import br.applabbs.ricettario.aux.onDebouncedListener
import br.applabbs.ricettario.databinding.ActivityExibirBinding
import br.applabbs.ricettario.domain.local.models.Foto
import br.applabbs.ricettario.ui.adicionar.FotosAdapter
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.viewModel

class ExibirActivity : AppCompatActivity(){

    private val viewModel : ExibirViewModel by viewModel()
    private lateinit var binding: ActivityExibirBinding
    private lateinit var fotosAdapter: FotosAdapter
    private var idReceita : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExibirBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        setupListeners()
        setupObservers()
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    private fun setupView(){
        idReceita = intent.getIntExtra("idReceita", 0) ?: 0
        viewModel.getFotosReceita(idReceita = idReceita)
    }

    private fun setupListeners(){
        binding.btnVoltar.onDebouncedListener {
            super.onBackPressed()
        }
    }

    private fun setupObservers(){
        viewModel.getFotos.observe(this, Observer { fotos ->
            fillListFotos(fotos = fotos)
        })
    }

    private fun fillListFotos(fotos: List<Foto>){
        binding.rvFotos.setHasFixedSize(true)
        binding.rvFotos.layoutManager = GridLayoutManager(this, 4)
        fotosAdapter = FotosAdapter(fotos, Glide.with(this), singleClickFoto, longClickFoto)
        binding.rvFotos.adapter = fotosAdapter
    }

    private val singleClickFoto = { foto: Foto ->
        Toast.makeText(this, "Id: ${foto.imgAddress}", Toast.LENGTH_SHORT).show()
    }

    private val longClickFoto = { foto: Foto ->
        Toast.makeText(this, "ImgAddress: ${foto.imgAddress}", Toast.LENGTH_SHORT).show()
    }
}