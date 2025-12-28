package com.techclub.vishesh.kumar.vkit.techclub.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.techclub.vishesh.kumar.vkit.techclub.R

class DailyChallengeResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daily_result)

        val tvTitle = findViewById<TextView>(R.id.tvTitle)
        val tvStreak = findViewById<TextView>(R.id.tvStreak)
        val tvMessage = findViewById<TextView>(R.id.tvMessage)
        val btnHome = findViewById<Button>(R.id.btnHome)

        val prefs = getSharedPreferences("DAILY_PREF", MODE_PRIVATE)
        val streak = prefs.getInt("STREAK", 0)
        val wonToday = prefs.getBoolean("WON_TODAY", false)

        if (wonToday) {
            tvTitle.text = "🎉 Challenge Completed!"
            tvMessage.text = "Great job! Come back tomorrow to continue your streak."
        } else {
            tvTitle.text = "❌ Challenge Failed"
            tvMessage.text = "Try again tomorrow. Streak resets if you miss!"
        }

        tvStreak.text = "🔥 Current Streak: $streak Days"

        btnHome.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
    }
}
