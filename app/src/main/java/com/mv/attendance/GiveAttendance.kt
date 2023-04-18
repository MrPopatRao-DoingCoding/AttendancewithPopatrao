package com.mv.attendance

import android.content.SharedPreferences
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.github.alexzhirkevich.customqrgenerator.QrData
import com.github.alexzhirkevich.customqrgenerator.style.Color
import com.github.alexzhirkevich.customqrgenerator.vector.QrCodeDrawable
import com.github.alexzhirkevich.customqrgenerator.vector.QrVectorOptions
import com.github.alexzhirkevich.customqrgenerator.vector.style.*
import se.simbio.encryption.Encryption
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class GiveAttendance : AppCompatActivity() {
    private lateinit var qrCodeImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(  //Make the activity Un-ScreenShotAble
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )
        setContentView(R.layout.activity_give_attendance)


        qrCodeImageView = findViewById(R.id.idImageViewQRCode)  // Linking the QRCode Textview with the xml file


        val sh = getSharedPreferences("MySharedPref", MODE_PRIVATE)   // Open SharedPreferences
        val drawable = createQRCode(sh)  // Create QR Code

        if(kotlin.math.abs(sh.getLong("savedTime", 167666442) - getTime()) > 20) {  // Check if the specified time has passed since changing settings ////////Change time here!!
            qrCodeImageView.setImageDrawable(drawable)  // Set the QR Code on the ImageView
        }

    }

    private fun createQRCode(sh : SharedPreferences) : Drawable {
        val current_time = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val formatted_date = current_time.format(formatter)

        var finalString = "{}|" + formatted_date + "|" + sh.getString("Roll No", "") + "|" + sh.getString("Div", "") + "|" + sh.getString("Name", "") + "|" + sh.getString("PRN", "") + "|"
        val key = formatted_date
        val salt = "Mith"
        val iv = ByteArray(16)
        val encryption = Encryption.getDefault(key, salt, iv)
        finalString = encryption.encryptOrNull(finalString)
        val data: QrData = QrData.Text(finalString)
        val options = createQROptions()

        return QrCodeDrawable(applicationContext, data, options)
    }

    private fun createQROptions(): QrVectorOptions {

        return QrVectorOptions.Builder()
            .padding(.3f)
            .colors(
                QrVectorColors(
                    dark = QrVectorColor
                        .Solid(Color(0xff111111)),
                    ball = QrVectorColor.Solid(
                        ContextCompat.getColor(applicationContext, R.color.black_shade_1)
                    )
                )
            )
            .shapes(
                QrVectorShapes(
                    darkPixel = QrVectorPixelShape
                        .RoundCorners(.5f),
                    ball = QrVectorBallShape
                        .RoundCorners(.25f),
                    frame = QrVectorFrameShape
                        .RoundCorners(.25f),
                )
            )
            .build()
    }


    private fun getTime(): Long {
        return System.currentTimeMillis() / 1000L
    }
}