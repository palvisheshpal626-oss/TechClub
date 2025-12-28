package com.techclub.vishesh.kumar.vkit.techclub.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.techclub.vishesh.kumar.vkit.techclub.R
import com.techclub.vishesh.kumar.vkit.techclub.model.GameData

import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback

class MindLogicPlayActivity : AppCompatActivity() {

    private var level = 1

    // Ads
    private var interstitialAd: InterstitialAd? = null
    private var rewardedAd: RewardedAd? = null

    // Answer reveal system
    private var answerLevel = 0
    private val MAX_ANSWER_LEVEL = 3

    private val PREF = "LOGIC_PREFS"
    private val KEY = "LOGIC_UNLOCKED"

    // 🔥 QUESTIONS (UNCHANGED – tu yahin edit karega)
    private val questions = listOf(

        // 🔹 1–10 Easy logic
        GameData("Find next: 2, 4, 6, ?", listOf("7","8","9","10"), 1),
        GameData("Odd one out: 2, 4, 6, 9", listOf("2","4","6","9"), 3),
        GameData("Find next: 5, 10, 15, ?", listOf("18","20","25","30"), 1),
        GameData("Find missing: 1, 3, 5, ?", listOf("6","7","8","9"), 1),
        GameData("Which is different?", listOf("Circle","Square","Triangle","Ball"), 3),
        GameData("Find next: 10, 20, 30, ?", listOf("35","40","45","50"), 1),
        GameData("Odd one: Dog, Cat, Cow, Car", listOf("Dog","Cat","Cow","Car"), 3),
        GameData("Find next: 1, 4, 9, ?", listOf("14","15","16","18"), 2),
        GameData("Find next: 2, 6, 10, ?", listOf("12","14","16","18"), 1),
        GameData("Odd one out", listOf("Pen","Pencil","Book","Apple"), 3),

        // 🔹 11–20 Medium
        GameData("Find next: 3, 6, 12, ?", listOf("18","21","24","30"), 2),
        GameData("Missing number: 2, 4, 8, ?", listOf("10","12","14","16"), 3),
        GameData("Find next: 1, 8, 27, ?", listOf("36","54","64","81"), 2),
        GameData("Odd one out", listOf("2","3","5","9"), 3),
        GameData("Find next: 20, 18, 16, ?", listOf("15","14","13","12"), 1),
        GameData("Find missing: 1, 2, 4, 7, ?", listOf("10","11","12","13"), 1),
        GameData("Which is different?", listOf("Eye","Ear","Nose","Hand"), 3),
        GameData("Find next: 4, 9, 16, ?", listOf("20","24","25","36"), 2),
        GameData("Odd one out", listOf("January","March","June","Sunday"), 3),
        GameData("Find next: 7, 14, 21, ?", listOf("24","28","35","42"), 1),

        // 🔹 21–30 Pattern based
        GameData("Find next: 2, 6, 12, 20, ?", listOf("28","30","32","36"), 1),
        GameData("Missing: 1, 4, 9, 16, ?", listOf("20","24","25","30"), 2),
        GameData("Find next: 5, 25, 125, ?", listOf("250","375","500","625"), 3),
        GameData("Odd one", listOf("Iron","Gold","Silver","Plastic"), 3),
        GameData("Find next: 3, 9, 27, ?", listOf("54","72","81","90"), 2),
        GameData("Missing: 10, 20, 40, ?", listOf("60","70","80","100"), 2),
        GameData("Odd one out", listOf("Square","Rectangle","Circle","Triangle"), 2),
        GameData("Find next: 2, 3, 5, 8, ?", listOf("11","12","13","15"), 2),
        GameData("Find missing: 100, 90, 80, ?", listOf("75","70","65","60"), 1),
        GameData("Odd one", listOf("Asia","Europe","Africa","Ocean"), 3),

        // 🔹 31–40 Logic + math
        GameData("2, 4, 8, 16, ?", listOf("18","24","32","64"), 2),
        GameData("Find missing: 6, 12, 18, ?", listOf("20","24","30","36"), 1),
        GameData("Odd one", listOf("1","4","9","11"), 3),
        GameData("Find next: 1, 1, 2, 3, ?", listOf("4","5","6","8"), 1),
        GameData("Find next: 11, 22, 33, ?", listOf("44","55","66","77"), 0),
        GameData("Missing: 3, 6, 9, ?", listOf("11","12","15","18"), 1),
        GameData("Odd one out", listOf("Cube","Sphere","Circle","Cylinder"), 2),
        GameData("Find next: 100, 81, 64, ?", listOf("49","36","25","16"), 0),
        GameData("Find missing: 2, 5, 10, ?", listOf("15","20","25","30"), 1),
        GameData("Odd one", listOf("Sun","Moon","Star","Planet"), 1),

        // 🔥 41–60 HARD
        GameData("Find next: 1, 4, 10, 20, ?", listOf("30","35","40","45"), 1),
        GameData("Missing: 3, 9, 27, 81, ?", listOf("162","243","324","729"), 1),
        GameData("Odd one", listOf("2","4","8","18"), 3),
        GameData("Find next: 2, 5, 10, 17, ?", listOf("24","26","28","30"), 1),
        GameData("Missing: 7, 14, 28, ?", listOf("42","49","56","63"), 2),
        GameData("Odd one", listOf("Triangle","Square","Rectangle","Sphere"), 3),
        GameData("Find next: 121, 144, 169, ?", listOf("196","225","256","289"), 0),
        GameData("Missing: 1, 8, 27, 64, ?", listOf("100","121","125","144"), 2),
        GameData("Find next: 5, 15, 45, ?", listOf("90","120","135","180"), 2),
        GameData("Odd one", listOf("Copper","Iron","Gold","Wood"), 3),

        // 🔥 61–80 VERY HARD
        GameData("Find next: 2, 12, 36, ?", listOf("72","96","108","144"), 3),
        GameData("Missing: 4, 16, 36, ?", listOf("49","64","81","100"), 0),
        GameData("Find next: 1, 3, 6, 10, ?", listOf("12","13","15","16"), 2),
        GameData("Odd one", listOf("3","5","11","21"), 3),
        GameData("Find next: 6, 15, 28, ?", listOf("40","45","55","66"), 1),
        GameData("Missing: 10, 22, 46, ?", listOf("70","82","94","110"), 2),
        GameData("Odd one", listOf("A","E","I","B"), 3),
        GameData("Find next: 2, 8, 18, ?", listOf("32","36","50","72"), 0),
        GameData("Missing: 1, 5, 14, ?", listOf("25","30","35","41"), 3),
        GameData("Odd one", listOf("Monday","Tuesday","Friday","January"), 3),

        // 🔥🔥 81–100 EXTREME
        GameData("Find next: 3, 9, 27, 81, ?", listOf("162","243","324","729"), 1),
        GameData("Missing: 2, 6, 14, 30, ?", listOf("46","62","58","54"), 3),
        GameData("Find next: 1, 4, 9, 16, 25, ?", listOf("30","35","36","49"), 2),
        GameData("Odd one", listOf("8","27","64","125"), 0),
        GameData("Find next: 5, 10, 20, 40, ?", listOf("60","80","100","120"), 1),
        GameData("Missing: 7, 21, 63, ?", listOf("84","126","189","252"), 2),
        GameData("Find next: 2, 4, 16, ?", listOf("32","64","128","256"), 1),
        GameData("Odd one", listOf("Prime","Composite","Even","Odd"), 2),
        GameData("Missing: 1, 11, 21, 121, ?", listOf("131","211","221","212"), 3),
        GameData("Final logic: 1 → 1, 2 → 4, 3 → 9, 4 → ?", listOf("12","14","15","16"), 3)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_c_game_play)

