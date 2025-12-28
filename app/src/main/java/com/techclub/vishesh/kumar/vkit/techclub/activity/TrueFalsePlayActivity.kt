package com.techclub.vishesh.kumar.vkit.techclub.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.techclub.vishesh.kumar.vkit.techclub.R

// AdMob
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.FullScreenContentCallback

// Facebook
import com.facebook.ads.*

class TrueFalsePlayActivity : AppCompatActivity() {

    private var level = 1

    private val PREF = "TF_PREF"
    private val KEY = "TF_UNLOCKED"

    // Ads
    private var admobInterstitial: InterstitialAd? = null
    private var fbInterstitial: com.facebook.ads.InterstitialAd? = null

    // 🔥 QUESTIONS (tu baad me add karega)
    private val questions = listOf(
        Pair("Sun rises in the East", true),
        Pair("5 + 2 = 10", false),
        Pair("Java is a programming language", true),
        Pair("Earth has 2 moons", false)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_true_false)

        level = intent.getIntExtra("LEVEL", 1)

        MobileAds.initialize(this)
        AudienceNetworkAds.initialize(this)

        loadAdmob()
        loadFacebook()

        loadLevel()
    }

    // ================= ADS =================

    private fun loadAdmob() {
        InterstitialAd.load(
            this,
            "ADMOB_INTERSTITIAL_ID",
            AdRequest.Builder().build(),
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: InterstitialAd) {
                    admobInterstitial = ad
                }
                override fun onAdFailedToLoad(error: LoadAdError) {
                    admobInterstitial = null
                }
            }
        )
    }

    private fun loadFacebook() {
        fbInterstitial = com.facebook.ads.InterstitialAd(
            this,
            "FACEBOOK_INTERSTITIAL_ID"
        )
        fbInterstitial?.loadAd()
    }

    private fun showAdThen(action: () -> Unit) {
        when {
            admobInterstitial != null -> {
                admobInterstitial?.fullScreenContentCallback =
                    object : FullScreenContentCallback() {
                        override fun onAdDismissedFullScreenContent() {
                            admobInterstitial = null
                            loadAdmob()
                            action()
                        }
                    }
                admobInterstitial?.show(this)
            }
            fbInterstitial?.isAdLoaded == true -> {
                fbInterstitial?.show()
                action()
            }
            else -> action()
        }
    }

    // ================= GAME =================

    private fun loadLevel() {

        if (level > questions.size) {
            Toast.makeText(this, "Challenge Completed 🎉", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        val data = questions[level - 1]

        val tvQuestion = findViewById<TextView>(R.id.tvQuestion)
        val btnTrue = findViewById<Button>(R.id.btnTrue)
        val btnFalse = findViewById<Button>(R.id.btnFalse)

        tvQuestion.text = "Level $level\n\n${data.first}"

        btnTrue.setOnClickListener { checkAnswer(true, data.second) }
        btnFalse.setOnClickListener { checkAnswer(false, data.second) }

        findViewById<Button>(R.id.btnRestart).setOnClickListener {
            showAdThen { recreate() }
        }
    }

    private fun checkAnswer(userAnswer: Boolean, correct: Boolean) {
        if (userAnswer == correct) {
            unlock()
            startActivity(
                Intent(this, TrueFalsePlayActivity::class.java)
                    .putExtra("LEVEL", level + 1)
            )
            finish()
        } else {
            Toast.makeText(this, "Wrong ❌", Toast.LENGTH_SHORT).show()
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
