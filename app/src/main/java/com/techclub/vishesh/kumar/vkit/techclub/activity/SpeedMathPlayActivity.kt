package com.techclub.vishesh.kumar.vkit.techclub.activity

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
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
import com.google.android.gms.ads.FullScreenContentCallback

// Facebook
import com.facebook.ads.*

class SpeedMathPlayActivity : AppCompatActivity() {

    private var level = 1
    private var timer: CountDownTimer? = null

    // Prefs
    private val PREF = "SPEED_MATH_PREF"
    private val KEY = "SPEED_MATH_UNLOCKED"

    // Ads
    private var admobInterstitial: InterstitialAd? = null
    private var fbInterstitial: com.facebook.ads.InterstitialAd? = null

    // Questions (tu baad me add karega)
    private val questions = listOf(

        // 🔰 Level 1–3 (Very Easy)
        GameData("5 + 3 = ?", listOf("6","7","8","9"),2),
        GameData("10 - 4 = ?", listOf("5","6","7","8"),1),
        GameData("2 × 4 = ?", listOf("6","8","10","12"),1),

        // 🔰 Level 4–6 (Easy)
        GameData("12 + 7 = ?", listOf("17","18","19","20"),2),
        GameData("15 - 6 = ?", listOf("7","8","9","10"),2),
        GameData("6 × 3 = ?", listOf("15","16","18","20"),2),

        // 🔰 Level 7–9
        GameData("25 + 14 = ?", listOf("37","38","39","40"),2),
        GameData("30 - 17 = ?", listOf("11","12","13","14"),2),
        GameData("8 × 7 = ?", listOf("54","56","58","60"),1),

        // 🟡 Level 10–12 (Medium)
        GameData("45 + 28 = ?", listOf("71","72","73","74"),2),
        GameData("64 - 29 = ?", listOf("33","34","35","36"),2),
        GameData("12 × 6 = ?", listOf("70","72","74","76"),1),

        // 🟡 Level 13–15
        GameData("90 ÷ 9 = ?", listOf("9","10","11","12"),1),
        GameData("7² = ?", listOf("47","48","49","50"),2),
        GameData("15 × 8 = ?", listOf("110","115","120","125"),2),

        // 🟠 Level 16–18 (Medium-Hard)
        GameData("144 ÷ 12 = ?", listOf("10","11","12","13"),2),
        GameData("18 × 7 = ?", listOf("124","126","128","130"),1),
        GameData("96 - 37 = ?", listOf("57","58","59","60"),2),

        // 🟠 Level 19–21
        GameData("25² = ?", listOf("600","625","650","675"),1),
        GameData("11 × 13 = ?", listOf("132","143","154","165"),1),
        GameData("150 ÷ 6 = ?", listOf("20","22","24","25"),3),

        // 🔴 Level 22–24 (Hard)
        GameData("48 × 6 = ?", listOf("264","276","288","300"),2),
        GameData("360 ÷ 15 = ?", listOf("22","23","24","25"),2),
        GameData("17² = ?", listOf("269","289","299","309"),1),

        // 🔴 Level 25–27
        GameData("125 × 4 = ?", listOf("400","450","500","550"),2),
        GameData("81 ÷ 0.9 = ?", listOf("80","85","90","95"),2),
        GameData("19 × 14 = ?", listOf("256","266","276","286"),2),

        // 🔴 Level 28–30
        GameData("√144 = ?", listOf("10","11","12","13"),2),
        GameData("225 ÷ 15 = ?", listOf("13","14","15","16"),2),
        GameData("23 × 7 = ?", listOf("141","151","161","171"),2),

        // ⚫ Level 31–40 (Very Hard)
        GameData("999 - 456 = ?", listOf("533","543","553","563"),1),
        GameData("48² = ?", listOf("2204","2304","2404","2504"),1),
        GameData("7³ = ?", listOf("321","343","365","389"),1),

        GameData("121 × 11 = ?", listOf("1311","1321","1331","1341"),2),
        GameData("1001 - 578 = ?", listOf("413","423","433","443"),1),
        GameData("64 × 0.75 = ?", listOf("46","47","48","49"),2),

        GameData("15³ = ?", listOf("3375","3475","3575","3675"),0),
        GameData("840 ÷ 7 = ?", listOf("110","115","120","125"),2),
        GameData("√529 = ?", listOf("21","22","23","24"),2),

        // ⚫⚫ Level 41–100 (Hard se bhi HARD – Speed Killer)
        // Pattern intentionally brutal

        GameData("99 × 99 = ?", listOf("9701","9801","9901","10001"),1),
        GameData("512 ÷ 0.8 = ?", listOf("620","640","660","680"),1),
        GameData("17 × 19 = ?", listOf("313","323","333","343"),1),

        GameData("√1024 = ?", listOf("30","31","32","33"),2),
        GameData("10000 ÷ 125 = ?", listOf("70","75","80","85"),2),
        GameData("45² = ?", listOf("1925","2025","2125","2225"),1)

        // 👉 Isi pattern me 100 tak continue kar
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_speed_math)

