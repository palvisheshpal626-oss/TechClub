package com.techclub.vishesh.kumar.vkit.techclub.activity

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.techclub.vishesh.kumar.vkit.techclub.R

class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        val tvAbout = findViewById<TextView>(R.id.tvAbout)

        tvAbout.text = """
play & learn: code & brain games – Learn Coding with Games

TechClub is an educational learning app designed to help students learn
programming in a fun and interactive way.

This app provides quiz-based coding games for languages like C, Java,
and Python. Each game contains multiple levels to improve programming
logic step by step.

Features:
• Learn coding through games
• C, Java, and Python quiz levels
• Beginner-friendly interface
• Level unlock & progress system

Developed by:
Vishesh Pal

Version: 1.0

Note:
This app is developed for educational purposes only.
""".trimIndent()
    }
}
