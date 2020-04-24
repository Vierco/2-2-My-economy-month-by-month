package com.sergio.alvarez.mieconomia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sergio.alvarez.mieconomia.databinding.ActivityLoginAdviceBinding
import kotlinx.android.synthetic.main.activity_login_advice.*

class LoginAdvice : AppCompatActivity() {

    private lateinit var vb: ActivityLoginAdviceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb = ActivityLoginAdviceBinding.inflate(layoutInflater)
        setContentView(vb.root)

        bt_ok.setOnClickListener {
            startActivity<SetId>()
            onPause()
        }


    }
}
