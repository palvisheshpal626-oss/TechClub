package com.techclub.vishesh.kumar.vkit.techclub.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.techclub.vishesh.kumar.vkit.techclub.R

class CGameLevelsActivity : AppCompatActivity() {

    private val PREF_NAME = "GAME_PREFS"
    private val KEY_UNLOCKED_LEVEL = "UNLOCKED_LEVEL"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_c_game_levels)

        val container = findViewById<LinearLayout>(R.id.levelsContainer)

        val prefs = getSharedPreferences(PREF_NAME, MODE_PRIVATE)
        val unlockedLevel = prefs.getInt(KEY_UNLOCKED_LEVEL, 1)

        for (level in 1..100) {
            val btn = Button(this)

            if (level <= unlockedLevel) {
                btn.text = "Level $level"
                btn.setOnClickListener {
                    startActivity(
                        Intent(this, CGamePlayActivity::class.java)
                            .putExtra("LEVEL", level)
                    )
                }
            } else {
                btn.text = "🔒 Level $level"
                btn.isEnabled = false
            }

            container.addView(btn)
        }
    }
}
