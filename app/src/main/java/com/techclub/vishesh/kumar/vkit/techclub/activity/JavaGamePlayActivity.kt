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

// AdMob
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback

class JavaGamePlayActivity : AppCompatActivity() {

    private var level = 1

    private val prefName = "JAVA_PREFS"
    private val keyUnlocked = "JAVA_UNLOCKED"

    // Ads
    private var interstitialAd: InterstitialAd? = null
    private var rewardedAd: RewardedAd? = null

    // Answer reveal system
    private var answerLevel = 0
    private val MAX_ANSWER_LEVEL = 3

    // 🧠 QUESTIONS (UNCHANGED)
    private val questions = listOf(

        GameData("Java is a ?", listOf("OS", "Language", "Browser", "Editor"), 1),
        GameData("Java developed by?", listOf("Google", "Microsoft", "Sun Microsystems", "Apple"), 2),
        GameData("Java file extension?", listOf(".js", ".java", ".class", ".exe"), 1),
        GameData("Entry point of Java?", listOf("main()", "start()", "run()", "init()"), 0),
        GameData("JVM stands for?", listOf("Java VM", "Java Virtual Machine", "Virtual Java", "None"), 1),

        GameData("Which is not Java keyword?", listOf("static", "void", "printf", "class"), 2),
        GameData("Which company owns Java now?", listOf("Sun", "Oracle", "Google", "IBM"), 1),
        GameData("Java is platform?", listOf("Dependent", "Independent", "Both", "None"), 1),
        GameData("Size of int?", listOf("2 byte", "4 byte", "8 byte", "Depends"), 1),
        GameData("Which is primitive?", listOf("String", "Array", "int", "Class"), 2),

        GameData("Which keyword creates object?", listOf("class", "object", "new", "this"), 2),
        GameData("Which is loop?", listOf("if", "switch", "for", "case"), 2),
        GameData("Which is not loop?", listOf("for", "while", "do", "switch"), 3),
        GameData("Java supports OOP?", listOf("Yes", "No", "Sometimes", "Never"), 0),
        GameData("Which is OOP concept?", listOf("Loop", "Inheritance", "Compiler", "Package"), 1),

        GameData("Which keyword stops inheritance?", listOf("final", "static", "void", "break"), 0),
        GameData("Which keyword refers current object?", listOf("this", "super", "new", "self"), 0),
        GameData("Which keyword refers parent?", listOf("this", "parent", "super", "base"), 2),
        GameData("Which is constructor?", listOf("Same name as class", "Any method", "Static", "Void"), 0),
        GameData("Constructor return type?", listOf("int", "void", "None", "class"), 2),

        GameData("Which access modifier is widest?", listOf("private", "protected", "public", "default"), 2),
        GameData("Which is not access modifier?", listOf("private", "public", "static", "protected"), 2),
        GameData("Which keyword handles exception?", listOf("catch", "throw", "try", "All"), 3),
        GameData("Which keyword throws exception?", listOf("throw", "throws", "try", "catch"), 0),
        GameData("Checked exception checked at?", listOf("Compile time", "Run time", "Both", "None"), 0),

        GameData("Which is unchecked exception?", listOf("IOException", "SQLException", "NullPointerException", "FileException"), 2),
        GameData("Array index starts from?", listOf("0", "1", "-1", "Any"), 0),
        GameData("String is?", listOf("Mutable", "Immutable", "Both", "None"), 1),
        GameData("Which package is default?", listOf("java.io", "java.lang", "java.util", "java.net"), 1),
        GameData("Which is used for input?", listOf("Scanner", "Print", "Echo", "Input"), 0),

        GameData("Which is not data type?", listOf("int", "float", "real", "double"), 2),
        GameData("Which keyword is constant?", listOf("final", "const", "static", "fix"), 0),
        GameData("Which operator is logical AND?", listOf("&", "&&", "|", "!"), 1),
        GameData("Which loop runs at least once?", listOf("for", "while", "do-while", "foreach"), 2),
        GameData("Which keyword ends loop?", listOf("stop", "exit", "break", "end"), 2),

        GameData("Java supports multiple inheritance?", listOf("Yes", "No", "Via interface", "Never"), 2),
        GameData("Interface keyword?", listOf("interface", "implements", "extends", "class"), 0),
        GameData("Implements used with?", listOf("class", "interface", "object", "method"), 0),
        GameData("Which is faster?", listOf("JVM", "JRE", "JDK", "None"), 0),
        GameData("Which contains compiler?", listOf("JRE", "JVM", "JDK", "API"), 2),

        GameData("Garbage collection done by?", listOf("Programmer", "JVM", "OS", "Compiler"), 1),
        GameData("Which keyword creates thread?", listOf("thread", "new", "run", "start"), 1),
        GameData("Thread start method?", listOf("run()", "start()", "init()", "begin()"), 1),
        GameData("Which collection has no duplicate?", listOf("List", "Set", "Map", "Array"), 1),
        GameData("HashMap stores data as?", listOf("List", "Key-Value", "Index", "Stack"), 1),

        GameData("Which sorting is fastest?", listOf("Bubble", "Selection", "Quick", "Insertion"), 2),
        GameData("Which is not collection?", listOf("ArrayList", "LinkedList", "Vector", "Array"), 3),
        GameData("Wrapper class of int?", listOf("Integer", "Int", "Number", "I"), 0),
        GameData("Which converts string to int?", listOf("parseInt()", "toInt()", "value()", "convert()"), 0),
        GameData("Which is mutable?", listOf("String", "StringBuilder", "StringBuffer", "Both B & C"), 3),

        GameData("Which keyword exits method?", listOf("break", "exit", "return", "stop"), 2),
        GameData("Java is?", listOf("Compiled", "Interpreted", "Both", "None"), 2),
        GameData("Which operator compares value?", listOf("=", "==", "!=", "==="), 1),
        GameData("Which is ternary operator?", listOf("?:", "&&", "||", "::"), 0),
        GameData("Which keyword prevents override?", listOf("final", "static", "private", "protected"), 0),

        GameData("Default value of int?", listOf("0", "1", "null", "undefined"), 0),
        GameData("Default value of boolean?", listOf("true", "false", "null", "0"), 1),
        GameData("Which is not OOP?", listOf("Encapsulation", "Abstraction", "Compilation", "Inheritance"), 2),
        GameData("Which file runs on JVM?", listOf(".java", ".class", ".jar", ".exe"), 1),
        GameData("Java supports pointers?", listOf("Yes", "No", "Indirect", "Sometimes"), 1)

    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_c_game_play)

