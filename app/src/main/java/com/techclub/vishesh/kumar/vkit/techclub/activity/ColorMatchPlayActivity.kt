package com.techclub.vishesh.kumar.vkit.techclub.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.techclub.vishesh.kumar.vkit.techclub.R

// ✅ CORRECT ADMOB IMPORTS (NEW API)
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

class ColorMatchPlayActivity : AppCompatActivity() {

    private var level = 1
    private var interstitialAd: InterstitialAd? = null

    private val PREF = "COLOR_PREF"
    private val KEY = "COLOR_UNLOCKED"

    private var correctColor = ""

    private val colors = mapOf(
        "RED" to Color.RED,
        "GREEN" to Color.GREEN,
        "BLUE" to Color.BLUE,
        "YELLOW" to Color.YELLOW,
        "CYAN" to Color.CYAN,
        "MAGENTA" to Color.MAGENTA
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_color_match)

        level = intent.getIntExtra("LEVEL", 1)

        MobileAds.initialize(this)
        loadAd()

        startRound()
    }

    // 🔹 LOAD AD (NEW API)
    private fun loadAd() {
        InterstitialAd.load(
            this,
            "ca-app-pub-6788180210698006/6868671346", // TEST ID
            AdRequest.Builder().build(),
            object : InterstitialAdLoadCallback() {

                override fun onAdLoaded(ad: InterstitialAd) {
                    interstitialAd = ad
                }

                override fun onAdFailedToLoad(error: LoadAdError) {
                    interstitialAd = null
                }
            }
        )
    }

    // 🔹 SHOW AD THEN ACTION
    private fun showAdThen(action: () -> Unit) {
        if (interstitialAd != null) {
            interstitialAd?.show(this)
            interstitialAd = null
            loadAd()
        }
        action()
    }

    private fun startRound() {
        val tv = findViewById<TextView>(R.id.tvWord)
        val buttons = listOf(
            findViewById<Button>(R.id.btn1),
            findViewById<Button>(R.id.btn2),
            findViewById<Button>(R.id.btn3),
            findViewById<Button>(R.id.btn4)
        )

        val pool = colors.keys.shuffled().take(4)

        val textWord = pool.random()
        correctColor = pool.random()

        tv.text = textWord
        tv.setTextColor(colors[correctColor]!!)

        buttons.forEachIndexed { i, b ->
            b.text = pool[i]
            b.setOnClickListener {
                if (pool[i] == correctColor) {
                    unlock()
                    startActivity(
                        Intent(this, ColorMatchPlayActivity::class.java)
                            .putExtra("LEVEL", level + 1)
                    )
                    finish()
                } else {
                    Toast.makeText(this, "Wrong ❌", Toast.LENGTH_SHORT).show()
                    showAdThen { recreate() }
                }
            }
        }

        // ⏱ TIME LIMIT
        Handler(Looper.getMainLooper()).postDelayed({
            Toast.makeText(this, "Too Slow ❌", Toast.LENGTH_SHORT).show()
            showAdThen { recreate() }
        }, 1500)
    }

    private fun unlock() {
        val p = getSharedPreferences(PREF, MODE_PRIVATE)
        val u = p.getInt(KEY, 1)
        if (level >= u) p.edit().putInt(KEY, level + 1).apply()
    }
}
