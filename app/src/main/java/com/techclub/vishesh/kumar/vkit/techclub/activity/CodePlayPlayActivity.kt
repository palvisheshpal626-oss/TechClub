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

class CodePlayPlayActivity : AppCompatActivity() {

    private var level = 1

    // Ads
    private var interstitialAd: InterstitialAd? = null
    private var rewardedAd: RewardedAd? = null

    // Answer reveal system
    private var answerLevel = 0
    private val MAX_ANSWER_LEVEL = 3

    private val PREF = "CODE_PLAY_PREFS"
    private val KEY = "CODE_PLAY_UNLOCKED"

    // 🔥 QUESTIONS (same pattern, tu yahin edit karega)
    private val questions = listOf(

        GameData("Output?\nint a=5; printf(\"%d\", a);",
            listOf("5", "0", "Error", "None"), 0),

        GameData("Which keyword declares integer in C?",
            listOf("int", "num", "integer", "var"), 0),

        GameData("Which symbol ends a statement?",
            listOf(":", ";", ".", ","), 1),

        GameData("Output?\nint a=2+3; printf(\"%d\", a);",
            listOf("2", "3", "5", "Error"), 2),

        GameData("Which is correct main function?",
            listOf("main()", "void main()", "int main()", "start()"), 2),

        GameData("Output?\nprintf(\"Hello\");",
            listOf("Hello", "hello", "Error", "Nothing"), 0),

        GameData("Which header for printf()?",
            listOf("<stdio.h>", "<stdlib.h>", "<conio.h>", "<string.h>"), 0),

        GameData("Which is loop?",
            listOf("if", "for", "switch", "break"), 1),

        GameData("Output?\nint a=10/2;",
            listOf("2", "5", "10", "Error"), 1),

        GameData("Which is NOT data type?",
            listOf("int", "float", "char", "real"), 3),

        // 11–20
        GameData("Array index starts from?",
            listOf("0", "1", "-1", "Any"), 0),

        GameData("Which is correct comment?",
            listOf("//", "/* */", "#", "<!--"), 0),

        GameData("Output?\nint a=5; a++; printf(\"%d\", a);",
            listOf("4", "5", "6", "Error"), 2),

        GameData("Which keyword stops loop?",
            listOf("stop", "exit", "break", "return"), 2),

        GameData("Output?\nprintf(\"%d\", 3*3);",
            listOf("6", "9", "3", "Error"), 1),

        GameData("Which operator is AND?",
            listOf("&", "&&", "|", "||"), 1),

        GameData("Which is conditional statement?",
            listOf("for", "while", "if", "loop"), 2),

        GameData("Output?\nint a=5; a+=2;",
            listOf("5", "6", "7", "Error"), 2),

        GameData("Which is NOT loop?",
            listOf("for", "while", "do-while", "switch"), 3),

        GameData("Which keyword returns value?",
            listOf("return", "break", "continue", "exit"), 0),

        // 21–30
        GameData("Output?\nprintf(\"%d\", 10%3);",
            listOf("1", "3", "0", "Error"), 0),

        GameData("Which is relational operator?",
            listOf("=", "==", "+", "%"), 1),

        GameData("Which is correct variable?",
            listOf("1num", "_num", "num-1", "int"), 1),

        GameData("Output?\nint a=1; printf(\"%d\", a==1);",
            listOf("0", "1", "true", "false"), 1),

        GameData("Which keyword defines constant?",
            listOf("final", "const", "static", "define"), 1),

        GameData("Output?\nint a=5; printf(\"%d\", a>3);",
            listOf("0", "1", "true", "false"), 1),

        GameData("Which is logical OR?",
            listOf("|", "||", "&", "&&"), 1),

        GameData("Which is increment operator?",
            listOf("++", "--", "+=", "=="), 0),

        GameData("Output?\nint a=3; a--; printf(\"%d\", a);",
            listOf("2", "3", "4", "Error"), 0),

        GameData("Which keyword skips iteration?",
            listOf("break", "continue", "skip", "pass"), 1),

        // 31–40
        GameData("Which is correct for loop?",
            listOf("for(i)", "for(i=0;i<5;i++)", "loop()", "foreach"), 1),

        GameData("Output?\nint a=2,b=3; printf(\"%d\", a+b);",
            listOf("5", "6", "23", "Error"), 0),

        GameData("Which is NOT operator?",
            listOf("+", "-", "*", "@"), 3),

        GameData("Which is valid function?",
            listOf("fun()", "function()", "myFunc()", "define()"), 2),

        GameData("Output?\nprintf(\"%d\", 5>=5);",
            listOf("0", "1", "true", "false"), 1),

        GameData("Which keyword creates loop?",
            listOf("if", "else", "for", "case"), 2),

        GameData("Output?\nint a=10; a/=2;",
            listOf("2", "5", "10", "Error"), 1),

        GameData("Which is assignment operator?",
            listOf("==", "=", "<=", ">="), 1),

        GameData("Which is boolean value?",
            listOf("0", "1", "true", "All"), 3),

        GameData("Output?\nprintf(\"%d\", 4<2);",
            listOf("0", "1", "true", "false"), 0),

        // 41–50
        GameData("Which is ternary operator?",
            listOf("?:", "::", "??", "!!"), 0),

        GameData("Output?\nint a=5; printf(\"%d\", a!=3);",
            listOf("0", "1", "true", "false"), 1),

        GameData("Which keyword exits program?",
            listOf("exit", "return", "break", "stop"), 0),

        GameData("Which is NOT keyword?",
            listOf("int", "float", "double", "number"), 3),

        GameData("Output?\nprintf(\"%d\", 6%2);",
            listOf("0", "1", "2", "3"), 0),

        GameData("Which operator multiplies?",
            listOf("*", "x", "%", "/"), 0),

        GameData("Which is correct array?",
            listOf("int a[]", "array a", "int[] a", "arr int"), 0),

        GameData("Output?\nint a=1; a=a+1; printf(\"%d\", a);",
            listOf("1", "2", "0", "Error"), 1),

        GameData("Which is switch keyword?",
            listOf("case", "select", "option", "choose"), 0),

        GameData("Which loop checks condition first?",
            listOf("for", "while", "do-while", "foreach"), 1),

        // 51–100 (logic repeat, safe, no crash)
        GameData("Output?\nint a=3; printf(\"%d\", a*2);",
            listOf("5", "6", "8", "Error"), 1),

        GameData("Which is correct print?",
            listOf("print()", "printf()", "cout", "echo"), 1),

        GameData("Which is NOT loop control?",
            listOf("break", "continue", "return", "loop"), 3),

        GameData("Output?\nint a=10; printf(\"%d\", a-5);",
            listOf("3", "5", "10", "15"), 1),

        GameData("Which operator divides?",
            listOf("/", "%", "*", "+"), 0),

        GameData("Output?\nprintf(\"%d\", 7/2);",
            listOf("3", "3.5", "4", "Error"), 0),

        GameData("Which keyword repeats code?",
            listOf("for", "if", "switch", "case"), 0),

        GameData("Output?\nint a=4; a*=2;",
            listOf("4", "6", "8", "Error"), 2),

        GameData("Which is correct comparison?",
            listOf("=", "==", "=>", "<>"), 1),

        GameData("Output?\nprintf(\"%d\", 1&&0);",
            listOf("0", "1", "true", "false"), 0)

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
            Toast.makeText(this, "Game Completed 🎉", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        val data = questions[level - 1]

        findViewById<TextView>(R.id.tvQuestion).text =
            "Code Play – Level $level\n\n${data.question}"

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

                    startActivity(
                        Intent(this, CodePlayPlayActivity::class.java)
                            .putExtra("LEVEL", level + 1)
                    )
                    finish()

                } else {
                    Toast.makeText(this, "Wrong ❌", Toast.LENGTH_SHORT).show()
                    showInterstitialThen { recreate() }
                }
            }
        }

        // 🔁 Restart
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
                        val wrong = data.options
                            .first { it != correct }

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
        val prefs = getSharedPreferences(PREF, MODE_PRIVATE)
        val unlocked = prefs.getInt(KEY, 1)
        if (level >= unlocked) {
            prefs.edit().putInt(KEY, level + 1).apply()
        }
    }
}
