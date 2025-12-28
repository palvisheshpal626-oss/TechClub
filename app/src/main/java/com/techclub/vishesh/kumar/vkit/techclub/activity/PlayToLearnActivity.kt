package com.techclub.vishesh.kumar.vkit.techclub.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.techclub.vishesh.kumar.vkit.techclub.R
import com.techclub.vishesh.kumar.vkit.techclub.utils.GamesListActivity

class PlayToLearnActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_to_learn)

        val btnCoding = findViewById<Button>(R.id.btnCodingGames)
        val btnBrain = findViewById<Button>(R.id.btnBrainGames)
        val btnLogic = findViewById<Button>(R.id.btnLogicGames)

        // Coding Games
        btnCoding.setOnClickListener {
            val intent = Intent(this, GamesListActivity::class.java)
            intent.putExtra("GAME_TYPE", "CODE")
            startActivity(intent)
        }

        // Brain Games
        btnBrain.setOnClickListener {
            startActivity(
                Intent(this, BrainBoosterActivity::class.java)
            )
        }

        // Logic Games
        btnLogic.setOnClickListener {
            val intent = Intent(this, GamesListActivity::class.java)
            intent.putExtra("GAME_TYPE", "LOGIC")
            startActivity(intent)
        }
    }
}
