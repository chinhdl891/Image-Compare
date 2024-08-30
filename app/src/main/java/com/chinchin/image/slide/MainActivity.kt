package com.chinchin.image.slide

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chinchin.image.compare.ImageCompareSlider

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val imageCompareSlider = findViewById<ImageCompareSlider>(R.id.imv_compare)
        val bitmapBack = BitmapFactory.decodeResource(resources, R.drawable.anh1)
        val bitmapForeground = BitmapFactory.decodeResource(resources, R.drawable.anh2)
        imageCompareSlider.setUpSlideCompare(bitmapBack, bitmapForeground)
    }
}