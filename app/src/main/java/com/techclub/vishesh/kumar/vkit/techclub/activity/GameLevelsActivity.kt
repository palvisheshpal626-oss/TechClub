package com.techclub.vishesh.kumar.vkit.techclub.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.techclub.vishesh.kumar.vkit.techclub.R

class GameLevelsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_levels)

        val language = intent.getStringExtra("LANGUAGE") ?: return
        val container = findViewById<LinearLayout>(R.id.levelContainer)

        val prefsName =
            if (language == "C") "GAME_PREFS" else "PY_GAME_PREFS"
        val key =
            if (language == "C") "UNLOCKED_LEVEL" else "PY_UNLOCKED_LEVEL"

        val unlocked =
            getSharedPreferences(prefsName, MODE_PRIVATE)
                .getInt(key, 1)

        for (i in 1..15) {
            val btn = Button(this)
            btn.text = if (i <= unlocked) "Level $i" else "🔒 Level $i"

            btn.setOnClickListener {
                if (i <= unlocked) {
                    val intent =
                        if (language == "C")
                            Intent(this, CGamePlayActivity::class.java)
                        else
                            Intent(this, PythonGamePlayActivity::class.java)

                    intent.putExtra("LEVEL", i)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Level Locked 🔒", Toast.LENGTH_SHORT).show()
                }
            }
            container.addView(btn)
        }
    }
}
