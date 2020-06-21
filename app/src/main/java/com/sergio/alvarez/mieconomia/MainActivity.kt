package com.sergio.alvarez.mieconomia

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.sergio.alvarez.mieconomia.App.Companion.appContext
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
        startActivity<Auth>()
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

        if (!prefs.firstRun) {
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

                cambioALogin()

            } else {

                with(vb.viewFlipper) {
                    setInAnimation(appContext, R.anim.view_flipper_slide_in_right)
                    setOutAnimation(appContext, R.anim.view_flipper_slide_out_left)
                    showNext()
                }
                checkVisibilities()

            }

        }

        vb.btPrev.setOnClickListener {

            with(vb.viewFlipper) {
                setInAnimation(appContext, android.R.anim.slide_in_left)
                setOutAnimation(appContext, android.R.anim.slide_out_right)
                showPrevious()
            }
            checkVisibilities()

        }

    }
}
