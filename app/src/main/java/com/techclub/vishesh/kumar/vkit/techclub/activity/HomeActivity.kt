package com.techclub.vishesh.kumar.vkit.techclub.activity

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.material.navigation.NavigationView
import com.techclub.vishesh.kumar.vkit.techclub.R
import com.techclub.vishesh.kumar.vkit.techclub.adapter.SimpleGameAdapter
import com.techclub.vishesh.kumar.vkit.techclub.model.GameItem
import com.facebook.ads.AdSize
import com.facebook.ads.AudienceNetworkAds

class HomeActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var toolbar: Toolbar
    private lateinit var recyclerView: RecyclerView

    private lateinit var admobBanner: AdView
    private var admobInterstitial: InterstitialAd? = null

    private var fbBanner: com.facebook.ads.AdView? = null

    private var openCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // ================= UI =================
        toolbar = findViewById(R.id.toolbar)
        drawerLayout = findViewById(R.id.drawerLayout)
        navigationView = findViewById(R.id.navigationView)
        recyclerView = findViewById(R.id.recyclerGames)

        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.open, R.string.close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // ================= ADS =================
        MobileAds.initialize(this)
        AudienceNetworkAds.initialize(this)

        admobBanner = findViewById(R.id.adView)
        admobBanner.loadAd(AdRequest.Builder().build())

        loadAdmobInterstitial()
        loadFacebookBanner()

        // ================= GAMES LIST =================
        recyclerView.layoutManager = LinearLayoutManager(this)

        val games = listOf(
            GameItem("C Language Game", CGameLevelsActivity::class.java),
            GameItem("Code Play", CodePlayLevelsActivity::class.java),
            GameItem("Code Rush", CodeRushLevelsActivity::class.java),
            GameItem("Java Game", JavaGamePlayActivity::class.java),
            GameItem("Python Game", PythonGamePlayActivity::class.java),

            GameItem("Brain Booster", BrainBoosterActivity::class.java),
            GameItem("Smart Brain", SmartBrainPlayActivity::class.java),
            GameItem("Mind Logic", MindLogicLevelsActivity::class.java),

            GameItem("Memory Matrix", MemoryMatrixLevelsActivity::class.java),
            GameItem("Color Match", ColorMatchLevelsActivity::class.java),
            GameItem("Pattern Lock", PatternLockLevelsActivity::class.java),

            GameItem("Speed Math", SpeedMathPlayActivity::class.java),
            GameItem("Universal Game", UniversalGamePlayActivity::class.java),
            GameItem("Spot Bug", SpotBugPlayActivity::class.java),

            GameItem("Reaction Speed", ReactionSpeedPlayActivity::class.java),
            GameItem("Tap Think", TapThinkPlayActivity::class.java),
            GameItem("Daily Challenge", DailyChallengeActivity::class.java)
        )

        recyclerView.adapter = SimpleGameAdapter(games) {
            openGameWithAds(it.activity)
        }

        // ================= DRAWER MENU (ADDED ONLY THIS) =================
        navigationView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {

                R.id.nav_home -> {
                    drawerLayout.closeDrawers()
                }

                R.id.nav_about -> {
                    startActivity(Intent(this, AboutActivity::class.java))
                    drawerLayout.closeDrawers()
                }

                R.id.nav_privacy -> {
                    startActivity(Intent(this, PrivacyPolicyActivity::class.java))
                    drawerLayout.closeDrawers()
                }
            }
            true
        }
    }

    private fun loadAdmobInterstitial() {
        InterstitialAd.load(
            this,
            "ca-app-pub-6788180210698006/7627181258",
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

    private fun loadFacebookBanner() {
        val container = findViewById<LinearLayout>(R.id.fbAdContainer)
        fbBanner = com.facebook.ads.AdView(
            this,
            "FACEBOOK_BANNER_ID",
            AdSize.BANNER_HEIGHT_50
        )
        container.addView(fbBanner)
        fbBanner?.loadAd()
    }

    private fun openGameWithAds(activity: Class<*>) {
        openCount++
        val intent = Intent(this, activity)

        if (openCount % 2 == 0 && admobInterstitial != null) {
            admobInterstitial?.fullScreenContentCallback =
                object : FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {
                        admobInterstitial = null
                        loadAdmobInterstitial()
                        startActivity(intent)
                    }
                }
            admobInterstitial?.show(this)
        } else {
            startActivity(intent)
        }
    }
}
