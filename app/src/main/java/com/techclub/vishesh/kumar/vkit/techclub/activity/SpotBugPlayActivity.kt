package com.techclub.vishesh.kumar.vkit.techclub.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAdLoadCallback
import com.techclub.vishesh.kumar.vkit.techclub.R
import com.techclub.vishesh.kumar.vkit.techclub.model.GameData

class SpotBugPlayActivity : AppCompatActivity() {

    private var level = 1

    // 🔐 PREFS
    private val PREF = "SPOT_BUG_PREF"
    private val KEY = "SPOT_BUG_UNLOCKED"

    // 🟢 ADS
    private var interstitialAd: InterstitialAd? = null
    private var rewardedAd: RewardedInterstitialAd? = null
    private var hintUsed = false

    // 🔥 QUESTIONS (tu baad me add karta rahega)
    private val questions = listOf(

        GameData(
            "C Code:\nint a = 5\nprintf(\"%d\", a);",
            listOf("Syntax Error", "Logical Error", "Runtime Error", "No Error"), 0
        ),

        GameData(
            "C Code:\nint a = 5;\nint b = 0;\nprintf(\"%d\", a/b);",
            listOf("Syntax Error", "Logical Error", "Runtime Error", "No Error"), 2
        ),

        GameData(
            "C Code:\nfor(int i=0;i<5;i++);\nprintf(\"Hello\");",
            listOf("Syntax Error", "Logical Error", "Runtime Error", "No Error"), 1
        ),

        GameData(
            "C Code:\nprintf(\"Hello World\");",
            listOf("Syntax Error", "Logical Error", "Runtime Error", "No Error"), 3
        ),

        GameData(
            "C Code:\nint arr[5];\narr[10] = 3;",
            listOf("Syntax Error", "Logical Error", "Runtime Error", "No Error"), 2
        ),

        GameData(
            "C Code:\nint a=10;\nif(a=5)\nprintf(\"Yes\");",
            listOf("Syntax Error", "Logical Error", "Runtime Error", "No Error"), 1
        ),

        GameData(
            "C Code:\nint a=10;\nif(a==10)\nprintf(\"OK\");",
            listOf("Syntax Error", "Logical Error", "Runtime Error", "No Error"), 3
        ),

        GameData(
            "C Code:\nint x;\nprintf(\"%d\",x);",
            listOf("Syntax Error", "Logical Error", "Runtime Error", "No Error"), 1
        ),

        GameData(
            "C Code:\nprintf(\"%d\",10/0);",
            listOf("Syntax Error", "Logical Error", "Runtime Error", "No Error"), 2
        ),

        GameData(
            "C Code:\nint a=5;\nprintf(\"%f\",a);",
            listOf("Syntax Error", "Logical Error", "Runtime Error", "No Error"), 1
        ),

        GameData(
            "C Code:\nint a=5;\nwhile(a>0)\nprintf(\"%d\",a);",
            listOf("Syntax Error", "Logical Error", "Runtime Error", "No Error"), 1
        ),

        GameData(
            "C Code:\nint a=5;\ndo{printf(\"%d\",a);}while(a>0);",
            listOf("Syntax Error", "Logical Error", "Runtime Error", "No Error"), 1
        ),

        GameData(
            "C Code:\nint arr[3]={1,2,3};\nprintf(\"%d\",arr[2]);",
            listOf("Syntax Error", "Logical Error", "Runtime Error", "No Error"), 3
        ),

        GameData(
            "C Code:\nint arr[3];\nprintf(\"%d\",arr[3]);",
            listOf("Syntax Error", "Logical Error", "Runtime Error", "No Error"), 2
        ),

        GameData(
            "C Code:\nfloat a=5.5;\nprintf(\"%d\",a);",
            listOf("Syntax Error", "Logical Error", "Runtime Error", "No Error"), 1
        ),

        GameData(
            "C Code:\nint a=5;\nprintf(a);",
            listOf("Syntax Error", "Logical Error", "Runtime Error", "No Error"), 2
        ),

        GameData(
            "C Code:\nint a=5;\nif(a>3)\n{\nprintf(\"Hi\");",
            listOf("Syntax Error", "Logical Error", "Runtime Error", "No Error"), 0
        ),

        GameData(
            "C Code:\nint a=5;\nif(a>3);\nprintf(\"Hi\");",
            listOf("Syntax Error", "Logical Error", "Runtime Error", "No Error"), 1
        ),

        GameData(
            "C Code:\nint i;\nfor(i=0;i<5;i++)\nprintf(\"%d\",i);",
            listOf("Syntax Error", "Logical Error", "Runtime Error", "No Error"), 3
        ),

        GameData(
            "C Code:\nint i=0;\nfor(;i<5;)\nprintf(\"%d\",i);",
            listOf("Syntax Error", "Logical Error", "Runtime Error", "No Error"), 1
        ),

        // ---- 20 ----

        GameData(
            "C Code:\nint a=5;\nswitch(a){case 5: printf(\"Hi\");}",
            listOf("Syntax Error", "Logical Error", "Runtime Error", "No Error"), 3
        ),

        GameData(
            "C Code:\nswitch(){case 1: printf(\"Hi\");}",
            listOf("Syntax Error", "Logical Error", "Runtime Error", "No Error"), 0
        ),

        GameData(
            "C Code:\nint a=5;\nswitch(a){case 5 printf(\"Hi\");}",
            listOf("Syntax Error", "Logical Error", "Runtime Error", "No Error"), 0
        ),

        GameData(
            "C Code:\nchar c='A';\nprintf(\"%d\",c);",
            listOf("Syntax Error", "Logical Error", "Runtime Error", "No Error"), 3
        ),

        GameData(
            "C Code:\nchar c=\"A\";",
            listOf("Syntax Error", "Logical Error", "Runtime Error", "No Error"), 0
        ),

        GameData(
            "C Code:\nprintf(\"%s\",'Hello');",
            listOf("Syntax Error", "Logical Error", "Runtime Error", "No Error"), 2
        ),

        GameData(
            "C Code:\nint *p;\nprintf(\"%d\",*p);",
            listOf("Syntax Error", "Logical Error", "Runtime Error", "No Error"), 2
        ),

        GameData(
            "C Code:\nint a=5;\nint *p=&a;\nprintf(\"%d\",*p);",
            listOf("Syntax Error", "Logical Error", "Runtime Error", "No Error"), 3
        ),

        GameData(
            "C Code:\nint *p=NULL;\nprintf(\"%d\",*p);",
            listOf("Syntax Error", "Logical Error", "Runtime Error", "No Error"), 2
        ),

        GameData(
            "C Code:\nint a[2]={1,2,3};",
            listOf("Syntax Error", "Logical Error", "Runtime Error", "No Error"), 1
        ),

        // ---- 30 ----

        GameData(
            "C Code:\nint a=5;\nprintf(\"%d\",a++ + ++a);",
            listOf("Syntax Error", "Logical Error", "Runtime Error", "No Error"), 1
        ),

        GameData(
            "C Code:\nint a=5;\nprintf(\"%d\",++a);",
            listOf("Syntax Error", "Logical Error", "Runtime Error", "No Error"), 3
        ),

        GameData(
            "C Code:\nint a=5;\nif(a<3)\nprintf(\"No\");",
            listOf("Syntax Error", "Logical Error", "Runtime Error", "No Error"), 3
        ),

        GameData(
            "C Code:\nint a=5;\nif(a>3)\nelse\nprintf(\"Hi\");",
            listOf("Syntax Error", "Logical Error", "Runtime Error", "No Error"), 0
        ),

        GameData(
            "C Code:\nint i=0;\nwhile(i<5){printf(\"%d\",i);i++;}",
            listOf("Syntax Error", "Logical Error", "Runtime Error", "No Error"), 3
        ),

        GameData(
            "C Code:\nint i=0;\nwhile(i<5);{printf(\"%d\",i);}",
            listOf("Syntax Error", "Logical Error", "Runtime Error", "No Error"), 1
        ),

        GameData(
            "C Code:\nint a=5;\nreturn a;",
            listOf("Syntax Error", "Logical Error", "Runtime Error", "No Error"), 0
        ),

        GameData(
            "C Code:\nprintf(\"%d\");",
            listOf("Syntax Error", "Logical Error", "Runtime Error", "No Error"), 2
        ),

        GameData(
            "C Code:\nprintf(\"Hello\";",
            listOf("Syntax Error", "Logical Error", "Runtime Error", "No Error"), 0
        ),

        GameData(
            "C Code:\nprintf(\"%d\",5.5);",
            listOf("Syntax Error", "Logical Error", "Runtime Error", "No Error"), 1
        )

        // ---- 100 complete karne ke liye isi pattern ko repeat kar
        // variable, loop, pointer, array, condition thoda change karta ja
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_c_game_play)

