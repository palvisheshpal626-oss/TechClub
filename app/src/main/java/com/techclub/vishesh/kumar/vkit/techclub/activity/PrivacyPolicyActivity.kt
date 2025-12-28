package com.techclub.vishesh.kumar.vkit.techclub.activity

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.techclub.vishesh.kumar.vkit.techclub.R

class PrivacyPolicyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privacy_policy)

        val tvPrivacy = findViewById<TextView>(R.id.tvPrivacy)

        tvPrivacy.text = """
Privacy Policy – Play & Learn: Code & Brain Games

Play & Learn: Code & Brain Games ("we", "our", "us") respects the privacy of its users and is committed to protecting it.
This Privacy Policy explains how information is handled when you use our mobile application.

────────────────────────
Information Collection
────────────────────────
Play & Learn: Code & Brain Games does NOT collect, store, or require any personally identifiable information such as name, phone number, address, or contact details.

Users can play all games without creating an account or providing personal data.

────────────────────────
Advertising
────────────────────────
Our app displays ads to support free gameplay and app maintenance.
We use third-party advertising services, including:

• Google AdMob  
• Meta (Facebook) Audience Network  

These ad networks may collect limited, non-personal information such as device identifiers, cookies, or advertising IDs to show relevant ads, in accordance with their own privacy policies.

────────────────────────
Data Usage
────────────────────────
• We do not collect or store personal user data.
• We do not sell, trade, or share user information.
• All data collected by ads is managed by third-party ad providers.
• We do not control how third-party networks use their collected data.

────────────────────────
Children’s Privacy
────────────────────────
This app is intended for users aged 13 years and above.
We do not knowingly collect personal information from children under the age of 13.

If you believe a child has provided personal data, please contact us and we will take appropriate action.

────────────────────────
Changes to This Policy
────────────────────────
This Privacy Policy may be updated from time to time.
Any changes will be reflected within the app.

────────────────────────
Contact Us
────────────────────────
If you have any questions or concerns about this Privacy Policy, please contact us:

Email: Tech.Club.V.A@gmail.com  
Developer: Vishesh Pal
""".trimIndent()
    }
}
