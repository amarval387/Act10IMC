package com.example.act10imc

import android.content.Intent
import android.icu.text.DecimalFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.google.android.material.slider.RangeSlider
import kotlin.math.pow

class MainActivity : AppCompatActivity() {
    private lateinit var cdHombre: CardView

    private lateinit var cdMujer: CardView

    private lateinit var llcHombre: LinearLayoutCompat
    private lateinit var llcMujer: LinearLayoutCompat

    private lateinit var tvAltura: TextView
    private lateinit var rsAltura: RangeSlider

    private lateinit var tvPeso: TextView
    private lateinit var butMinusPeso: Button
    private lateinit var butPlusPeso: Button

    private lateinit var tvEdad: TextView
    private lateinit var butMinusEdad: Button
    private lateinit var butPlusEdad: Button

    private lateinit var rgComplex: RadioGroup
    private lateinit var rbDelgado: RadioButton
    private lateinit var rbNormal: RadioButton
    private lateinit var rbAtletico: RadioButton
    private lateinit var rbCorpulento: RadioButton


    private lateinit var butCalcular: AppCompatButton

    //Valores por defecto
    private var altura = 165
    private var peso = 62
    private var edad = 32
    private var man = true

    private val METER = 100
    private val MININUM = 0
    private val POWER: Double = 2.0
    private val DELGADO = 1.03
    private val NORMAL = 1.0
    private val ATLETICO = 0.97
    private val CORPULENTO = 0.94

    private val HOMBRE = 0.9
    private val MUJER = 1.0

    private val EDAD1 = 1.1
    private val EDAD2 = 1.0
    private val EDAD3 = 0.95
    private val EDAD4 = 1.05

    private var age = EDAD3

    private var genero = HOMBRE
    private var complexion = NORMAL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        iniComponent()
        val intent = Intent(this, ResultIMCActivity::class.java)
        initListeners(intent)
    }

    private fun initListeners(intent: Intent) {
        var imc: Double
        tvAltura.text = "$altura cm"
        tvPeso.text = "$peso kg"
        tvEdad.text = "$edad años"

        setupWeightListeners()
        setupAgeListeners()
        setupHeightListener()
        updateCardViewColors()
        setupComplexListener()
        setupSexValues()
        setupAgeValues()

        cdHombre.setOnClickListener {
            man = true
            updateCardViewColors()
        }

        cdMujer.setOnClickListener {
            man = false
            updateCardViewColors()
        }

        butCalcular.setOnClickListener {
            imc = (peso / (altura.toDouble() / METER).pow(POWER)) * genero * age * complexion

            Log.i("***********", complexion.toString())

            intent.putExtra("IMC", imc)
            intent.putExtra("GENERO",man)
            intent.putExtra("EDAD",edad)
            intent.putExtra("ALTURA",altura)
            intent.putExtra("PESO",peso)
            startActivity(intent)
        }
    }

    private fun updateCardViewColors() {
        val colorHombre = if (man) R.color.selected else R.color.unselected
        val colorMujer = if (!man) R.color.selected else R.color.unselected

        val colorHombreRes = ContextCompat.getColor(this, colorHombre)
        val colorMujerRes = ContextCompat.getColor(this, colorMujer)

        llcHombre.setBackgroundColor(colorHombreRes)
        llcMujer.setBackgroundColor(colorMujerRes)
    }

    private fun setupSexValues() {
        genero = if (man) {
            HOMBRE
        } else {
            MUJER
        }
    }

    private fun setupAgeValues() {
        age = when (edad) {
            in 0..15 -> EDAD1
            in 15..30 -> EDAD2
            in 30..60 -> EDAD3
            else -> EDAD4
        }
    }

    private fun setupWeightListeners() {
        butMinusPeso.setOnClickListener {
            peso = if (decrementValue(peso) < MININUM) MININUM else --peso
            tvPeso.text = "$peso kg"
        }

        butPlusPeso.setOnClickListener {
            peso = incrementValue(peso)
            tvPeso.text = "$peso kg"
        }
    }

    private fun setupAgeListeners() {
        butMinusEdad.setOnClickListener {
            edad = if (decrementValue(edad) < MININUM) MININUM else --edad
            tvEdad.text = "$edad años"
        }

        butPlusEdad.setOnClickListener {
            edad = incrementValue(edad)
            tvEdad.text = "$edad años"
        }
    }

    private fun setupHeightListener() {
        rsAltura.addOnChangeListener { _, value, _ ->
            val df = DecimalFormat("#.##")
            altura = df.format(value).toInt()
            tvAltura.text = "$altura cm"
        }
    }

    private fun setupComplexListener() {
        rgComplex.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                0 -> complexion = DELGADO
                1 -> complexion = NORMAL
                2 -> complexion = ATLETICO
                3 -> complexion = CORPULENTO
            }
        }
    }

    private fun decrementValue(value: Int): Int {
        return value - 1
    }

    private fun incrementValue(value: Int): Int {
        return value + 1
    }

    private fun iniComponent() {
        cdHombre = findViewById(R.id.cdHombre)
        cdMujer = findViewById(R.id.cdMujer)

        llcHombre = findViewById(R.id.llcHombre)
        llcMujer = findViewById(R.id.llcMujer)

        tvAltura = findViewById(R.id.tvAltura)
        rsAltura = findViewById(R.id.rsAltura)

        tvPeso = findViewById(R.id.tvPeso)
        butMinusPeso = findViewById(R.id.butMinusPeso)
        butPlusPeso = findViewById(R.id.butPlusPeso)

        tvEdad = findViewById(R.id.tvEdad)
        butMinusEdad = findViewById(R.id.butMinusEdad)
        butPlusEdad = findViewById(R.id.butPlusEdad)

        rgComplex = findViewById(R.id.rgComplex)
        rbDelgado = findViewById(R.id.rbDelgado)
        rbNormal = findViewById(R.id.rbNormal)
        rbAtletico = findViewById(R.id.rbAtletico)
        rbCorpulento = findViewById(R.id.rbCorpulento)

        butCalcular = findViewById(R.id.butCalcular)
    }
}