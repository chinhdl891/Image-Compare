package com.chinchin.image.compare

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import java.io.ByteArrayOutputStream

object Util {

    fun compressBitmapFromPath(context: Context, imagePath: String, quality: Int): Bitmap? {
        return try {
            // Load bitmap from the file path
            val bitmap = BitmapFactory.decodeFile(imagePath) ?: return null

            // Compress and return the bitmap
            compressBitmap(bitmap, quality)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun compressBitmapFromDrawable(context: Context, drawable: Drawable, quality: Int): Bitmap? {
        return try {
            // Convert drawable to bitmap
            val bitmap = (drawable as? BitmapDrawable)?.bitmap ?: return null

            // Compress and return the bitmap
            compressBitmap(bitmap, quality)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun compressBitmap(bitmap: Bitmap, quality: Int): Bitmap? {
        return try {
            val outputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
            val byteArray = outputStream.toByteArray()
            BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

}