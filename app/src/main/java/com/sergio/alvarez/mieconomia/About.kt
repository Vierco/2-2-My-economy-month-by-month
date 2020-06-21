package com.sergio.alvarez.mieconomia

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sergio.alvarez.mieconomia.databinding.ActivityAboutBinding

class About : AppCompatActivity() {

    private lateinit var vb: ActivityAboutBinding

    private fun website(web: String) {
        val i = Intent(Intent.ACTION_VIEW, Uri.parse(web))
        startActivity(i)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(vb.root)

        vb.btAccept.setOnClickListener {
            onBackPressed()
        }

        vb.Github.setOnClickListener {
            website("https://github.com/Vierco/2-2-My-economy-month-by-month")
        }

        vb.vierco.setOnClickListener {
            website("https://www.sergioalvarez.dev")
        }

        vb.numberPicker.setOnClickListener {
            website("https://github.com/sephiroth74/NumberSlidingPicker")
        }

        vb.scroll.setOnClickListener {
            website("https://github.com/Q42/AndroidScrollingImageView")
        }

        vb.customToggle.setOnClickListener {
            website("https://github.com/rishabhk07/custom-toggle-button")
        }

        vb.license.setOnClickListener {
            website("https://creativecommons.org/licenses/by-nc/4.0/legalcode.es")
        }
    }
}