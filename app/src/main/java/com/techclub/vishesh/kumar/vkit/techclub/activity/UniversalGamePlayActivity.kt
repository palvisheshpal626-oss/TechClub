package com.techclub.vishesh.kumar.vkit.techclub.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.techclub.vishesh.kumar.vkit.techclub.R
import com.techclub.vishesh.kumar.vkit.techclub.model.GameData

// 🔴 AdMob
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.FullScreenContentCallback

// 🔵 Facebook
import com.facebook.ads.*

class UniversalGamePlayActivity : AppCompatActivity() {

    private var level = 1

    // 🔐 PREF
    private val PREF = "GAME_PREF"
    private val KEY = "GAME_UNLOCKED"

    // 🔴 AdMob
    private var admobInterstitial: InterstitialAd? = null

    // 🔵 Facebook
    private var fbInterstitial: com.facebook.ads.InterstitialAd? = null

    // ❓ QUESTIONS (tu baad me add karega)
    private val questions = listOf(

        GameData("What is the output of: 2 + 3 * 4 ?",
            listOf("20","14","24","10"),1),

        GameData("Which keyword is used to define a constant in C?",
            listOf("const","final","static","define"),0),

        GameData("Which data type is used to store true/false?",
            listOf("int","char","boolean","float"),2),

        GameData("What does CPU stand for?",
            listOf("Central Processing Unit","Computer Personal Unit","Central Program Unit","Control Processing Unit"),0),

        GameData("Which symbol is used for single-line comment in C?",
            listOf("//","/*","<!--","#"),0),

        GameData("Which loop runs at least once?",
            listOf("for","while","do-while","foreach"),2),

        GameData("Which is NOT a programming language?",
            listOf("Python","Java","HTML","Windows"),3),

        GameData("What is the extension of C file?",
            listOf(".cpp",".java",".c",".py"),2),

        GameData("Which operator is used for equality check?",
            listOf("=","==","!=","<="),1),

        GameData("Which one is an operating system?",
            listOf("Compiler","Linux","Browser","Editor"),1),

        // ---- 10 ----

        GameData("Which data structure uses FIFO?",
            listOf("Stack","Queue","Array","Tree"),1),

        GameData("RAM is a ____ memory.",
            listOf("Permanent","Secondary","Temporary","Offline"),2),

        GameData("Which is faster?",
            listOf("HDD","SSD","CD","DVD"),1),

        GameData("Which keyword is used to create object in Java?",
            listOf("class","new","object","this"),1),

        GameData("What does URL stand for?",
            listOf("Uniform Resource Locator","Universal Resource Link","Unique Resource Location","Uniform Reference Link"),0),

        GameData("Which language is used for Android?",
            listOf("Swift","Kotlin","Ruby","PHP"),1),

        GameData("Which one is NOT a loop?",
            listOf("for","while","repeat","switch"),3),

        GameData("Which symbol ends a statement in C?",
            listOf(":",";"," .",","),1),

        GameData("Binary number system uses base?",
            listOf("2","8","10","16"),0),

        GameData("Which is an input device?",
            listOf("Monitor","Printer","Keyboard","Speaker"),2),

        // ---- 20 ----

        GameData("What is 101 in binary equal to?",
            listOf("4","5","6","7"),1),

        GameData("Which is used to style web pages?",
            listOf("HTML","CSS","JS","SQL"),1),

        GameData("Which company developed Java?",
            listOf("Google","Sun Microsystems","Microsoft","Apple"),1),

        GameData("Which one is NOT OOP concept?",
            listOf("Encapsulation","Inheritance","Compilation","Polymorphism"),2),

        GameData("Which symbol is used for AND operator?",
            listOf("&","&&","|","||"),1),

        GameData("Which is the brain of computer?",
            listOf("RAM","CPU","Hard Disk","Keyboard"),1),

        GameData("What does AI stand for?",
            listOf("Automatic Intelligence","Artificial Intelligence","Advanced Internet","Applied Interface"),1),

        GameData("Which is used to store multiple values?",
            listOf("Variable","Array","Function","Class"),1),

        GameData("Which one is NOT a database?",
            listOf("MySQL","Firebase","Oracle","Android"),3),

        GameData("Which language is used for iOS?",
            listOf("Java","Kotlin","Swift","C#"),2),

        // ---- 30 ----

        GameData("Which is an example of cloud storage?",
            listOf("Google Drive","Pendrive","Hard Disk","RAM"),0),

        GameData("Which operator increases value by 1?",
            listOf("--","++","+","+="),1),

        GameData("Which is a search engine?",
            listOf("Chrome","Google","Windows","Linux"),1),

        GameData("What is the full form of HTTP?",
            listOf("HyperText Transfer Protocol","High Transfer Text Protocol","Hyper Tool Transfer Program","None"),0),

        GameData("Which is NOT a mobile OS?",
            listOf("Android","iOS","Windows","Ubuntu"),3),

        GameData("Which one is backend language?",
            listOf("HTML","CSS","Python","XML"),2),

        GameData("Which keyword stops loop?",
            listOf("stop","exit","break","end"),2),

        GameData("Which is a valid email symbol?",
            listOf("@","#","$","%"),0),

        GameData("Which unit measures CPU speed?",
            listOf("GB","MHz","Pixels","Watts"),1),

        GameData("Which one is NOT hardware?",
            listOf("Mouse","Keyboard","RAM","Windows"),3),

        // ---- 40 ----

        GameData("Which data type stores decimal?",
            listOf("int","char","float","bool"),2),

        GameData("Which is used to find errors?",
            listOf("Compiler","Debugger","Editor","OS"),1),

        GameData("Which one is open-source?",
            listOf("Windows","Linux","MacOS","iOS"),1),

        GameData("Which symbol is modulus?",
            listOf("%","/","*","-"),0),

        GameData("Which language is used for ML?",
            listOf("HTML","Python","CSS","C"),1),

        GameData("Which is faster memory?",
            listOf("Cache","RAM","ROM","HDD"),0),

        GameData("Which is NOT a browser?",
            listOf("Chrome","Firefox","Opera","Android"),3),

        GameData("What does SQL stand for?",
            listOf("Structured Query Language","Simple Query Language","System Query Logic","None"),0),

        GameData("Which one is used to upload app?",
            listOf("Play Store","Android Studio","AdMob","Firebase"),0),

        GameData("Which is a logic gate?",
            listOf("AND","ADD","SUM","PLUS"),0),

        // ---- 50 ----

        // 👉 Repeat difficulty with same pattern till 100
        // You can safely duplicate structure with changed questions

    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_universal_game)

