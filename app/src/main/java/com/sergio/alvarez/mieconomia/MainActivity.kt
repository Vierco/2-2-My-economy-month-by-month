package com.sergio.alvarez.mieconomia

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.sergio.alvarez.mieconomia.GlobalVar.Companion.prefs
import com.sergio.alvarez.mieconomia.PreferenceHelper.firstRun
import com.sergio.alvarez.mieconomia.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var vb: ActivityMainBinding


    private fun cambioAhome() {
        startActivity<Home>()
        finish()
    }

    private fun cambioALogin() {
        startActivity<LoginAdvice>()
        finish()
    }


    private fun checkVisibilities() {
        if (vb.item1.visibility == View.VISIBLE) {
            vb.btPrev.visibility = View.GONE
        } else {
            vb.btPrev.visibility = View.VISIBLE
        }

        if (vb.item4.visibility == View.VISIBLE) {
            vb.btNext.text = (resources.getString(R.string.st_start))
        } else {
            vb.btNext.text = (resources.getString(R.string.st_next))
        }
    }

    private fun openingChecks() {

        if (!prefs.firstRun){
            cambioAhome()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb = ActivityMainBinding.inflate(layoutInflater)
        setContentView(vb.root)


        openingChecks()

        vb.btNext.setOnClickListener {


            if (vb.btNext.text == resources.getString(R.string.st_start)) {

                prefs.firstRun = false

                cambioALogin()

            } else {

                vb.viewFlipper.setInAnimation(
                    App.applicationContext(),
                    R.anim.view_flipper_slide_in_right
                )
                vb.viewFlipper.setOutAnimation(
                    App.applicationContext(),
                    R.anim.view_flipper_slide_out_left
                )
                vb.viewFlipper.showNext()
                checkVisibilities()

            }

        }

        vb.btPrev.setOnClickListener {
            vb.viewFlipper.setInAnimation(App.applicationContext(), android.R.anim.slide_in_left)
            vb.viewFlipper.setOutAnimation(App.applicationContext(), android.R.anim.slide_out_right)
            vb.viewFlipper.showPrevious()
            checkVisibilities()

        }

    }
}
