package com.techclub.vishesh.kumar.vkit.techclub.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.techclub.vishesh.kumar.vkit.techclub.R
import com.techclub.vishesh.kumar.vkit.techclub.model.GameData

import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.LoadAdError

class TapThinkPlayActivity : AppCompatActivity() {

    private var level = 1
    private var interstitialAd: InterstitialAd? = null

    private val PREF = "TAP_PREFS"
    private val KEY = "TAP_UNLOCKED"

    // 🔥 ADD 100 SPEED QUESTIONS HERE
    private val questions = listOf(

        // 1–10 (Very Easy – warm up)
        GameData("Fast! 2 + 2 ?", listOf("2","3","4","5"), 2),
        GameData("Fast! 5 + 1 ?", listOf("5","6","7","8"), 1),
        GameData("Fast! 3 + 4 ?", listOf("5","6","7","8"), 2),
        GameData("Fast! 6 - 2 ?", listOf("2","3","4","5"), 2),
        GameData("Fast! 1 + 8 ?", listOf("7","8","9","10"), 2),
        GameData("Fast! 10 - 5 ?", listOf("3","4","5","6"), 2),
        GameData("Fast! 4 + 5 ?", listOf("7","8","9","10"), 2),
        GameData("Fast! 9 - 3 ?", listOf("5","6","7","8"), 1),
        GameData("Fast! 2 x 2 ?", listOf("2","3","4","5"), 2),
        GameData("Fast! 8 ÷ 2 ?", listOf("2","3","4","5"), 2),

        // 11–20
        GameData("Fast! 3 x 3 ?", listOf("6","8","9","12"), 2),
        GameData("Fast! 12 ÷ 3 ?", listOf("3","4","5","6"), 1),
        GameData("Fast! 7 + 6 ?", listOf("12","13","14","15"), 1),
        GameData("Fast! 15 - 7 ?", listOf("6","7","8","9"), 2),
        GameData("Fast! 5 x 2 ?", listOf("8","9","10","12"), 2),
        GameData("Fast! 14 ÷ 2 ?", listOf("6","7","8","9"), 1),
        GameData("Fast! 9 + 5 ?", listOf("13","14","15","16"), 1),
        GameData("Fast! 20 - 9 ?", listOf("9","10","11","12"), 2),
        GameData("Fast! 4 x 3 ?", listOf("10","11","12","13"), 2),
        GameData("Fast! 16 ÷ 4 ?", listOf("2","3","4","5"), 2),

        // 21–30
        GameData("Fast! 6 x 4 ?", listOf("20","22","24","26"), 2),
        GameData("Fast! 18 ÷ 3 ?", listOf("5","6","7","8"), 1),
        GameData("Fast! 11 + 9 ?", listOf("18","19","20","21"), 2),
        GameData("Fast! 25 - 8 ?", listOf("15","16","17","18"), 2),
        GameData("Fast! 7 x 5 ?", listOf("30","32","35","40"), 2),
        GameData("Fast! 21 ÷ 7 ?", listOf("2","3","4","5"), 1),
        GameData("Fast! 14 + 6 ?", listOf("18","19","20","21"), 2),
        GameData("Fast! 30 - 11 ?", listOf("17","18","19","20"), 2),
        GameData("Fast! 8 x 4 ?", listOf("28","30","32","36"), 2),
        GameData("Fast! 24 ÷ 6 ?", listOf("3","4","5","6"), 1),

        // 31–40
        GameData("Fast! 9 x 4 ?", listOf("32","34","36","40"), 2),
        GameData("Fast! 27 ÷ 3 ?", listOf("7","8","9","10"), 2),
        GameData("Fast! 18 + 7 ?", listOf("24","25","26","27"), 1),
        GameData("Fast! 40 - 19 ?", listOf("19","20","21","22"), 2),
        GameData("Fast! 6 x 7 ?", listOf("40","41","42","44"), 2),
        GameData("Fast! 28 ÷ 4 ?", listOf("6","7","8","9"), 1),
        GameData("Fast! 22 + 8 ?", listOf("28","29","30","31"), 2),
        GameData("Fast! 50 - 17 ?", listOf("31","32","33","34"), 2),
        GameData("Fast! 9 x 6 ?", listOf("52","54","56","58"), 1),
        GameData("Fast! 45 ÷ 5 ?", listOf("7","8","9","10"), 2),

        // 41–50
        GameData("Fast! 12 x 4 ?", listOf("44","46","48","50"), 2),
        GameData("Fast! 36 ÷ 6 ?", listOf("5","6","7","8"), 1),
        GameData("Fast! 19 + 6 ?", listOf("23","24","25","26"), 2),
        GameData("Fast! 60 - 27 ?", listOf("31","32","33","34"), 2),
        GameData("Fast! 11 x 3 ?", listOf("30","32","33","35"), 2),
        GameData("Fast! 49 ÷ 7 ?", listOf("6","7","8","9"), 1),
        GameData("Fast! 17 + 9 ?", listOf("24","25","26","27"), 2),
        GameData("Fast! 70 - 38 ?", listOf("30","31","32","33"), 2),
        GameData("Fast! 8 x 9 ?", listOf("70","72","74","76"), 1),
        GameData("Fast! 64 ÷ 8 ?", listOf("6","7","8","9"), 2),

        // 51–60
        GameData("Fast! 13 x 4 ?", listOf("48","50","52","56"), 2),
        GameData("Fast! 81 ÷ 9 ?", listOf("7","8","9","10"), 2),
        GameData("Fast! 24 + 19 ?", listOf("41","42","43","44"), 2),
        GameData("Fast! 90 - 47 ?", listOf("41","42","43","44"), 2),
        GameData("Fast! 7 x 8 ?", listOf("54","56","58","60"), 1),
        GameData("Fast! 72 ÷ 9 ?", listOf("6","7","8","9"), 2),
        GameData("Fast! 35 + 18 ?", listOf("51","52","53","54"), 2),
        GameData("Fast! 100 - 59 ?", listOf("39","40","41","42"), 2),
        GameData("Fast! 14 x 5 ?", listOf("60","65","70","75"), 2),
        GameData("Fast! 84 ÷ 7 ?", listOf("10","11","12","13"), 2),

        // 61–70
        GameData("Fast! 16 x 4 ?", listOf("60","62","64","66"), 2),
        GameData("Fast! 96 ÷ 12 ?", listOf("6","7","8","9"), 2),
        GameData("Fast! 29 + 17 ?", listOf("44","45","46","47"), 2),
        GameData("Fast! 120 - 73 ?", listOf("45","46","47","48"), 2),
        GameData("Fast! 9 x 8 ?", listOf("70","72","74","76"), 1),
        GameData("Fast! 90 ÷ 10 ?", listOf("7","8","9","10"), 2),
        GameData("Fast! 41 + 19 ?", listOf("58","59","60","61"), 2),
        GameData("Fast! 150 - 89 ?", listOf("59","60","61","62"), 2),
        GameData("Fast! 15 x 6 ?", listOf("84","88","90","96"), 2),
        GameData("Fast! 144 ÷ 12 ?", listOf("10","11","12","13"), 2),

        // 71–80
        GameData("Fast! 18 x 5 ?", listOf("80","85","90","95"), 2),
        GameData("Fast! 121 ÷ 11 ?", listOf("9","10","11","12"), 2),
        GameData("Fast! 55 + 26 ?", listOf("79","80","81","82"), 2),
        GameData("Fast! 200 - 117 ?", listOf("81","82","83","84"), 2),
        GameData("Fast! 16 x 7 ?", listOf("108","110","112","114"), 2),
        GameData("Fast! 169 ÷ 13 ?", listOf("11","12","13","14"), 2),
        GameData("Fast! 68 + 29 ?", listOf("95","96","97","98"), 2),
        GameData("Fast! 250 - 153 ?", listOf("95","96","97","98"), 2),
        GameData("Fast! 14 x 8 ?", listOf("108","110","112","114"), 2),
        GameData("Fast! 196 ÷ 14 ?", listOf("12","13","14","15"), 2),

        // 81–100 (Harder but fast)
        GameData("Fast! 21 x 4 ?", listOf("80","82","84","86"), 2),
        GameData("Fast! 225 ÷ 15 ?", listOf("13","14","15","16"), 2),
        GameData("Fast! 73 + 28 ?", listOf("99","100","101","102"), 2),
        GameData("Fast! 300 - 197 ?", listOf("101","102","103","104"), 2),
        GameData("Fast! 18 x 7 ?", listOf("124","126","128","130"), 1),
        GameData("Fast! 256 ÷ 16 ?", listOf("14","15","16","17"), 2),
        GameData("Fast! 86 + 37 ?", listOf("121","122","123","124"), 2),
        GameData("Fast! 400 - 277 ?", listOf("121","122","123","124"), 2),
        GameData("Fast! 19 x 6 ?", listOf("110","112","114","116"), 2),
        GameData("Fast! 361 ÷ 19 ?", listOf("17","18","19","20"), 2)
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_c_game_play)

