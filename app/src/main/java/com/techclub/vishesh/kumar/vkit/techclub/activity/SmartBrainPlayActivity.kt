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
import kotlin.random.Random

import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback

class SmartBrainPlayActivity : AppCompatActivity() {

    private var level = 1

    private val PREF = "SMART_PREFS"
    private val KEY = "SMART_UNLOCKED"

    // Ads
    private var interstitialAd: InterstitialAd? = null
    private var rewardedAd: RewardedAd? = null

    // Answer system
    private var answerLevel = 0
    private val MAX_ANSWER_LEVEL = 3

    // 🔥 QUESTIONS (UNCHANGED)
    private val questions = listOf(

        // 1–10 EASY
        GameData("2 + 3 = ?", listOf("4","5","6","7"), 1),
        GameData("Odd one out", listOf("Cat","Dog","Cow","Car"), 3),
        GameData("Next: 1, 2, 3, ?", listOf("4","5","6","7"), 0),
        GameData("5 × 2 = ?", listOf("8","9","10","12"), 2),
        GameData("Which is a fruit?", listOf("Potato","Apple","Onion","Carrot"), 1),
        GameData("Next: 2, 4, 6, ?", listOf("7","8","9","10"), 1),
        GameData("Odd one", listOf("Pen","Pencil","Book","Banana"), 3),
        GameData("10 − 4 = ?", listOf("4","5","6","7"), 2),
        GameData("Next: A, B, C, ?", listOf("D","E","F","G"), 0),
        GameData("3 × 3 = ?", listOf("6","8","9","12"), 2),

        // 11–20 MEDIUM
        GameData("Next: 5, 10, 15, ?", listOf("18","20","25","30"), 1),
        GameData("Odd one", listOf("2","4","6","9"), 3),
        GameData("Next: 1, 4, 9, ?", listOf("12","14","16","18"), 2),
        GameData("12 ÷ 3 = ?", listOf("3","4","5","6"), 1),
        GameData("Which is different?", listOf("Eye","Ear","Nose","Hand"), 3),
        GameData("Next: 20, 18, 16, ?", listOf("15","14","13","12"), 1),
        GameData("Odd one", listOf("January","March","April","Sunday"), 3),
        GameData("7 × 4 = ?", listOf("24","26","28","30"), 2),
        GameData("Next: 2, 6, 12, ?", listOf("18","20","24","30"), 2),
        GameData("Which is not metal?", listOf("Iron","Gold","Plastic","Silver"), 2),

        // 21–30 TRICKY
        GameData("Next: 3, 9, 27, ?", listOf("54","72","81","90"), 2),
        GameData("Odd one", listOf("Square","Rectangle","Circle","Triangle"), 2),
        GameData("Next: 1, 8, 27, ?", listOf("36","54","64","81"), 2),
        GameData("15 + 5 × 2 = ?", listOf("25","30","35","40"), 1),
        GameData("Which is different?", listOf("Dog","Cat","Lion","Chair"), 3),
        GameData("Next: 100, 90, 80, ?", listOf("75","70","65","60"), 1),
        GameData("Odd one", listOf("2","3","5","11"), 3),
        GameData("Next: 4, 9, 16, ?", listOf("20","24","25","30"), 2),
        GameData("24 ÷ 6 = ?", listOf("2","3","4","6"), 2),
        GameData("Which is a pattern?", listOf("1,2,3","A,B,C","Sun rises","2,4,6"), 3),

        // 31–40 HARD
        GameData("Next: 2, 4, 8, 16, ?", listOf("18","24","32","64"), 2),
        GameData("Odd one", listOf("1","4","9","11"), 3),
        GameData("Next: 1, 1, 2, 3, ?", listOf("4","5","6","8"), 1),
        GameData("6, 12, 18, ?", listOf("20","24","30","36"), 1),
        GameData("Which is different?", listOf("Cube","Sphere","Circle","Cylinder"), 2),
        GameData("100, 81, 64, ?", listOf("49","36","25","16"), 0),
        GameData("2, 5, 10, ?", listOf("15","20","25","30"), 1),
        GameData("Odd one", listOf("Sun","Moon","Star","Planet"), 1),
        GameData("11, 22, 33, ?", listOf("44","55","66","77"), 0),
        GameData("3 × (4 + 2) = ?", listOf("12","18","24","30"), 1),

        // 41–60 VERY HARD
        GameData("1, 4, 10, 20, ?", listOf("30","35","40","45"), 1),
        GameData("3, 9, 27, 81, ?", listOf("162","243","324","729"), 1),
        GameData("Odd one", listOf("2","4","8","18"), 3),
        GameData("2, 5, 10, 17, ?", listOf("24","26","28","30"), 1),
        GameData("7, 14, 28, ?", listOf("42","49","56","63"), 2),
        GameData("Square of 13?", listOf("144","156","169","196"), 2),
        GameData("121, 144, 169, ?", listOf("196","225","256","289"), 0),
        GameData("1, 8, 27, 64, ?", listOf("100","121","125","144"), 2),
        GameData("5, 15, 45, ?", listOf("90","120","135","180"), 2),
        GameData("Odd one", listOf("Copper","Iron","Gold","Wood"), 3),

        // 61–80 EXTREME
        GameData("2, 12, 36, ?", listOf("72","96","108","144"), 3),
        GameData("4, 16, 36, ?", listOf("49","64","81","100"), 0),
        GameData("1, 3, 6, 10, ?", listOf("12","13","15","16"), 2),
        GameData("Odd one", listOf("3","5","11","21"), 3),
        GameData("6, 15, 28, ?", listOf("40","45","55","66"), 1),
        GameData("10, 22, 46, ?", listOf("70","82","94","110"), 2),
        GameData("Odd one", listOf("A","E","I","B"), 3),
        GameData("2, 8, 18, ?", listOf("32","36","50","72"), 0),
        GameData("1, 5, 14, ?", listOf("25","30","35","41"), 3),
        GameData("Odd one", listOf("Monday","Tuesday","Friday","January"), 3),

        // 81–100 BRUTAL
        GameData("3, 9, 27, 81, ?", listOf("162","243","324","729"), 1),
        GameData("2, 6, 14, 30, ?", listOf("46","62","58","54"), 3),
        GameData("1, 4, 9, 16, 25, ?", listOf("30","35","36","49"), 2),
        GameData("Odd one", listOf("8","27","64","125"), 0),
        GameData("5, 10, 20, 40, ?", listOf("60","80","100","120"), 1),
        GameData("7, 21, 63, ?", listOf("84","126","189","252"), 2),
        GameData("2, 4, 16, ?", listOf("32","64","128","256"), 1),
        GameData("Odd one", listOf("Prime","Composite","Even","Odd"), 2),
        GameData("1, 11, 21, 121, ?", listOf("131","211","221","212"), 3),
        GameData("1→1, 2→4, 3→9, 4→?", listOf("12","14","15","16"), 3)
    )

