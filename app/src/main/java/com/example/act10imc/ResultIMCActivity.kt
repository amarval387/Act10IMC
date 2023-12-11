package com.example.act10imc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton

class ResultIMCActivity : AppCompatActivity() {

    private lateinit var tvGenero:TextView
    private lateinit var tvAge:TextView
    private lateinit var tvHeight:TextView
    private lateinit var tvWeight:TextView
    private lateinit var tvResult0:TextView
    private lateinit var tvResult1:TextView
    private lateinit var tvResult2:TextView
    private lateinit var btnBack: AppCompatButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_imcactivity)
        iniComponent()
        setUpViews()
        btnBack.setOnClickListener{
            finish()
        }
    }

    private fun setUpViews(){
        if (intent.hasExtra("IMC")){
            val result = intent.getDoubleExtra("IMC",0.0)
            val imc = "IMC: $result"
            when(result){
                in 0.0..18.5 ->{
                    tvResult0.text = "Peso Bajo"
                    tvResult2.text = if(intent.getBooleanExtra("GENERO",true)) "Estás muy delgado\nTienes que ganar peso" else "Estás muy delgada\n" +
                            "Tienes que ganar peso"
                }
                in 18.5 ..<25.0 -> {
                    tvResult0.text = "Normal"
                    tvResult2.text = if(intent.getBooleanExtra("GENERO",true)) "Estás muy bien, tío!!!\nSigue así!!!" else "Estás muy bien, tía\n" +
                            "Sigue así!!!"
                }
                in 25.0 .. 30.0 ->{
                    tvResult0.text = "Sobrepeso"
                    tvResult2.text = if(intent.getBooleanExtra("GENERO",true)) "Estás rellenito\nCuídate!!!" else "Estás rellenita\n" +
                            "Cuídate!!!"
                }
                else -> {
                    tvResult0.text = "Obesidad"
                    tvResult2.text = if(intent.getBooleanExtra("GENERO",true)) "Estás gordito\nTienes que adelgazar!!!" else "Estás gordita\n" +
                            "Tienes que adelgazar!!!"
                }
            }
            tvResult1.text = imc
        }
        if(intent.hasExtra("GENERO")){
            val genero = intent.getBooleanExtra("GENERO",true)
            val text = if(genero) "Hombre" else "Mujer"
            tvGenero.text = text
        }
        if (intent.hasExtra("EDAD")){
            val edad = intent.getIntExtra("EDAD", 32)
            val text = "$edad años"
            tvAge.text = text
        }
        if (intent.hasExtra("ALTURA")){
            val altura = intent.getIntExtra("ALTURA", 165)
            val text = "$altura cm"
            tvHeight.text = text
        }
        if (intent.hasExtra("PESO")){
            val peso = intent.getIntExtra("PESO", 62)
            val text = "$peso kg"
            tvWeight.text = text
        }
    }


    private fun iniComponent(){
        tvAge = findViewById(R.id.tvAge)
        tvGenero = findViewById(R.id.tvGenero)
        tvHeight = findViewById(R.id.tvHeight)
        tvWeight = findViewById(R.id.tvWeight)
        tvResult0 = findViewById(R.id.tvResult0)
        tvResult1 = findViewById(R.id.tvResult1)
        tvResult2 = findViewById(R.id.tvResult2)
        btnBack = findViewById(R.id.btnBack)
    }
}