        level = intent.getIntExtra("LEVEL", 1)

        MobileAds.initialize(this)
        AudienceNetworkAds.initialize(this)

        loadAdmob()
        loadFacebook()

        loadLevel()
    }

    // ---------------- ADS ----------------

    private fun loadAdmob() {
        InterstitialAd.load(
            this,
            "ADMOB_INTERSTITIAL_ID",
            AdRequest.Builder().build(),
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: InterstitialAd) { admobInterstitial = ad }
                override fun onAdFailedToLoad(error: LoadAdError) { admobInterstitial = null }
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

    // ---------------- GAME ----------------

    private fun loadLevel() {

        if (level > questions.size) {
            Toast.makeText(this, "Speed Math Completed ⚡", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        val q = questions[level - 1]

        val tvQuestion = findViewById<TextView>(R.id.tvQuestion)
        val tvTimer = findViewById<TextView>(R.id.tvTimer)

        tvQuestion.text = "Level $level\n${q.question}"

        val buttons = listOf(
            findViewById<Button>(R.id.btn1),
            findViewById<Button>(R.id.btn2),
            findViewById<Button>(R.id.btn3),
            findViewById<Button>(R.id.btn4)
        )

        buttons.forEachIndexed { i, b ->
            b.text = q.options[i]
            b.setOnClickListener {
                timer?.cancel()
                if (i == q.correctIndex) {
                    unlock()
                    startActivity(
                        Intent(this, SpeedMathPlayActivity::class.java)
                            .putExtra("LEVEL", level + 1)
                    )
                    finish()
                } else {
                    Toast.makeText(this, "Wrong ❌", Toast.LENGTH_SHORT).show()
                    showAdThen { recreate() }
                }
            }
        }

        // ⏱ TIMER (harder with levels)
        val timeMs = when {
            level < 10 -> 6000L
            level < 20 -> 5000L
            else -> 4000L
        }

        timer = object : CountDownTimer(timeMs, 1000) {
            override fun onTick(ms: Long) {
                tvTimer.text = "Time: ${ms / 1000}s"
            }
            override fun onFinish() {
                Toast.makeText(this@SpeedMathPlayActivity, "Time Up ⏱", Toast.LENGTH_SHORT).show()
                showAdThen { recreate() }
            }
        }.start()

        findViewById<Button>(R.id.btnRestart).setOnClickListener {
            timer?.cancel()
            showAdThen { recreate() }
        }
    }

    private fun unlock() {
        val p = getSharedPreferences(PREF, MODE_PRIVATE)
        val u = p.getInt(KEY, 1)
        if (level >= u) p.edit().putInt(KEY, level + 1).apply()
    }

    override fun onDestroy() {
        timer?.cancel()
        super.onDestroy()
    }
}