        level = intent.getIntExtra("LEVEL", 1)

        if (questions.isEmpty()) {
            Toast.makeText(this, "No questions added", Toast.LENGTH_LONG).show()
            finish()
            return
        }

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

        answerLevel = 0 // reset per level

        if (level > questions.size) {
            Toast.makeText(this, "Game Completed 🎉", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        val data = questions[level - 1]

        val tvQuestion = findViewById<TextView>(R.id.tvQuestion)
        val tvHint = findViewById<TextView>(R.id.tvHint)
        val btnHint = findViewById<Button>(R.id.btnHint)

        val btn1 = findViewById<Button>(R.id.btn1)
        val btn2 = findViewById<Button>(R.id.btn2)
        val btn3 = findViewById<Button>(R.id.btn3)
        val btn4 = findViewById<Button>(R.id.btn4)
        val btnRestart = findViewById<Button>(R.id.btnRestart)

        tvHint.visibility = View.GONE
        tvQuestion.text = "Java Level $level\n\n${data.question}"

        val buttons = listOf(btn1, btn2, btn3, btn4)

        buttons.forEachIndexed { i, btn ->
            btn.text = data.options[i]
            btn.setOnClickListener {

                if (i == data.correctIndex) {

                    unlockLevel()
                    Toast.makeText(this, "Correct ✅", Toast.LENGTH_SHORT).show()

                    showInterstitialThen {
                        startActivity(
                            Intent(this, JavaGamePlayActivity::class.java)
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

        // 🔁 Restart
        btnRestart.setOnClickListener {
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

                val correct = data.options[data.correctIndex]

                tvHint.text = when (answerLevel) {

                    1 -> {
                        val wrongs = data.options
                            .filterIndexed { idx, _ -> idx != data.correctIndex }
                            .shuffled()
                            .take(2)
                        "Help 1:\n❌ Not: ${wrongs.joinToString(", ")}"
                    }

                    2 -> {
                        val wrong = data.options.first { it != correct }
                        "Help 2:\nCorrect answer is between:\n✔ $correct OR ❓ $wrong"
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
        val prefs = getSharedPreferences(prefName, MODE_PRIVATE)
        val unlocked = prefs.getInt(keyUnlocked, 1)
        if (level >= unlocked) {
            prefs.edit().putInt(keyUnlocked, level + 1).apply()
        }
    }
}
