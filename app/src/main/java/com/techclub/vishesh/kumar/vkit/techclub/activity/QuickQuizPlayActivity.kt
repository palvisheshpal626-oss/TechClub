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

class QuickQuizPlayActivity : AppCompatActivity() {

    private var level = 1

    // Ads
    private var interstitialAd: InterstitialAd? = null
    private var rewardedAd: RewardedAd? = null

    // Answer reveal system
    private var answerLevel = 0
    private val MAX_ANSWER_LEVEL = 3

    // 🔥 QUESTIONS (unchanged)
    private val questions = listOf(

        GameData("What is 5 + 3 ?", listOf("5", "8", "10", "15"), 1),
        GameData("Capital of India?", listOf("Delhi", "Mumbai", "Kolkata", "Chennai"), 0),
        GameData("Which is even number?", listOf("3", "5", "7", "8"), 3),
        GameData("Sun rises in?", listOf("North", "South", "East", "West"), 2),
        GameData("2 x 6 = ?", listOf("8", "10", "12", "14"), 2),

        GameData("Which is a fruit?", listOf("Potato", "Carrot", "Apple", "Onion"), 2),
        GameData("How many days in a week?", listOf("5", "6", "7", "8"), 2),
        GameData("Which animal barks?", listOf("Cat", "Dog", "Cow", "Lion"), 1),
        GameData("Which is a color?", listOf("Table", "Red", "Chair", "Pen"), 1),
        GameData("10 / 2 = ?", listOf("2", "4", "5", "10"), 2),

        GameData("Which is largest planet?", listOf("Earth", "Mars", "Jupiter", "Venus"), 2),
        GameData("How many months in a year?", listOf("10", "11", "12", "13"), 2),
        GameData("Which bird flies?", listOf("Dog", "Cat", "Crow", "Cow"), 2),
        GameData("Which is programming language?", listOf("HTML", "Python", "Paint", "Chrome"), 1),
        GameData("3 x 7 = ?", listOf("18", "20", "21", "24"), 2),

        GameData("Which is national animal of India?", listOf("Lion", "Tiger", "Elephant", "Cow"), 1),
        GameData("Which shape has 3 sides?", listOf("Square", "Circle", "Triangle", "Rectangle"), 2),
        GameData("Which is used to write?", listOf("Pen", "Fan", "Shoe", "Phone"), 0),
        GameData("Which gas we breathe?", listOf("Oxygen", "Carbon", "Nitrogen", "Helium"), 0),
        GameData("15 - 5 = ?", listOf("5", "10", "15", "20"), 1),

        GameData("Which is a vegetable?", listOf("Apple", "Banana", "Potato", "Mango"), 2),
        GameData("Which day comes after Friday?", listOf("Thursday", "Saturday", "Sunday", "Monday"), 1),
        GameData("Which is smallest number?", listOf("2", "5", "1", "9"), 2),
        GameData("Which is used to call?", listOf("TV", "Mobile", "Fan", "Bulb"), 1),
        GameData("6 x 6 = ?", listOf("30", "32", "36", "40"), 2),

        GameData("Which is water animal?", listOf("Dog", "Cat", "Fish", "Cow"), 2),
        GameData("Which month has 28 days?", listOf("Feb", "All", "Jan", "None"), 1),
        GameData("Which is hot?", listOf("Ice", "Fire", "Water", "Snow"), 1),
        GameData("Which is computer?", listOf("Laptop", "Table", "Book", "Pen"), 0),
        GameData("9 + 1 = ?", listOf("9", "10", "11", "12"), 1),

        GameData("Which is fastest animal?", listOf("Dog", "Horse", "Cheetah", "Lion"), 2),
        GameData("Which is capital of UP?", listOf("Agra", "Kanpur", "Lucknow", "Noida"), 2),
        GameData("Which is heavy?", listOf("Feather", "Stone", "Paper", "Leaf"), 1),
        GameData("Which is sweet?", listOf("Salt", "Sugar", "Chilli", "Lemon"), 1),
        GameData("12 / 4 = ?", listOf("2", "3", "4", "6"), 1),

        GameData("Which is a festival?", listOf("Diwali", "Monday", "January", "Summer"), 0),
        GameData("Which is used to fly?", listOf("Car", "Plane", "Train", "Bus"), 1),
        GameData("Which is liquid?", listOf("Water", "Stone", "Wood", "Iron"), 0),
        GameData("Which is green?", listOf("Apple", "Banana", "Grapes", "Orange"), 2),
        GameData("8 x 5 = ?", listOf("35", "40", "45", "50"), 1),

        GameData("Which is sense organ?", listOf("Heart", "Eye", "Liver", "Kidney"), 1),
        GameData("Which is day?", listOf("Sunday", "January", "Winter", "Night"), 0),
        GameData("Which is flower?", listOf("Rose", "Apple", "Potato", "Onion"), 0),
        GameData("Which is fast?", listOf("Turtle", "Snail", "Bike", "Ant"), 2),
        GameData("20 - 10 = ?", listOf("5", "10", "15", "20"), 1),

        GameData("Which is a game?", listOf("Cricket", "Milk", "Water", "Rice"), 0),
        GameData("Which is used to read?", listOf("Book", "Bat", "Ball", "Pen"), 0),
        GameData("Which is big?", listOf("Ant", "Elephant", "Cat", "Dog"), 1),
        GameData("Which is cold?", listOf("Ice", "Fire", "Sun", "Oil"), 0),
        GameData("4 x 9 = ?", listOf("32", "36", "40", "45"), 1),

        GameData("Which is transport?", listOf("Car", "Tree", "House", "Chair"), 0),
        GameData("Which is sky color?", listOf("Red", "Blue", "Green", "Black"), 1),
        GameData("Which is drink?", listOf("Milk", "Rice", "Bread", "Apple"), 0),
        GameData("Which is star?", listOf("Moon", "Sun", "Earth", "Mars"), 1),
        GameData("50 / 5 = ?", listOf("5", "10", "15", "20"), 1),

        GameData("Which is a body part?", listOf("Hand", "Table", "Chair", "Phone"), 0),
        GameData("Which is animal?", listOf("Cow", "Car", "Bus", "Bike"), 0),
        GameData("Which is tree?", listOf("Mango", "Potato", "Carrot", "Onion"), 0),
        GameData("Which is tall?", listOf("Building", "Ball", "Pen", "Book"), 0),
        GameData("100 - 50 = ?", listOf("40", "50", "60", "70"), 1)

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
            Toast.makeText(this, "Quiz Completed 🎉", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        val data = questions[level - 1]

        findViewById<TextView>(R.id.tvQuestion).text =
            "Quiz Level $level\n\n${data.question}"

        val tvHint = findViewById<TextView>(R.id.tvHint)
        val btnHint = findViewById<Button>(R.id.btnHint)
        tvHint.visibility = View.GONE

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

                    unlockLevel()
                    Toast.makeText(this, "Correct ✅", Toast.LENGTH_SHORT).show()

                    showInterstitialThen {
                        startActivity(
                            Intent(this, QuickQuizPlayActivity::class.java)
                                .putExtra("LEVEL", level + 1)
                        )
                        finish()
                    }

                } else {
                    Toast.makeText(this, "Wrong ❌", Toast.LENGTH_SHORT).show()
                    showInterstitialThen { recreate() }
                }
            }
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

                val correct = data.options[data.correctIndex]

                tvHint.text = when (answerLevel) {

                    1 -> {
                        val wrongs = data.options
                            .filterIndexed { i, _ -> i != data.correctIndex }
                            .shuffled()
                            .take(2)

                        "Answer Help 1:\n❌ Not: ${wrongs.joinToString(", ")}"
                    }

                    2 -> {
                        val wrong = data.options.first { it != correct }

                        "Answer Help 2:\nCorrect answer is between:\n✔ $correct  OR  ❓ $wrong"
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

    private fun unlockLevel() {
        val prefs = getSharedPreferences("QUIZ_PREFS", MODE_PRIVATE)
        val unlocked = prefs.getInt("QUIZ_UNLOCKED", 1)
        if (level >= unlocked) {
            prefs.edit().putInt("QUIZ_UNLOCKED", level + 1).apply()
        }
    }
}
