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

class PythonGamePlayActivity : AppCompatActivity() {

    private var level = 1

    // 🔒 SharedPreferences
    private val PREF_NAME = "PY_GAME_PREFS"
    private val KEY_UNLOCKED_LEVEL = "PY_UNLOCKED_LEVEL"

    // 🟢 Ads
    private var interstitialAd: InterstitialAd? = null
    private var rewardedAd: RewardedAd? = null

    // 🧠 Answer system
    private var answerLevel = 0
    private val MAX_ANSWER_LEVEL = 3

    // 🧠 QUESTIONS (UNCHANGED – tu yahin add karega)
    private val levels = listOf(

        GameData("Python is a ?", listOf("Compiler", "Interpreter", "OS", "Browser"), 1),
        GameData("Who developed Python?", listOf("Dennis Ritchie", "Guido van Rossum", "James Gosling", "Bjarne Stroustrup"), 1),
        GameData("Python file extension?", listOf(".py", ".java", ".c", ".txt"), 0),
        GameData("Python is ?", listOf("Low level", "High level", "Machine", "Assembly"), 1),
        GameData("Which keyword prints output?", listOf("echo", "print", "cout", "printf"), 1),

        GameData("Which symbol is comment?", listOf("//", "#", "/* */", "<!--"), 1),
        GameData("Which data type stores text?", listOf("int", "float", "str", "bool"), 2),
        GameData("Which is correct variable?", listOf("1x", "x1", "x-1", "int"), 1),
        GameData("Python is case sensitive?", listOf("Yes", "No", "Sometimes", "Never"), 0),
        GameData("Which is list?", listOf("{}", "()", "[]", "<>"), 2),

        GameData("Which is tuple?", listOf("[]", "{}", "()", "<>"), 2),
        GameData("Which is dictionary?", listOf("[]", "()", "{}", "<>"), 2),
        GameData("Which loop repeats?", listOf("if", "for", "def", "class"), 1),
        GameData("Which loop checks condition first?", listOf("for", "while", "do", "repeat"), 1),
        GameData("Which keyword defines function?", listOf("func", "def", "function", "define"), 1),

        GameData("Which keyword returns value?", listOf("return", "break", "pass", "exit"), 0),
        GameData("Which keyword skips iteration?", listOf("break", "continue", "pass", "skip"), 1),
        GameData("Which keyword stops loop?", listOf("stop", "exit", "break", "pass"), 2),
        GameData("Which keyword does nothing?", listOf("null", "void", "pass", "none"), 2),
        GameData("Python supports OOP?", listOf("Yes", "No", "Sometimes", "Never"), 0),

        GameData("Which keyword creates class?", listOf("class", "struct", "object", "define"), 0),
        GameData("Which keyword creates object?", listOf("new", "create", "object", "No keyword"), 3),
        GameData("Which function gives length?", listOf("length()", "len()", "size()", "count()"), 1),
        GameData("Which operator is power?", listOf("^", "**", "//", "%"), 1),
        GameData("Which operator is floor division?", listOf("/", "//", "%", "**"), 1),

        GameData("Which operator is modulus?", listOf("%", "/", "//", "**"), 0),
        GameData("Which data type is immutable?", listOf("list", "dict", "set", "tuple"), 3),
        GameData("Which data type is mutable?", listOf("tuple", "str", "list", "int"), 2),
        GameData("Index starts from?", listOf("0", "1", "-1", "Any"), 0),
        GameData("Negative index means?", listOf("Error", "From start", "From end", "None"), 2),

        GameData("Which keyword handles exception?", listOf("catch", "try", "except", "error"), 2),
        GameData("Which keyword raises exception?", listOf("raise", "throw", "error", "except"), 0),
        GameData("Which block always executes?", listOf("try", "except", "else", "finally"), 3),
        GameData("Which keyword imports module?", listOf("include", "import", "using", "require"), 1),
        GameData("Which function reads input?", listOf("scan()", "input()", "read()", "get()"), 1),

        GameData("Which function converts to int?", listOf("int()", "str()", "float()", "bool()"), 0),
        GameData("Which function converts to string?", listOf("int()", "str()", "char()", "string()"), 1),
        GameData("Which is boolean true?", listOf("true", "True", "TRUE", "1"), 1),
        GameData("Which is boolean false?", listOf("false", "False", "FALSE", "0"), 1),
        GameData("Which operator is AND?", listOf("&", "&&", "and", "AND"), 2),

        GameData("Which operator is OR?", listOf("|", "||", "or", "OR"), 2),
        GameData("Which operator is NOT?", listOf("!", "~", "not", "NOT"), 2),
        GameData("Which loop runs fixed times?", listOf("while", "for", "do", "repeat"), 1),
        GameData("Which keyword checks condition?", listOf("if", "for", "while", "def"), 0),
        GameData("Which keyword checks multiple conditions?", listOf("if-else", "elif", "switch", "case"), 1),

        GameData("Which keyword defines lambda?", listOf("def", "lambda", "func", "anon"), 1),
        GameData("Lambda function is?", listOf("Named", "Anonymous", "Recursive", "Static"), 1),
        GameData("Which function sorts list?", listOf("sort()", "order()", "arrange()", "set()"), 0),
        GameData("Which function reverses list?", listOf("reverse()", "invert()", "flip()", "back()"), 0),
        GameData("Which function adds element?", listOf("add()", "append()", "insert()", "Both B & C"), 3),

        GameData("Which removes element?", listOf("delete()", "remove()", "pop()", "Both B & C"), 3),
        GameData("Which keyword deletes variable?", listOf("remove", "del", "delete", "clear"), 1),
        GameData("Which checks membership?", listOf("in", "has", "contains", "exists"), 0),
        GameData("Which creates empty list?", listOf("()", "{}", "[]", "<>"), 2),
        GameData("Which creates empty dict?", listOf("[]", "()", "{}", "<>"), 2),

        GameData("Which keyword creates set?", listOf("{}", "set()", "[]", "()"), 1),
        GameData("Set stores?", listOf("Duplicate", "Ordered", "Unordered unique", "Indexed"), 2),
        GameData("Which keyword defines module?", listOf("module", "import", "file", "No keyword"), 3),
        GameData("Which file runs Python?", listOf(".exe", ".py", ".class", ".run"), 1),
        GameData("Which mode opens file read?", listOf("r", "w", "a", "rw"), 0),

        GameData("Which mode writes file?", listOf("r", "w", "a", "rw"), 1),
        GameData("Which mode appends file?", listOf("r", "w", "a", "rw"), 2),
        GameData("Which function opens file?", listOf("open()", "file()", "read()", "load()"), 0),
        GameData("Which function closes file?", listOf("close()", "stop()", "end()", "exit()"), 0),
        GameData("Which keyword ends program?", listOf("stop", "exit", "quit()", "Both B & C"), 3)

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

        if (level > levels.size) {
            Toast.makeText(this, "Python Game Completed 🎉", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        val data = levels[level - 1]

        val tvQuestion = findViewById<TextView>(R.id.tvQuestion)
        val tvHint = findViewById<TextView>(R.id.tvHint)
        val btnHint = findViewById<Button>(R.id.btnHint)

        val btn1 = findViewById<Button>(R.id.btn1)
        val btn2 = findViewById<Button>(R.id.btn2)
        val btn3 = findViewById<Button>(R.id.btn3)
        val btn4 = findViewById<Button>(R.id.btn4)
        val btnRestart = findViewById<Button>(R.id.btnRestart)

        tvHint.visibility = View.GONE
        tvQuestion.text = "Python Level $level\n\n${data.question}"

        val buttons = listOf(btn1, btn2, btn3, btn4)

        buttons.forEachIndexed { index, button ->
            button.text = data.options[index]
            button.setOnClickListener {

                if (index == data.correctIndex) {

                    unlockNextLevel(level)
                    Toast.makeText(this, "Correct ✅", Toast.LENGTH_SHORT).show()

                    startActivity(
                        Intent(this, PythonGamePlayActivity::class.java)
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
    private fun unlockNextLevel(currentLevel: Int) {
        val prefs = getSharedPreferences(PREF_NAME, MODE_PRIVATE)
        val unlocked = prefs.getInt(KEY_UNLOCKED_LEVEL, 1)
        if (currentLevel >= unlocked && currentLevel < levels.size) {
            prefs.edit()
                .putInt(KEY_UNLOCKED_LEVEL, currentLevel + 1)
                .apply()
        }
    }
}
