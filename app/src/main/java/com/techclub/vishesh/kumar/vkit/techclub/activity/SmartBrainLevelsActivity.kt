package com.techclub.vishesh.kumar.vkit.techclub.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.techclub.vishesh.kumar.vkit.techclub.R

class SmartBrainLevelsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_levels)

        val container = findViewById<LinearLayout>(R.id.levelContainer)
        val prefs = getSharedPreferences("SMART_PREFS", MODE_PRIVATE)
        val unlocked = prefs.getInt("SMART_UNLOCKED", 1)

        for (i in 1..100) {
            val btn = Button(this)
            btn.text = if (i <= unlocked) "Level $i" else "🔒 Level $i"

            btn.setOnClickListener {
                if (i <= unlocked) {
                    startActivity(
                        Intent(this, SmartBrainPlayActivity::class.java)
                            .putExtra("LEVEL", i)
                    )
                } else {
                    Toast.makeText(this, "Level Locked 🔒", Toast.LENGTH_SHORT).show()
                }
            }
            container.addView(btn)
        }
    }
}