        level = intent.getIntExtra("LEVEL", 1)

        MobileAds.initialize(this)
        AudienceNetworkAds.initialize(this)

        loadAdmob()
        loadFacebook()

        loadLevel()
    }

    // ================= ADS =================

    private fun loadAdmob() {
        InterstitialAd.load(
            this,
            "ADMOB_INTERSTITIAL_ID",
            AdRequest.Builder().build(),
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: InterstitialAd) {
                    admobInterstitial = ad
                }
                override fun onAdFailedToLoad(error: LoadAdError) {
                    admobInterstitial = null
                }
            }
        )
    }

    private fun loadFacebook() {
        fbInterstitial = com.facebook.ads.InterstitialAd(
            this,
            "FACEBOOK_INTERSTITIAL_ID"
        )
        fbInterstitial?.loadAd()
    }

    private fun showAdThen(action: () -> Unit) {
        when {
            admobInterstitial != null -> {
                admobInterstitial?.fullScreenContentCallback =
                    object : FullScreenContentCallback() {
                        override fun onAdDismissedFullScreenContent() {
                            admobInterstitial = null
                            loadAdmob()
                            action()
                        }
                    }
                admobInterstitial?.show(this)
            }

            fbInterstitial?.isAdLoaded == true -> {
                fbInterstitial?.show()
                action()
            }

            else -> action()
        }
    }

    // ================= GAME =================

    private fun loadLevel() {

        if (level > questions.size) {
            Toast.makeText(this, "Game Completed 🎉", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        val q = questions[level - 1]

        findViewById<TextView>(R.id.tvQuestion).text =
            "Level $level\n\n${q.question}"

        val buttons = listOf(
            findViewById<Button>(R.id.btn1),
            findViewById<Button>(R.id.btn2),
            findViewById<Button>(R.id.btn3),
            findViewById<Button>(R.id.btn4)
        )

        buttons.forEachIndexed { index, button ->
            button.text = q.options[index]
            button.setOnClickListener {

                if (index == q.correctIndex) {
                    unlock()
                    Toast.makeText(this, "Correct ✅", Toast.LENGTH_SHORT).show()

                    startActivity(
                        Intent(this, UniversalGamePlayActivity::class.java)
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
        if (level >= u) {
            p.edit().putInt(KEY, level + 1).apply()
        }
    }
}
