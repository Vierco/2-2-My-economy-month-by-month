package com.sergio.alvarez.mieconomia

//import com.sergio.alvarez.mieconomia.Months.Companion._months
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.sergio.alvarez.mieconomia.databinding.ActivityHomeBinding
import kotlinx.android.synthetic.main.activity_home.*
import java.util.*

class Home : AppCompatActivity() {


    private lateinit var vb: ActivityHomeBinding
    private val calendar: Calendar = Calendar.getInstance(TimeZone.getDefault());


    private fun getDate() {

        val month = calendar.get(Calendar.MONTH) + 1
        vb.tvMounth.text = (Months.months.get(month).toString())

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(vb.root)

        getDate()

        fab.setOnClickListener { view ->
            // TODO ACTION
        }
    }
}
