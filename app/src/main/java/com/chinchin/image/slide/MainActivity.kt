package com.chinchin.image.slide

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.chinchin.image.compare.ImageCompareSlider
import com.chinchin.image.compare.Util

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val imageCompareSlider = findViewById<ImageCompareSlider>(R.id.imv_compare)

        ContextCompat.getDrawable(this, R.drawable.image_test_1)?.let { res->
            Util.compressBitmapFromDrawable(this, res, 10)
                ?.let { bitmap -> imageCompareSlider.setUpSlideCompare(bitmap) }
        }


        findViewById<Button>(R.id.tv_zoom_in).setOnClickListener {
            imageCompareSlider.zoomForeImage(1.5f)
        }



        findViewById<Button>(R.id.tv_original).setOnClickListener {
            imageCompareSlider.originalForeImage()
        }

        Handler(mainLooper).postDelayed({
          runOnUiThread {
              Toast.makeText(this, "Show", Toast.LENGTH_SHORT).show()
              imageCompareSlider.setForegroundImage("https://pub-3626123a908346a7a8be8d9295f44e26.r2.dev/generations/224e86ca-203f-482d-b7f5-03408bf6d4f6_out.png")
          }
        }, 5000)

        findViewById<Button>(R.id.tv_after).setOnClickListener {
            imageCompareSlider.setHiddenBackGround()
        }

        findViewById<Button>(R.id.tv_before).setOnClickListener {
            imageCompareSlider.setHiddenForeground()
        }

        imageCompareSlider.setPositionSlideImage(0.8f)
    }
}