        level = intent.getIntExtra("LEVEL", 1)

        if (questions.isEmpty()) {
            Toast.makeText(this, "Add Tap Think questions", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        MobileAds.initialize(this)
        loadAd()
        loadLevel()
    }

    private fun loadAd() {
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

    private fun showAdThen(action: () -> Unit) {
        if (interstitialAd != null) {
            interstitialAd?.show(this)
            interstitialAd = null
            loadAd()
        }
        action()
    }

    private fun loadLevel() {

        if (level > questions.size) {
            Toast.makeText(this, "Game Completed 🎉", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        val data = questions[level - 1]

        findViewById<TextView>(R.id.tvQuestion).text =
            "Tap Think – Level $level\n\n${data.question}"

        val buttons = listOf(
            findViewById<Button>(R.id.btn1),
            findViewById<Button>(R.id.btn2),
            findViewById<Button>(R.id.btn3),
            findViewById<Button>(R.id.btn4)
        )

        buttons.forEachIndexed { i, btn ->
            btn.text = data.options[i]
            btn.setOnClickListener {
                if (i == data.correctIndex) {
                    unlock()
                    Toast.makeText(this, "Correct ⚡", Toast.LENGTH_SHORT).show()
                    startActivity(
                        Intent(this, TapThinkPlayActivity::class.java)
                            .putExtra("LEVEL", level + 1)
                    )
                    finish()
                } else {
                    Toast.makeText(this, "Wrong ❌", Toast.LENGTH_SHORT).show()
                    showAdThen { recreate() }
                }
            }
        }

        findViewById<Button>(R.id.btnRestart).setOnClickListener {
            showAdThen { recreate() }
        }
    }

    private fun unlock() {
        val p = getSharedPreferences(PREF, MODE_PRIVATE)
        val u = p.getInt(KEY, 1)
        if (level >= u) p.edit().putInt(KEY, level + 1).apply()
    }
}
