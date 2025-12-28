package com.techclub.vishesh.kumar.vkit.techclub.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.techclub.vishesh.kumar.vkit.techclub.R
import java.util.Calendar

class DailyChallengeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daily_dummy)
        // ↑ empty / splash type layout (safe)

        val prefs = getSharedPreferences("DAILY_PREF", MODE_PRIVATE)

        val today = Calendar.getInstance().get(Calendar.DAY_OF_YEAR)
        val lastDay = prefs.getInt("LAST_DAY", -1)
        val streak = prefs.getInt("STREAK", 0)
        val wonToday = prefs.getBoolean("WON_TODAY", false)

        // 🔁 New day → reset daily status
        if (today != lastDay) {
            prefs.edit()
                .putBoolean("WON_TODAY", false)
                .putInt("LAST_DAY", today)
                .apply()
        }

        // ✅ If already WON today → show result
        if (wonToday) {
            startActivity(
                Intent(this, DailyChallengeResultActivity::class.java)
            )
            finish()
            return
        }

        // 🎮 Pick today's challenge ONCE
        val gameOfDay = prefs.getInt("GAME_OF_DAY", -1)
        val selectedGame =
            if (gameOfDay == -1 || today != lastDay) {
                val g = (1..5).random()
                prefs.edit().putInt("GAME_OF_DAY", g).apply()
                g
            } else {
                gameOfDay
            }

        val intent = when (selectedGame) {
            1 -> Intent(this, MindLogicPlayActivity::class.java)
            2 -> Intent(this, ReactionSpeedPlayActivity::class.java)
            3 -> Intent(this, MemoryMatrixPlayActivity::class.java)
            4 -> Intent(this, ColorMatchPlayActivity::class.java)
            else -> Intent(this, CodeRushPlayActivity::class.java)
        }

        // 🏁 DAILY MODE FLAGS
        intent.putExtra("LEVEL", 1)
        intent.putExtra("DAILY_MODE", true)

        startActivity(intent)
        finish()
    }
}
