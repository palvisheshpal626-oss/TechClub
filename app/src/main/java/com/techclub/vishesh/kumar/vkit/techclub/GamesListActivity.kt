package com.techclub.vishesh.kumar.vkit.techclub.utils

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.techclub.vishesh.kumar.vkit.techclub.R
import com.techclub.vishesh.kumar.vkit.techclub.activity.*
import com.techclub.vishesh.kumar.vkit.techclub.adapter.SimpleGameAdapter
import com.techclub.vishesh.kumar.vkit.techclub.model.GameItem

class GamesListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_games_list)

        val recyclerView = findViewById<RecyclerView>(R.id.rvGames)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val gameType = intent.getStringExtra("GAME_TYPE") ?: "ALL"

        val games = mutableListOf<GameItem>()

        when (gameType) {
            "CODE" -> {
                games.add(GameItem("C Language Game", CGamePlayActivity::class.java))
                games.add(GameItem("Code Play", CodePlayLevelsActivity::class.java))
                games.add(GameItem("Code Rush", CodeRushLevelsActivity::class.java))
                games.add(GameItem("Java Game", JavaGamePlayActivity::class.java))
                games.add(GameItem("Python Game", PythonGamePlayActivity::class.java))
            }

            "BRAIN" -> {
                games.add(GameItem("Brain Booster", BrainBoosterActivity::class.java))
                games.add(GameItem("Smart Brain", SmartBrainPlayActivity::class.java))
                games.add(GameItem("Memory Matrix", MemoryMatrixLevelsActivity::class.java))
            }

            "MATH" -> {
                games.add(GameItem("Mind Logic", MindLogicLevelsActivity::class.java))
                games.add(GameItem("Color Match", ColorMatchLevelsActivity::class.java))
                games.add(GameItem("Pattern Lock", PatternLockLevelsActivity::class.java))
            }
        }

        recyclerView.adapter = SimpleGameAdapter(games) {
            startActivity(Intent(this, it.activity))
        }
    }
}
