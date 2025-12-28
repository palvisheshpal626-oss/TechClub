package com.techclub.vishesh.kumar.vkit.techclub.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.techclub.vishesh.kumar.vkit.techclub.R
import com.techclub.vishesh.kumar.vkit.techclub.model.GameData

class CodeRushPlayActivity : AppCompatActivity() {

    private var level = 1
    private val PREF = "CODE_PREF"
    private val KEY = "CODE_UNLOCKED"

    // 🔥 SAMPLE QUESTIONS (tu 100 tak extend karega)
    private val questions = listOf(

        /* ---------- 1–10 : BASIC ---------- */
        GameData("C Output?\nint a=5; printf(\"%d\", a);",
            listOf("5","6","0","Error"), 0),

        GameData("C Output?\nint a=5; printf(\"%d\", a+1);",
            listOf("5","6","7","Error"), 1),

        GameData("C Output?\nint a=5; a++; printf(\"%d\", a);",
            listOf("4","5","6","7"), 2),

        GameData("C Output?\nprintf(\"Hello\");",
            listOf("Hello","hello","Error","Nothing"), 0),

        GameData("C Output?\nint a=2,b=3; printf(\"%d\", a+b);",
            listOf("4","5","6","23"), 1),

        GameData("C Output?\nint a=10; printf(\"%d\", a-3);",
            listOf("7","8","9","Error"), 0),

        GameData("C Output?\nprintf(\"%d\", 5*2);",
            listOf("7","10","12","Error"), 1),

        GameData("C Output?\nprintf(\"%d\", 10/2);",
            listOf("2","4","5","10"), 2),

        GameData("C Output?\nint a=3; printf(\"%d\", a*a);",
            listOf("6","9","12","Error"), 1),

        GameData("C Output?\nprintf(\"%d\", 7%3);",
            listOf("1","2","3","0"), 1),

        /* ---------- 11–20 : ++ / -- ---------- */
        GameData("C Output?\nint a=5; printf(\"%d\", a++);",
            listOf("4","5","6","Error"), 1),

        GameData("C Output?\nint a=5; printf(\"%d\", ++a);",
            listOf("5","6","7","Error"), 1),

        GameData("C Output?\nint a=5; int b=a++; printf(\"%d\", b);",
            listOf("4","5","6","Error"), 1),

        GameData("C Output?\nint a=5; int b=++a; printf(\"%d\", b);",
            listOf("5","6","7","Error"), 1),

        GameData("C Output?\nint a=5; printf(\"%d\", a++ + a);",
            listOf("10","11","12","Error"), 1),

        GameData("C Output?\nint a=5; printf(\"%d\", ++a + a);",
            listOf("11","12","13","Error"), 1),

        GameData("C Output?\nint a=3; printf(\"%d\", a--);",
            listOf("2","3","4","Error"), 1),

        GameData("C Output?\nint a=3; printf(\"%d\", --a);",
            listOf("2","3","4","Error"), 0),

        GameData("C Output?\nint a=3; printf(\"%d\", a-- + a);",
            listOf("4","5","6","Error"), 0),

        GameData("C Output?\nint a=3; printf(\"%d\", --a + a);",
            listOf("2","3","4","Error"), 1),

        /* ---------- 21–30 : CONDITIONS ---------- */
        GameData("C Output?\nint a=5; if(a>3) printf(\"Yes\");",
            listOf("Yes","No","Error","Nothing"), 0),

        GameData("C Output?\nint a=5; if(a<3) printf(\"Yes\");",
            listOf("Yes","No","Error","Nothing"), 3),

        GameData("C Output?\nint a=0; if(a) printf(\"Yes\");",
            listOf("Yes","No","Error","Nothing"), 3),

        GameData("C Output?\nint a=10; if(a=5) printf(\"Yes\");",
            listOf("Yes","No","Error","Nothing"), 0),

        GameData("C Output?\nint a=0; if(a==0) printf(\"Yes\");",
            listOf("Yes","No","Error","Nothing"), 0),

        GameData("C Output?\nint a=5; if(a!=5) printf(\"Yes\");",
            listOf("Yes","No","Error","Nothing"), 3),

        GameData("C Output?\nint a=5; if(a>=5) printf(\"Yes\");",
            listOf("Yes","No","Error","Nothing"), 0),

        GameData("C Output?\nint a=5; if(a<=4) printf(\"Yes\");",
            listOf("Yes","No","Error","Nothing"), 3),

        GameData("C Output?\nint a=5; if(a&&0) printf(\"Yes\");",
            listOf("Yes","No","Error","Nothing"), 3),

        GameData("C Output?\nint a=5; if(a||0) printf(\"Yes\");",
            listOf("Yes","No","Error","Nothing"), 0),

        /* ---------- 31–40 : LOOPS ---------- */
        GameData("Output?\nfor(int i=1;i<=3;i++) printf(\"%d\",i);",
            listOf("123","0123","321","Error"), 0),

        GameData("Output?\nfor(int i=3;i>=1;i--) printf(\"%d\",i);",
            listOf("123","321","213","Error"), 1),

        GameData("Output?\nint i=1; while(i<=3){printf(\"%d\",i);i++;}",
            listOf("123","0123","321","Error"), 0),

        GameData("Output?\nint i=1; do{printf(\"%d\",i);i++;}while(i<=3);",
            listOf("123","0123","321","Error"), 0),

        GameData("Output?\nfor(int i=0;i<3;i++) printf(\"%d\",i);",
            listOf("012","123","01","Error"), 0),

        GameData("Output?\nfor(int i=1;i<5;i+=2) printf(\"%d\",i);",
            listOf("135","13","24","Error"), 1),

        GameData("Output?\nint i=1; while(i<5){printf(\"%d\",i); i+=2;}",
            listOf("135","13","24","Error"), 0),

        GameData("Output?\nfor(int i=1;i<=3;i++) for(int j=1;j<=1;j++) printf(\"%d\",i);",
            listOf("123","111","121","Error"), 0),

        GameData("Output?\nfor(int i=1;i<=3;i++) printf(\"*\");",
            listOf("***","****","**","Error"), 0),

        GameData("Output?\nfor(int i=1;i<=3;i++); printf(\"Hi\");",
            listOf("Hi","HiHiHi","Error","Nothing"), 0),

        /* ---------- 41–60 : ARRAYS ---------- */
        GameData("C Output?\nint a[]={1,2,3}; printf(\"%d\", a[0]);",
            listOf("1","2","3","Error"), 0),

        GameData("C Output?\nint a[]={1,2,3}; printf(\"%d\", a[2]);",
            listOf("1","2","3","Error"), 2),

        GameData("C Output?\nint a[3]={1}; printf(\"%d\", a[1]);",
            listOf("0","1","Garbage","Error"), 0),

        GameData("C Output?\nint a[]={1,2,3}; printf(\"%d\", a[1]+a[2]);",
            listOf("3","4","5","Error"), 2),

        GameData("C Output?\nint a[5]={1,2}; printf(\"%d\", a[3]);",
            listOf("0","1","Garbage","Error"), 0),

        GameData("C Output?\nint a[]={1,2,3}; printf(\"%d\", sizeof(a)/sizeof(int));",
            listOf("2","3","4","Error"), 1),

        GameData("C Output?\nint a[5]; printf(\"%d\", sizeof(a));",
            listOf("5","10","20","Error"), 2),

        GameData("C Output?\nint a[]={10,20,30}; printf(\"%d\", a[1]/10);",
            listOf("1","2","3","Error"), 1),

        GameData("C Output?\nint a[]={1,2,3}; a[1]=5; printf(\"%d\", a[1]);",
            listOf("2","3","5","Error"), 2),

        GameData("C Output?\nint a[]={1,2,3}; printf(\"%d\", a[3]);",
            listOf("0","Garbage","Error","Compile Error"), 1),

        /* ---------- 61–80 : FUNCTIONS & POINTERS ---------- */
        GameData("C Output?\nint a=5; printf(\"%d\", &a);",
            listOf("5","Address","Error","0"), 1),

        GameData("C Output?\nint a=5; int *p=&a; printf(\"%d\", *p);",
            listOf("Address","5","Error","0"), 1),

        GameData("C Output?\nint a=5; int *p=&a; printf(\"%d\", p);",
            listOf("5","Address","Error","0"), 1),

        GameData("C Output?\nint a=5; int *p=&a; *p=10; printf(\"%d\", a);",
            listOf("5","10","Error","0"), 1),

        GameData("C Output?\nvoid f(){printf(\"Hi\");} int main(){f();}",
            listOf("Hi","Error","Nothing","0"), 0),

        GameData("C Output?\nint f(){return 5;} printf(\"%d\", f());",
            listOf("5","Error","0","Nothing"), 0),

        GameData("C Output?\nint a=5; int *p=&a; printf(\"%d\", *(&a));",
            listOf("5","Address","Error","0"), 0),

        GameData("C Output?\nint a=5; int **p; printf(\"%d\", a);",
            listOf("5","Error","0","Garbage"), 0),

        GameData("C Output?\nint a=5; int *p; printf(\"%d\", *p);",
            listOf("5","0","Garbage","Error"), 2),

        GameData("C Output?\nprintf(\"%d\", NULL);",
            listOf("0","Error","NULL","Garbage"), 0),

        /* ---------- 81–100 : EXTREME ---------- */
        GameData("C Output?\nint a=5; printf(\"%d\", a+++a);",
            listOf("10","11","Error","Undefined"), 2),

        GameData("C Output?\nint a=5; printf(\"%d\", ++a + a++);",
            listOf("11","12","13","Undefined"), 2),

        GameData("C Output?\nint a=1; printf(\"%d\", a<<2);",
            listOf("2","3","4","8"), 2),

        GameData("C Output?\nint a=8; printf(\"%d\", a>>2);",
            listOf("1","2","3","4"), 1),

        GameData("C Output?\nint a=5; printf(\"%d\", sizeof(a++));",
            listOf("2","4","5","Undefined"), 1),

        GameData("C Output?\nint a=0; printf(\"%d\", a && a++);",
            listOf("0","1","Undefined","Error"), 0),

        GameData("C Output?\nint a=1; printf(\"%d\", a || a++);",
            listOf("0","1","Undefined","Error"), 1),

        GameData("C Output?\nint a=5; printf(\"%d\", a+++ ++a);",
            listOf("Error","Undefined","11","12"), 1),

        GameData("C Output?\nint a=5; printf(\"%d\", a + ++a + a++);",
            listOf("15","16","17","Undefined"), 3),

        GameData("Final:\nint a=5; printf(\"%d %d\", a++, ++a);",
            listOf("5 7","5 6","6 7","Undefined"), 3)
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_c_game_play)

        level = intent.getIntExtra("LEVEL", 1)
        load()
    }

    private fun load() {
        val q = questions[level % questions.size]

        findViewById<TextView>(R.id.tvQuestion).text =
            "Code Rush – Level $level\n\n${q.question}"

        val btns = listOf(
            findViewById<Button>(R.id.btn1),
            findViewById<Button>(R.id.btn2),
            findViewById<Button>(R.id.btn3),
            findViewById<Button>(R.id.btn4)
        )

        btns.forEachIndexed { i, b ->
            b.text = q.options[i]
            b.setOnClickListener {
                if (i == q.correctIndex) {
                    unlock()
                    startActivity(
                        Intent(this, CodeRushPlayActivity::class.java)
                            .putExtra("LEVEL", level + 1)
                    )
                    finish()
                } else {
                    Toast.makeText(this, "Wrong ❌", Toast.LENGTH_SHORT).show()
                    recreate()
                }
            }
        }

        findViewById<Button>(R.id.btnRestart).setOnClickListener {
            recreate()
        }
    }

    private fun unlock() {
        val p = getSharedPreferences(PREF, MODE_PRIVATE)
        val u = p.getInt(KEY, 1)
        if (level >= u) p.edit().putInt(KEY, level + 1).apply()
    }
}
