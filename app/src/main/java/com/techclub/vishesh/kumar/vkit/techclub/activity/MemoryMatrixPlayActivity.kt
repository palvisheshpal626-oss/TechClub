package com.techclub.vishesh.kumar.vkit.techclub.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
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

class MemoryMatrixPlayActivity : AppCompatActivity() {

    private var level = 1

    private val PREF = "MEM_PREF"
    private val KEY = "MEM_UNLOCKED"

    private val sequence = mutableListOf<Int>()
    private var userIndex = 0

    // Ads
    private var interstitialAd: InterstitialAd? = null
    private var rewardedAd: RewardedAd? = null

    // Help system
    private var helpLevel = 0
    private val MAX_HELP = 3

    private lateinit var grid: GridLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memory_matrix)

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
        userIndex = 0
        sequence.clear()

        grid = findViewById(R.id.grid)
        grid.removeAllViews()

        val size = when {
            level < 20 -> 3
            level < 40 -> 4
            level < 60 -> 5
            level < 80 -> 6
            else -> 7
        }

        val total = size * size
        grid.columnCount = size

        repeat(size) {
            sequence.add(Random.nextInt(total))
        }

        for (i in 0 until total) {
            val b = Button(this)
            b.text = ""
            b.isEnabled = false
            b.setOnClickListener { onUserTap(i) }
            grid.addView(b)
        }

        showSequence()
        setupHelpButton()
    }

    private fun showSequence() {
        val handler = Handler(Looper.getMainLooper())
        var delay = 0L

        sequence.forEach { idx ->
            handler.postDelayed({
                (grid.getChildAt(idx) as Button).text = "●"
            }, delay)
            delay += 400
            handler.postDelayed({
                (grid.getChildAt(idx) as Button).text = ""
            }, delay)
            delay += 200
        }

        handler.postDelayed({
            for (i in 0 until grid.childCount) {
                grid.getChildAt(i).isEnabled = true
            }
        }, delay + 300)
    }

    // ================= HELP SYSTEM =================

    private fun setupHelpButton() {
        val btnHint = findViewById<Button>(R.id.btnHint)

        btnHint.setOnClickListener {

            if (helpLevel >= MAX_HELP) {
                Toast.makeText(this, "No more help", Toast.LENGTH_SHORT).show()
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
        val handler = Handler(Looper.getMainLooper())
        var delay = 0L

        for (i in userIndex until minOf(userIndex + count, sequence.size)) {
            val idx = sequence[i]
            handler.postDelayed({
                (grid.getChildAt(idx) as Button).text = "●"
            }, delay)
            delay += 400
            handler.postDelayed({
                (grid.getChildAt(idx) as Button).text = ""
            }, delay)
            delay += 200
        }
    }

    private fun revealRemaining() {
        val handler = Handler(Looper.getMainLooper())
        var delay = 0L

        for (i in userIndex until sequence.size) {
            val idx = sequence[i]
            handler.postDelayed({
                (grid.getChildAt(idx) as Button).text = "●"
            }, delay)
            delay += 300
            handler.postDelayed({
                (grid.getChildAt(idx) as Button).text = ""
            }, delay)
            delay += 200
        }
    }

    // ================= USER INPUT =================

    private fun onUserTap(pos: Int) {
        if (sequence[userIndex] == pos) {
            userIndex++
            if (userIndex == sequence.size) {
                unlock()
                startActivity(
                    Intent(this, MemoryMatrixPlayActivity::class.java)
                        .putExtra("LEVEL", level + 1)
                )
                finish()
            }
        } else {
            Toast.makeText(this, "Wrong ❌", Toast.LENGTH_SHORT).show()
            showInterstitialThen { recreate() }
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
