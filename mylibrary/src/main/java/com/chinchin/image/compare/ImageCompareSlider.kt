package com.chinchin.image.compare

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.ImageView
import android.widget.SeekBar
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.chinchin.image.compare.databinding.ImageCompareSliderBinding


class ImageCompareSlider @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private var heightView = 0
    private var widthView = 0

    init {
        init(attrs)
    }

    private lateinit var binding: ImageCompareSliderBinding
    private fun init(attrs: AttributeSet?) {
        binding = ImageCompareSliderBinding.bind(View.inflate(context, R.layout.image_compare_slider, this))

        val styledAttrs = context.obtainStyledAttributes(attrs, R.styleable.ImageCompareSlider)
        try {
            val drawableBackground =
                styledAttrs.getResourceId(R.styleable.ImageCompareSlider_background_image, 0)
            if (drawableBackground != 0) {
                val drawable = AppCompatResources.getDrawable(context, drawableBackground)
                binding.backgroundImage.setImageDrawable(drawable)
            }
            val drawableForeground =
                styledAttrs.getResourceId(R.styleable.ImageCompareSlider_foreground_image, 0)
            if (drawableForeground != 0) {
                val drawable = AppCompatResources.getDrawable(context, drawableForeground)
                binding.foregroundImage.setImageDrawable(drawable)
            }
            val drawableSliderIcon = styledAttrs.getResourceId(R.styleable.ImageCompareSlider_slider_icon, 0)
            if (drawableSliderIcon != 0) {
                val drawable = AppCompatResources.getDrawable(context, drawableSliderIcon)
                binding.sliderImage.setImageDrawable(drawable)
            }
            val percentPosition = styledAttrs.getFloat(R.styleable.ImageCompareSlider_slider_image_position_percent, 0.5f)
            binding.guideline.setGuidelinePercent(percentPosition)
        } finally {
            styledAttrs.recycle()
        }

        binding.sbImageSeek.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                setImageWidth(i)
                binding.sliderImage.visibility = View.VISIBLE
                binding.sliderBar.visibility = View.VISIBLE
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })


        binding.backgroundImage.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                binding.foregroundImage.layoutParams.height = binding.backgroundImage.height
                binding.foregroundImage.layoutParams.width = binding.backgroundImage.width
                binding.sbImageSeek.max = binding.backgroundImage.width
                heightView = binding.backgroundImage.height
                binding.sliderBar.layoutParams.height = binding.backgroundImage.height
                binding.backgroundImage.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }

        })
    }


    fun setUpSlideCompare(backgroundData: Bitmap) {

        val resizedBackgroundBitmap = resizeBitmapToFitScreenWidth(backgroundData)
        binding.backgroundImage.setImageBitmap(resizedBackgroundBitmap)

        val foregroundBitmap = createTransparentBitmap(
            backgroundData.width,
            backgroundData.height
        )
        val resizedForegroundBitmap = resizeBitmapToFitScreenWidth(foregroundBitmap)
        binding.foregroundImage.setImageBitmap(resizedForegroundBitmap)

    }



    fun resizeBitmapToFitScreenWidth(bitmap: Bitmap): Bitmap {
        val screenWidth = Resources.getSystem().displayMetrics.widthPixels
        val aspectRatio = bitmap.height.toFloat() / bitmap.width.toFloat()
        val newHeight = (screenWidth * aspectRatio).toInt()
        return Bitmap.createScaledBitmap(bitmap, screenWidth, newHeight, true)
    }


    fun createTransparentBitmap(width: Int, height: Int): Bitmap {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        bitmap.eraseColor(android.graphics.Color.TRANSPARENT)
        return bitmap
    }


    fun setForegroundImage( foregroundData: Any) {
        loadIntoImageView(foregroundData, binding.foregroundImage)
    }


    private fun loadIntoImageView(data: Any, imageView: ImageView) {
        when (data) {
            is Bitmap -> Glide.with(context).load(data).into(imageView)
            is Drawable -> Glide.with(context).load(data).into(imageView)
            is String -> Glide.with(context).load(data).into(imageView)
            is Int -> Glide.with(context).load(data).into(imageView)
            else -> Log.e("ImageCompareSlider", "Unsupported data type")
        }
    }


    fun setHiddenForeground() {
        binding.foregroundImage.visibility = View.INVISIBLE
        binding.backgroundImage.visibility = View.VISIBLE
        setImageWidth(0)
        binding.sliderImage.visibility = View.GONE
        binding.sliderBar.visibility = View.GONE
    }

    fun setHiddenBackGround() {
        setImageWidth(binding.sbImageSeek.max)
        binding.backgroundImage.visibility = View.INVISIBLE
        binding.sliderImage.visibility = View.GONE
        binding.sliderBar.visibility = View.GONE

    }

    fun setPositionSlideImage(percent: Float) {
        binding.guideline.setGuidelinePercent(percent)
        invalidate()
    }

    private fun setImageWidth(progress: Int) {
        if (progress <= 0)
            return
        if (binding.foregroundImage.visibility == View.INVISIBLE || binding.backgroundImage.visibility == View.INVISIBLE) {
            binding.backgroundImage.visibility = View.VISIBLE
            binding.foregroundImage.visibility = View.VISIBLE
        }
        val lp: ViewGroup.LayoutParams = binding.target.layoutParams
        lp.width = progress
        binding.target.layoutParams = lp
    }
}