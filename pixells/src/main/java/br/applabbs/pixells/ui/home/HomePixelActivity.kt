package br.applabbs.pixells.ui.home

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import br.applabbs.pixells.R
import br.applabbs.pixells.databinding.ActivityHomePixelBinding
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomePixelActivity: AppCompatActivity() {

    private val viewModel: HomePixelViewModel by viewModel()
    private lateinit var binding: ActivityHomePixelBinding
    private lateinit var pixellAdapter: PixellAdapter

    private  val rotateOpen : Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.rotate_open_anim) }
    private  val rotateClose : Animation by lazy { AnimationUtils.loadAnimation(this,R.anim.rotate_close_anim) }
    private  val fromBottom: Animation by lazy { AnimationUtils.loadAnimation(this,R.anim.from_bottom_anim) }
    private  val toBottom : Animation by lazy { AnimationUtils.loadAnimation(this,R.anim.to_bottom_anim) }
    private var closed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomePixelBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        setupListeners()
    }

    private fun setupView(){
        binding.txtAltura.text = "Altura: " + this.resources.displayMetrics.heightPixels
        binding.txtLargura.text = "Largura: " + this.resources.displayMetrics.widthPixels

        val pixells = getListaPixels(400)
        val spanSizeLookup = object : GridLayoutManager.SpanSizeLookup(){
            override fun getSpanSize(position: Int): Int {
                return 10
            }
        }

        val layoutManager = GridLayoutManager(this, 10)
        layoutManager.spanSizeLookup = spanSizeLookup
        binding.rvPixells.layoutManager = layoutManager
        pixellAdapter = PixellAdapter(pixells, Glide.with(this), singleClick)
        binding.rvPixells.adapter = pixellAdapter
    }

    private val singleClick = { pixell: Pixell ->
        Toast.makeText(this, "Id: $pixell.id", Toast.LENGTH_SHORT).show()
    }

    private fun setupListeners(){
        binding.btnMain.setOnClickListener {
            onMenuClick()
        }
        binding.btnBuy.setOnClickListener{
            Toast.makeText(this, "Comprar", Toast.LENGTH_SHORT).show()
        }
        binding.btnSell.setOnClickListener {
            Toast.makeText(this, "Vender", Toast.LENGTH_SHORT).show()
        }
        binding.btnEdit.setOnClickListener {
            Toast.makeText(this, "Editar", Toast.LENGTH_SHORT).show()
        }
        binding.btnAccount.setOnClickListener {
            Toast.makeText(this, "Acessar Conta", Toast.LENGTH_SHORT).show()
        }
    }

    private fun onMenuClick(){
        setVisibility(closed)
        setAnimation(closed)
        closed = !closed
    }

    private fun setVisibility(closed:Boolean) = with(binding) {
        if(!closed) {
                btnBuy.visibility = View.VISIBLE
                btnSell.visibility = View.VISIBLE
                btnEdit.visibility = View.VISIBLE
                btnAccount.visibility = View.VISIBLE
        } else{
            btnBuy.visibility = View.INVISIBLE
            btnSell.visibility = View.INVISIBLE
            btnEdit.visibility = View.INVISIBLE
            btnAccount.visibility = View.INVISIBLE
        }
    }

    private fun setAnimation(closed:Boolean) = with(binding){
        if(!closed){
            btnBuy.startAnimation(fromBottom)
            btnSell.startAnimation(fromBottom)
            btnEdit.startAnimation(fromBottom)
            btnAccount.startAnimation(fromBottom)
            btnMain.startAnimation(rotateOpen)
        } else{
            btnBuy.startAnimation(toBottom)
            btnSell.startAnimation(toBottom)
            btnEdit.startAnimation(toBottom)
            btnAccount.startAnimation(toBottom)
            btnMain.startAnimation(rotateClose)
        }
    }
}

data class Pixell(
    var id: Int,
    var xPosition: Int,
    var yPosition: Int,
    var text: String? = "",
    var urlLink: String? = "",
    var urlImg: String? = ""
)

fun getListaPixels(qtd: Int): List<Pixell>{
    val list = ArrayList<Pixell>()
    for(i in 1..qtd){
        list.add(
            Pixell(
                id = i,
                xPosition = i,
                yPosition = i,
                text = i.toString(),
                urlLink = "www.globo.com",
                urlImg = "https://miro.medium.com/freeze/focal/112/112/50/50/1*LlOFjo2kAqldR1OdpbDTKQ.gif"
            )
        )
    }
    return list
}