        level = intent.getIntExtra("LEVEL", 1)

        // 🔥 INIT ADS
        MobileAds.initialize(this)
        loadInterstitial()
        loadRewarded()

        loadLevel()
    }

    // ================= ADS =================

    private fun loadInterstitial() {
        InterstitialAd.load(
            this,
            "INTERSTITIAL_AD_UNIT_ID", // 🔁 baad me fill karega
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

    private fun showInterstitial(after: () -> Unit) {
        if (interstitialAd != null) {
            interstitialAd?.fullScreenContentCallback =
                object : FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {
                        interstitialAd = null
                        loadInterstitial()
                        after()
                    }
                }
            interstitialAd?.show(this)
        } else {
            after()
        }
    }

    private fun loadRewarded() {
        RewardedInterstitialAd.load(
            this,
            "REWARDED_AD_UNIT_ID", // 💡 baad me fill karega
            AdRequest.Builder().build(),
            object : RewardedInterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: RewardedInterstitialAd) {
                    rewardedAd = ad
                }
                override fun onAdFailedToLoad(error: LoadAdError) {
                    rewardedAd = null
                }
            }
        )
    }

    // ================= GAME =================

    private fun loadLevel() {

        if (level > questions.size) {
            Toast.makeText(this, "Spot the Bug Completed 🎉", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        hintUsed = false

        val data = questions[level - 1]

        val tvQuestion = findViewById<TextView>(R.id.tvQuestion)
        val btn1 = findViewById<Button>(R.id.btn1)
        val btn2 = findViewById<Button>(R.id.btn2)
        val btn3 = findViewById<Button>(R.id.btn3)
        val btn4 = findViewById<Button>(R.id.btn4)
        val btnRestart = findViewById<Button>(R.id.btnRestart)
        val btnHint = findViewById<Button>(R.id.btnHint)

        tvQuestion.text = "Spot the Bug – Level $level\n\n${data.question}"

        val buttons = listOf(btn1, btn2, btn3, btn4)

        buttons.forEachIndexed { index, button ->
            button.text = data.options[index]
            button.setBackgroundColor(resources.getColor(android.R.color.holo_blue_dark))

            button.setOnClickListener {
                if (index == data.correctIndex) {
                    unlockNext()
                    Toast.makeText(this, "Correct ✅", Toast.LENGTH_SHORT).show()

                    startActivity(
                        Intent(this, SpotBugPlayActivity::class.java)
                            .putExtra("LEVEL", level + 1)
                    )
                    finish()
                } else {
                    Toast.makeText(this, "Wrong ❌", Toast.LENGTH_SHORT).show()
                    showInterstitial { recreate() }
                }
            }
        }

        // 🔁 Restart
        btnRestart.setOnClickListener {
            showInterstitial { recreate() }
        }

        // 💡 Hint (Rewarded)
        btnHint.setOnClickListener {
            if (rewardedAd != null && !hintUsed) {
                rewardedAd?.show(this) {
                    hintUsed = true
                    buttons[data.correctIndex]
                        .setBackgroundColor(resources.getColor(android.R.color.holo_green_dark))
                }
                rewardedAd = null
                loadRewarded()
            } else {
                Toast.makeText(this, "Hint not available", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun unlockNext() {
        val prefs = getSharedPreferences(PREF, MODE_PRIVATE)
        val unlocked = prefs.getInt(KEY, 1)
        if (level >= unlocked) {
            prefs.edit().putInt(KEY, level + 1).apply()
        }
    }
}