        level = intent.getIntExtra("LEVEL", 1)

        MobileAds.initialize(this)

        loadInterstitialAd()
        loadRewardedAd()

        loadLevel()
    }

    // ================= ADS =================

    private fun loadInterstitialAd() {
        InterstitialAd.load(
            this,
            "ca-app-pub-6788180210698006/6868671346", // interstitial
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
            "ca-app-pub-6788180210698006/1219160354", // 👉 APNA REWARDED AD UNIT ID
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

    // ================= LEVEL =================

    private fun loadLevel() {

        answerLevel = 0 // 🔥 reset per level

        if (level > questions.size) {
            Toast.makeText(this, "Game Completed 🧠🔥", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        val q = questions[level - 1]

        findViewById<TextView>(R.id.tvQuestion).text =
            "Mind Logic – Level $level\n\n${q.question}"

        val tvHint = findViewById<TextView>(R.id.tvHint)
        val btnHint = findViewById<Button>(R.id.btnHint)
        tvHint.visibility = View.GONE

        val buttons = listOf(
            findViewById<Button>(R.id.btn1),
            findViewById<Button>(R.id.btn2),
            findViewById<Button>(R.id.btn3),
            findViewById<Button>(R.id.btn4)
        )

        buttons.forEachIndexed { i, b ->
            b.text = q.options[i]
            b.setOnClickListener {
                if (i == q.correctIndex) {
                    unlock()
                    Toast.makeText(this, "Correct 🧠", Toast.LENGTH_SHORT).show()
                    startActivity(
                        Intent(this, MindLogicPlayActivity::class.java)
                            .putExtra("LEVEL", level + 1)
                    )
                    finish()
                } else {
                    Toast.makeText(this, "Wrong ❌", Toast.LENGTH_SHORT).show()
                    showInterstitialThen { recreate() }
                }
            }
        }

        findViewById<Button>(R.id.btnRestart).setOnClickListener {
            showInterstitialThen { recreate() }
        }

        // 💡 ANSWER BUTTON (AD → ANSWER)
        btnHint.setOnClickListener {

            if (answerLevel >= MAX_ANSWER_LEVEL) {
                Toast.makeText(this, "Answer already revealed", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (rewardedAd == null) {
                Toast.makeText(this, "Ad loading… try again", Toast.LENGTH_SHORT).show()
                loadRewardedAd()
                return@setOnClickListener
            }

            rewardedAd?.show(this) {

                answerLevel++

                val correct = q.options[q.correctIndex]

                tvHint.text = when (answerLevel) {

                    1 -> {
                        val wrongs = q.options
                            .filterIndexed { i, _ -> i != q.correctIndex }
                            .shuffled()
                            .take(2)

                        "Logic Help 1:\n❌ Not: ${wrongs.joinToString(", ")}"
                    }

                    2 -> {
                        val wrong = q.options.first { it != correct }

                        "Logic Help 2:\nAnswer is between:\n✔ $correct  OR  ❓ $wrong"
                    }

                    3 -> {
                        "Answer Revealed ✅\nCorrect Answer: $correct"
                    }

                    else -> ""
                }

                tvHint.visibility = View.VISIBLE
            }

            rewardedAd = null
            loadRewardedAd()
        }
    }

    private fun unlock() {
        val p = getSharedPreferences(PREF, MODE_PRIVATE)
        val u = p.getInt(KEY, 1)
        if (level >= u) p.edit().putInt(KEY, level + 1).apply()
    }
}