    private lateinit var currentQuestion: GameData

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

    private fun loadLevel() {

        answerLevel = 0

        currentQuestion = questions[Random.nextInt(questions.size)]

        val tvQuestion = findViewById<TextView>(R.id.tvQuestion)
        val tvHint = findViewById<TextView>(R.id.tvHint)
        val btnHint = findViewById<Button>(R.id.btnHint)

        val buttons = listOf(
            findViewById<Button>(R.id.btn1),
            findViewById<Button>(R.id.btn2),
            findViewById<Button>(R.id.btn3),
            findViewById<Button>(R.id.btn4)
        )

        tvHint.visibility = View.GONE
        tvQuestion.text = "Smart Brain – Level $level\n\n${currentQuestion.question}"

        buttons.forEachIndexed { i, b ->
            b.text = currentQuestion.options[i]
            b.setOnClickListener {

                if (i == currentQuestion.correctIndex) {

                    unlock()
                    Toast.makeText(this, "Correct 🧠", Toast.LENGTH_SHORT).show()

                    startActivity(
                        Intent(this, SmartBrainPlayActivity::class.java)
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

        // 💡 HINT BUTTON (AD → ANSWER)
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

                val correct = currentQuestion.options[currentQuestion.correctIndex]

                tvHint.text = when (answerLevel) {

                    1 -> {
                        val wrongs = currentQuestion.options
                            .filterIndexed { idx, _ -> idx != currentQuestion.correctIndex }
                            .shuffled()
                            .take(2)
                        "Hint 1:\n❌ Not: ${wrongs.joinToString(", ")}"
                    }

                    2 -> {
                        val wrong = currentQuestion.options.first { it != correct }
                        "Hint 2:\nAnswer is between:\n✔ $correct OR ❓ $wrong"
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
