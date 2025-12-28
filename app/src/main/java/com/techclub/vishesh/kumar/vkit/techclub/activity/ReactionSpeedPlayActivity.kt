package com.techclub.vishesh.kumar.vkit.techclub.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.techclub.vishesh.kumar.vkit.techclub.R
import kotlin.random.Random

// ✅ CORRECT ADMOB IMPORTS (NEW API)
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

class ReactionSpeedPlayActivity : AppCompatActivity() {

    private var level = 1
    private var startTime = 0L
    private var canTap = false

    private val PREF = "REACT_PREF"
    private val KEY = "REACT_UNLOCKED"

    private var interstitialAd: InterstitialAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reaction_speed)

        level = intent.getIntExtra("LEVEL", 1)

        MobileAds.initialize(this)
        loadAd()

        startRound()
    }

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

    private fun showAdThen(action: () -> Unit) {
        if (interstitialAd != null) {
            interstitialAd?.show(this)
            interstitialAd = null
            loadAd()
        }
        action()
    }

    private fun startRound() {
        val tv = findViewById<TextView>(R.id.tvText)
        val btn = findViewById<Button>(R.id.btnTap)

        tv.text = "WAIT..."
        btn.isEnabled = true
        canTap = false

        val delay = Random.nextLong(1000, 4000)

        Handler(Looper.getMainLooper()).postDelayed({
            tv.text = "TAP NOW!"
            canTap = true
            startTime = System.currentTimeMillis()
        }, delay)

        btn.setOnClickListener {
            if (!canTap) {
                Toast.makeText(this, "Too Early ❌", Toast.LENGTH_SHORT).show()
                showAdThen { recreate() }
            } else {
                val reaction = System.currentTimeMillis() - startTime
                checkResult(reaction)
            }
        }
    }

    private fun checkResult(time: Long) {
        val limit = when {
            level < 30 -> 800
            level < 60 -> 600
            level < 80 -> 450
            else -> 350
        }

        if (time <= limit) {
            unlock()
            startActivity(
                Intent(this, ReactionSpeedPlayActivity::class.java)
                    .putExtra("LEVEL", level + 1)
            )
            finish()
        } else {
            Toast.makeText(this, "Too Slow ❌ ($time ms)", Toast.LENGTH_SHORT).show()
            showAdThen { recreate() }
        }
    }

    private fun unlock() {
        val p = getSharedPreferences(PREF, MODE_PRIVATE)
        val u = p.getInt(KEY, 1)
        if (level >= u) {
            p.edit().putInt(KEY, level + 1).apply()
        }
    }
}
