package com.techclub.vishesh.kumar.vkit.techclub.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.techclub.vishesh.kumar.vkit.techclub.R

class BrainBoosterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_brain_booster)

        // Buttons
        val btnReaction = findViewById<Button>(R.id.btnReaction)
        val btnMemory = findViewById<Button>(R.id.btnMemory)
        val btnPattern = findViewById<Button>(R.id.btnPattern)
        val btnColor = findViewById<Button>(R.id.btnColor)

        // Reaction Speed
        btnReaction.setOnClickListener {
            startActivity(
                Intent(this, ReactionSpeedPlayActivity::class.java)
                    .putExtra("LEVEL", 1)
            )
        }

        // Memory Matrix
        btnMemory.setOnClickListener {
            startActivity(
                Intent(this, MemoryMatrixPlayActivity::class.java)
                    .putExtra("LEVEL", 1)
            )
        }

        // Pattern Lock
        btnPattern.setOnClickListener {
            startActivity(
                Intent(this, PatternLockPlayActivity::class.java)
                    .putExtra("LEVEL", 1)
            )
        }

        // Color Match
        btnColor.setOnClickListener {
            startActivity(
                Intent(this, ColorMatchPlayActivity::class.java)
                    .putExtra("LEVEL", 1)
            )
        }
    }
}
