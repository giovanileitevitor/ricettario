package br.applabbs.ricettario.ui.splash

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import br.applabbs.ricettario.BuildConfig
import br.applabbs.ricettario.R
import br.applabbs.ricettario.databinding.ActivitySplashBinding
import br.applabbs.ricettario.ui.home.HomeActivity
import br.applabbs.ricettario.uiInventtario.home.InventarioActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity(): AppCompatActivity() {

    private val viewModel : SplashViewModel by viewModel()
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        setupObservers()
        viewModel.getWaitProcess()
    }

    private fun setupView(){
        val appInit = br.applabbs.ricettario.BuildConfig.APP_TYPE_NAME
        when(appInit){
            "Inventtario" -> {
                binding.containerSplash.background = getDrawable(R.color.white)
                binding.mainImg.setImageDrawable(getDrawable(R.drawable.madison_ico))
            }
            "Riccettario" -> {
                if(BuildConfig.DEBUG){
                    binding.containerSplash.background = getDrawable(R.color.red)
                    binding.mainImg.setImageDrawable(getDrawable(R.mipmap.ic_riccetario_debug))
                }else{
                    binding.containerSplash.background = getDrawable(R.color.yellow)
                    binding.mainImg.setImageDrawable(getDrawable(R.mipmap.ic_riccetario_release))
                }
            }
            else -> {
                binding.containerSplash.background = getDrawable(R.color.red)
                binding.mainImg.setImageDrawable(getDrawable(R.mipmap.ic_riccetario_debug))
            }
        }
    }

    private fun setupObservers(){
        viewModel.next.observe(this){
            if(it){
                val appInit = br.applabbs.ricettario.BuildConfig.APP_TYPE_NAME
                if(appInit == "Inventtario"){
                    val intent = Intent(this, InventarioActivity::class.java)
                    startActivity(intent)
                }else{
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }

}