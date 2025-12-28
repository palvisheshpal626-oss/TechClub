package com.techclub.vishesh.kumar.vkit.techclub.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.GridLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.techclub.vishesh.kumar.vkit.techclub.R
import kotlin.random.Random

import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback

class PatternLockPlayActivity : AppCompatActivity() {

    private var level = 1
    private val PREF = "PAT_PREF"
    private val KEY = "PAT_UNLOCKED"

    private val pattern = mutableListOf<Int>()
    private val input = mutableListOf<Int>()

    // Ads
    private var interstitialAd: InterstitialAd? = null
    private var rewardedAd: RewardedAd? = null

    // Help system
    private var helpLevel = 0
    private val MAX_HELP = 3

    private lateinit var grid: GridLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pattern_lock)

        level = intent.getIntExtra("LEVEL", 1)

        MobileAds.initialize(this)

        loadInterstitialAd()
        loadRewardedAd()

        startGame()
    }

    // ================= ADS =================

    private fun loadInterstitialAd() {
        InterstitialAd.load(
            this,
            "ca-app-pub-6788180210698006/6868671346",
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

    private fun loadRewardedAd() {
        RewardedAd.load(
            this,
            "ca-app-pub-6788180210698006/1219160354", // 👉 REWARDED AD UNIT ID
            AdRequest.Builder().build(),
            object : RewardedAdLoadCallback() {
                override fun onAdLoaded(ad: RewardedAd) {
                    rewardedAd = ad
                }
                override fun onAdFailedToLoad(error: LoadAdError) {
                    rewardedAd = null
                }
            }
        )
    }

    private fun showInterstitialThen(action: () -> Unit) {
        if (interstitialAd != null) {
            interstitialAd?.show(this)
            interstitialAd = null
            loadInterstitialAd()
        }
        action()
    }

    // ================= GAME =================

    private fun startGame() {

        helpLevel = 0
        input.clear()
        pattern.clear()

        grid = findViewById(R.id.grid)
        grid.removeAllViews()

        val size = when {
            level < 40 -> 3
            level < 80 -> 4
            else -> 5
        }

        grid.columnCount = size
        val total = size * size

        val length = when {
            level < 20 -> 3
            level < 40 -> 5
            level < 60 -> 6
            level < 80 -> 7
            else -> 8
        }

        while (pattern.size < length) {
            val r = Random.nextInt(total)
            if (!pattern.contains(r)) pattern.add(r)
        }

        for (i in 0 until total) {
            val b = Button(this)
            b.isEnabled = false
            b.setOnClickListener { onTap(i) }
            grid.addView(b)
        }

        showPattern()
        setupHintButton()
    }

    private fun showPattern() {
        val h = Handler(Looper.getMainLooper())
        var d = 0L

        pattern.forEach {
            h.postDelayed({ (grid.getChildAt(it) as Button).text = "●" }, d)
            d += 300
            h.postDelayed({ (grid.getChildAt(it) as Button).text = "" }, d)
            d += 200
        }

        h.postDelayed({
            for (i in 0 until grid.childCount) {
                (grid.getChildAt(i) as Button).isEnabled = true
            }
        }, d + 300)
    }

    // ================= HELP SYSTEM =================

    private fun setupHintButton() {
        val btnHint = findViewById<Button>(R.id.btnHint)

        btnHint.setOnClickListener {

            if (helpLevel >= MAX_HELP) {
                Toast.makeText(this, "No more hints", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (rewardedAd == null) {
                Toast.makeText(this, "Ad loading… try again", Toast.LENGTH_SHORT).show()
                loadRewardedAd()
                return@setOnClickListener
            }

            rewardedAd?.show(this) {

                helpLevel++

                when (helpLevel) {
                    1 -> revealNext(1)
                    2 -> revealNext(2)
                    3 -> revealRemaining()
                }
            }

            rewardedAd = null
            loadRewardedAd()
        }
    }

    private fun revealNext(count: Int) {
        val h = Handler(Looper.getMainLooper())
        var d = 0L

        for (i in input.size until minOf(input.size + count, pattern.size)) {
            val idx = pattern[i]
            h.postDelayed({ (grid.getChildAt(idx) as Button).text = "●" }, d)
            d += 300
            h.postDelayed({ (grid.getChildAt(idx) as Button).text = "" }, d)
            d += 200
        }
    }

    private fun revealRemaining() {
        val h = Handler(Looper.getMainLooper())
        var d = 0L

        for (i in input.size until pattern.size) {
            val idx = pattern[i]
            h.postDelayed({ (grid.getChildAt(idx) as Button).text = "●" }, d)
            d += 250
            h.postDelayed({ (grid.getChildAt(idx) as Button).text = "" }, d)
            d += 150
        }
    }

    // ================= USER INPUT =================

    private fun onTap(i: Int) {
        input.add(i)

        if (input.last() != pattern[input.size - 1]) {
            Toast.makeText(this, "Wrong ❌", Toast.LENGTH_SHORT).show()
            showInterstitialThen { recreate() }
            return
        }

        if (input.size == pattern.size) {
            unlock()
            startActivity(
                Intent(this, PatternLockPlayActivity::class.java)
                    .putExtra("LEVEL", level + 1)
            )
            finish()
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
