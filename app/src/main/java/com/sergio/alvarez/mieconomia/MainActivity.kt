package com.sergio.alvarez.mieconomia

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.sergio.alvarez.mieconomia.PreferenceHelper.customPreference
import com.sergio.alvarez.mieconomia.PreferenceHelper.firstRun
import com.sergio.alvarez.mieconomia.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var vb: ActivityMainBinding

    val CUSTOM_PREF_NAME = "App_data"

    private fun cambioAhome() {
        val intent = Intent(this, Home::class.java)
        startActivity(intent)
        finish()
    }

    private fun cambioALogin() {
        val intent = Intent(this, LoginAdvice::class.java)
        startActivity(intent)
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb = ActivityMainBinding.inflate(layoutInflater)
        setContentView(vb.root)

        val prefs = customPreference(this, CUSTOM_PREF_NAME)

//        Log.i("2134","primera? ${prefs.firstRun}");

        if (!prefs.firstRun){
            cambioAhome()
        }


        vb.btNext.setOnClickListener() {


            if (vb.btNext.text.equals(resources.getString(R.string.st_start))) {

                prefs.firstRun = false

                cambioALogin()

            } else {

                vb.viewFlipper.setInAnimation(
                    getApplicationContext(),
                    R.anim.view_flipper_slide_in_right
                );
                vb.viewFlipper.setOutAnimation(
                    getApplicationContext(),
                    R.anim.view_flipper_slide_out_left
                );
                vb.viewFlipper.showNext()
                checkVisibilities()

            }

        }

        vb.btPrev.setOnClickListener() {
            vb.viewFlipper.setInAnimation(getApplicationContext(), android.R.anim.slide_in_left);
            vb.viewFlipper.setOutAnimation(getApplicationContext(), android.R.anim.slide_out_right);
            vb.viewFlipper.showPrevious()
            checkVisibilities()

        }

    }
}
