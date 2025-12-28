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
import com.google.android.gms.ads.FullScreenContentCallback

class CGamePlayActivity : AppCompatActivity() {

    private var level = 1

    // 🔐 SharedPreferences
    private val PREF_NAME = "C_GAME_PREFS"
    private val KEY_UNLOCKED_LEVEL = "UNLOCKED_LEVEL"

    // 🟢 Ads
    private var interstitialAd: InterstitialAd? = null
    private var rewardedAd: RewardedAd? = null

    // 🧠 Answer reveal system
    private var answerLevel = 0
    private val MAX_ANSWER_LEVEL = 3

    // 🧠 QUESTIONS (UNCHANGED – tu yahin add karta rahega)
    private val questions = listOf(

        GameData("What is C language?",
            listOf("Programming Language", "Game", "Browser", "OS"), 0),

        GameData("Who developed C language?",
            listOf("Dennis Ritchie", "James Gosling", "Guido van Rossum", "Bjarne Stroustrup"), 0),

        GameData("Which symbol ends a statement in C?",
            listOf(":", ";", ".", ","), 1),

        GameData("Entry point of C program?",
            listOf("start()", "main()", "run()", "init()"), 1),

        GameData("Header file for printf()?",
            listOf("<stdio.h>", "<stdlib.h>", "<conio.h>", "<string.h>"), 0),

        GameData("Single line comment?",
            listOf("//", "/* */", "#", "<!-- -->"), 0),

        GameData("Loop that runs at least once?",
            listOf("for", "while", "do-while", "foreach"), 2),

        GameData("Data type for decimal?",
            listOf("int", "char", "float", "bool"), 2),

        GameData("Size of int (usually)?",
            listOf("1 byte", "2 bytes", "4 bytes", "8 bytes"), 2),

        GameData("Logical AND operator?",
            listOf("&", "&&", "|", "||"), 1),

        GameData("Keyword for constant?",
            listOf("const", "final", "static", "define"), 0),

        GameData("Array index starts from?",
            listOf("0", "1", "-1", "Depends"), 0),

        GameData("Keyword to return value?",
            listOf("break", "continue", "return", "exit"), 2),

        GameData("Which is NOT a C keyword?",
            listOf("auto", "register", "class", "static"), 2),

        GameData("Which data type stores character?",
            listOf("int", "char", "float", "double"), 1),

        GameData("Which loop is entry controlled?",
            listOf("do-while", "while", "for", "Both while & for"), 3),

        GameData("Which loop is exit controlled?",
            listOf("for", "while", "do-while", "foreach"), 2),

        GameData("Format specifier for int?",
            listOf("%c", "%d", "%f", "%s"), 1),

        GameData("Format specifier for float?",
            listOf("%d", "%f", "%c", "%s"), 1),

        GameData("Which header has scanf()?",
            listOf("<stdio.h>", "<stdlib.h>", "<math.h>", "<string.h>"), 0),

        GameData("Which operator is used for address?",
            listOf("&", "*", "#", "@"), 0),

        GameData("Which operator is dereference?",
            listOf("&", "*", "#", "@"), 1),

        GameData("Pointer stores?",
            listOf("Value", "Address", "Character", "Function"), 1),

        GameData("Which is correct pointer declaration?",
            listOf("int *p;", "int p*;", "*int p;", "pointer int p;"), 0),

        GameData("Which keyword defines macro?",
            listOf("#define", "#include", "#macro", "#const"), 0),

        GameData("Which header supports malloc()?",
            listOf("<stdlib.h>", "<stdio.h>", "<math.h>", "<string.h>"), 0),

        GameData("malloc() returns?",
            listOf("int", "void*", "char", "float"), 1),

        GameData("free() is used for?",
            listOf("Allocate memory", "Free memory", "Resize memory", "Copy memory"), 1),

        GameData("Which function copies string?",
            listOf("strcpy()", "strcmp()", "strlen()", "strcat()"), 0),

        GameData("Which function compares string?",
            listOf("strcpy()", "strcmp()", "strlen()", "strcat()"), 1),

        GameData("Which function finds string length?",
            listOf("strlen()", "strcpy()", "strcmp()", "strcat()"), 0),

        GameData("Which keyword exits loop?",
            listOf("exit", "break", "continue", "stop"), 1),

        GameData("continue keyword does?",
            listOf("Exit loop", "Skip iteration", "Stop program", "Restart loop"), 1),

        GameData("Which storage class has local scope?",
            listOf("auto", "extern", "static", "register"), 0),

        GameData("Which storage class has global scope?",
            listOf("auto", "register", "extern", "static"), 2),

        GameData("Which keyword creates structure?",
            listOf("struct", "class", "record", "object"), 0),

        GameData("Which operator accesses structure?",
            listOf(".", "->", "::", ":"), 0),

        GameData("Which operator accesses structure pointer?",
            listOf(".", "->", "::", ":"), 1),

        GameData("Union keyword?",
            listOf("union", "struct", "record", "class"), 0),

        GameData("Difference between struct & union?",
            listOf("Memory", "Syntax", "Keyword", "Scope"), 0),

        GameData("Which file extension for C?",
            listOf(".c", ".cpp", ".java", ".py"), 0),

        GameData("Which compiler for C?",
            listOf("gcc", "javac", "python", "node"), 0),

        GameData("Which operator is conditional?",
            listOf("?", ":", "?:", "&&"), 2),

        GameData("Which keyword stops program?",
            listOf("exit()", "stop()", "end()", "break"), 0),

        GameData("Which function prints output?",
            listOf("scanf()", "printf()", "input()", "echo()"), 1),

        GameData("Which function takes input?",
            listOf("scanf()", "printf()", "cin", "input"), 0),

        GameData("Which operator is modulus?",
            listOf("%", "/", "*", "+"), 0),

        GameData("Which operator increments?",
            listOf("++", "--", "+=", "=="), 0),

        GameData("Which operator decrements?",
            listOf("--", "++", "-=", "=="), 0),

        GameData("Which header handles math?",
            listOf("<math.h>", "<stdio.h>", "<stdlib.h>", "<string.h>"), 0),

        GameData("Which function calculates square root?",
            listOf("sqrt()", "pow()", "root()", "square()"), 0),

        GameData("Which loop best for known iterations?",
            listOf("for", "while", "do-while", "foreach"), 0),

        GameData("Which keyword creates infinite loop?",
            listOf("for(;;)", "while()", "loop()", "repeat()"), 0),

        GameData("Which data type holds large integer?",
            listOf("int", "long", "float", "double"), 1),

        GameData("Which data type holds decimal?",
            listOf("float", "int", "char", "bool"), 0),

        GameData("Which operator has highest priority?",
            listOf("()", "*", "+", "="), 0),

        GameData("Which keyword defines function?",
            listOf("function", "define", "void", "def"), 2),

        GameData("Which keyword has no return?",
            listOf("int", "float", "void", "char"), 2),

        GameData("Which header handles file?",
            listOf("<stdio.h>", "<file.h>", "<fs.h>", "<io.h>"), 0),

        GameData("File pointer type?",
            listOf("FILE", "file", "fp", "pointer"), 0),

        GameData("Which mode opens file for read?",
            listOf("r", "w", "a", "rw"), 0),

        GameData("Which mode appends file?",
            listOf("r", "w", "a", "rw"), 2),

        GameData("Which function closes file?",
            listOf("fclose()", "close()", "end()", "stop()"), 0),

        GameData("Which function reads file?",
            listOf("fscanf()", "fprintf()", "read()", "input()"), 0),

        GameData("Which function writes file?",
            listOf("fprintf()", "fscanf()", "print()", "write()"), 0),

        GameData("Which keyword avoids name conflict?",
            listOf("static", "extern", "register", "auto"), 0),

        GameData("Which operator is bitwise AND?",
            listOf("&", "&&", "|", "||"), 0),

        GameData("Which operator is bitwise OR?",
            listOf("|", "||", "&", "&&"), 0),

        GameData("Which operator shifts left?",
            listOf("<<", ">>", "<", ">"), 0),

        GameData("Which operator shifts right?",
            listOf(">>", "<<", "<", ">"), 0)

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
            interstitialAd?.fullScreenContentCallback =
                object : FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {
                        interstitialAd = null
                        loadInterstitialAd()
                        action()
                    }
                }
            interstitialAd?.show(this)
        } else {
            action()
        }
    }

    // ================= LEVEL =================

    private fun loadLevel() {

        answerLevel = 0 // 🔥 reset per level

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
        tvQuestion.text = "Level $level\n\n${data.question}"

        val buttons = listOf(btn1, btn2, btn3, btn4)

        buttons.forEachIndexed { index, button ->
            button.text = data.options[index]
            button.setOnClickListener {

                if (index == data.correctIndex) {
                    unlockNextLevel()
                    Toast.makeText(this, "Correct ✅", Toast.LENGTH_SHORT).show()

                    startActivity(
                        Intent(this, CGamePlayActivity::class.java)
                            .putExtra("LEVEL", level + 1)
                    )
                    finish()

                } else {
                    Toast.makeText(this, "Wrong ❌", Toast.LENGTH_SHORT).show()
                    showInterstitialThen { recreate() }
                }
            }
        }

        // 🔁 Restart → Interstitial
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
                            .filterIndexed { i, _ -> i != data.correctIndex }
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

    // 🔓 Unlock next level
    private fun unlockNextLevel() {
        val prefs = getSharedPreferences(PREF_NAME, MODE_PRIVATE)
        val unlocked = prefs.getInt(KEY_UNLOCKED_LEVEL, 1)
        if (level >= unlocked) {
            prefs.edit()
                .putInt(KEY_UNLOCKED_LEVEL, level + 1)
                .apply()
        }
    }
}
