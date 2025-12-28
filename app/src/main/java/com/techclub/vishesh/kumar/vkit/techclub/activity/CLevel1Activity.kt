package com.techclub.vishesh.kumar.vkit.techclub.activity

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.techclub.vishesh.kumar.vkit.techclub.R

class CLevel1Activity : AppCompatActivity() {

    private lateinit var tvQuestion: TextView
    private lateinit var btn1: Button
    private lateinit var btn2: Button
    private lateinit var btn3: Button
    private lateinit var btn4: Button
    private lateinit var btnRestart: Button

    private var currentIndex = 0
    private var score = 0

    private val questions = arrayOf(
        "C language was developed by?",
        "Which symbol is used to end a statement in C?",
        "Which keyword is used to declare integer in C?"
    )

    private val options = arrayOf(
        arrayOf("Dennis Ritchie", "James Gosling", "Bjarne Stroustrup", "Guido van Rossum"),
        arrayOf(":", ";", ".", ","),
        arrayOf("int", "integer", "num", "var")
    )

    private val answers = arrayOf(
        "Dennis Ritchie",
        ";",
        "int"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_c_level1)

        tvQuestion = findViewById(R.id.tvQuestion)
        btn1 = findViewById(R.id.btnOption1)
        btn2 = findViewById(R.id.btnOption2)
        btn3 = findViewById(R.id.btnOption3)
        btn4 = findViewById(R.id.btnOption4)
        btnRestart = findViewById(R.id.btnRestart)

        loadQuestion()

        val clickListener = { button: Button ->
            checkAnswer(button.text.toString())
        }

        btn1.setOnClickListener { clickListener(btn1) }
        btn2.setOnClickListener { clickListener(btn2) }
        btn3.setOnClickListener { clickListener(btn3) }
        btn4.setOnClickListener { clickListener(btn4) }

        // 🔁 RESTART GAME
        btnRestart.setOnClickListener {
            restartGame()
        }
    }

    private fun loadQuestion() {
        tvQuestion.text = questions[currentIndex]
        btn1.text = options[currentIndex][0]
        btn2.text = options[currentIndex][1]
        btn3.text = options[currentIndex][2]
        btn4.text = options[currentIndex][3]
    }

    private fun checkAnswer(selected: String) {
        if (selected == answers[currentIndex]) {
            score++
        }

        currentIndex++

        if (currentIndex < questions.size) {
            loadQuestion()
        } else {
            Toast.makeText(
                this,
                "Game Over! Score: $score/${questions.size}",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    // 🔁 Restart function
    private fun restartGame() {
        currentIndex = 0
        score = 0
        loadQuestion()
        Toast.makeText(this, "Game Restarted", Toast.LENGTH_SHORT).show()
    }
}
