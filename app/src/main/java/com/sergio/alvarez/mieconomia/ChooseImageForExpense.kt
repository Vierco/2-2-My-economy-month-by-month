package com.sergio.alvarez.mieconomia

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.sergio.alvarez.adapters.ImagesCardsAdapter
import com.sergio.alvarez.providers.GlobalVar.Companion.card_image_number
import com.sergio.alvarez.mieconomia.databinding.ActivityChooseImageForExpenseBinding
import com.sergio.alvarez.providers.drawingsProvider

class ChooseImageForExpense : AppCompatActivity() {

    private lateinit var vb: ActivityChooseImageForExpenseBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        vb = ActivityChooseImageForExpenseBinding.inflate(layoutInflater)
        setContentView(vb.root)
        this.setFinishOnTouchOutside(true)

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        vb.recyclerImages.layoutManager = layoutManager

        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(vb.recyclerImages)

        vb.recyclerImages.adapter = ImagesCardsAdapter(drawingsProvider.getImageCard()) {

            card_image_number = it
            finish()

        }


    }
}
