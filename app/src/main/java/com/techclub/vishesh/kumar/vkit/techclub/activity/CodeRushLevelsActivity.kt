package com.techclub.vishesh.kumar.vkit.techclub.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.techclub.vishesh.kumar.vkit.techclub.R

class CodeRushLevelsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_levels)

        val box = findViewById<LinearLayout>(R.id.levelContainer)
        val prefs = getSharedPreferences("CODE_PREF", MODE_PRIVATE)
        val unlocked = prefs.getInt("CODE_UNLOCKED", 1)

        for (i in 1..100) {
            val b = Button(this)
            b.text = if (i <= unlocked) "Level $i" else "🔒 Level $i"
            b.setOnClickListener {
                if (i <= unlocked) {
                    startActivity(
                        Intent(this, CodeRushPlayActivity::class.java)
                            .putExtra("LEVEL", i)
                    )
                } else {
                    Toast.makeText(this, "Locked 🔒", Toast.LENGTH_SHORT).show()
                }
            }
            box.addView(b)
        }
    }
}
