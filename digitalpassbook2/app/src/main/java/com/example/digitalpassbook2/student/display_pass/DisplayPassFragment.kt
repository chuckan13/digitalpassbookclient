package com.example.digitalpassbook2.student.display_pass

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextClock
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import com.example.digitalpassbook2.R
import com.example.digitalpassbook2.student.MyStudent
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix


class DisplayPassFragment() : Fragment() {

    private lateinit var displayPassViewModel: DisplayPassViewModel

    private val args: DisplayPassFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        displayPassViewModel = ViewModelProviders.of(this).get(DisplayPassViewModel::class.java)
        return inflater.inflate(R.layout.fragment_display_pass, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val passId = args.passArg.toInt()
        val clubName = args.clubNameArg
        val clubLogo = args.clubLogoArg

        val progress = view.findViewById(R.id.loading_spinner) as ProgressBar
        val orgLogo = view.findViewById(R.id.pass_club_logo) as ImageView
        val orgName = view.findViewById(R.id.pass_club_name) as TextView
        val studentName = view.findViewById(R.id.user_name) as TextView
        val orgQR = view.findViewById(R.id.pass_qr) as ImageView
        val clock = view.findViewById(R.id.clock) as TextClock

        orgLogo.setImageResource(resources.getIdentifier(clubLogo, "drawable", context?.packageName))
        orgName.text = args.clubNameArg
        studentName.text = MyStudent.name

        try {
            val qrText = ""+clubName+passId
            val bitmap = textToImageEncode(qrText)
            orgQR.setImageBitmap(bitmap)
        }
        catch (e: WriterException) {
            e.printStackTrace()
        }

        orgQR.visibility = View.VISIBLE
        progress.visibility = View.INVISIBLE
    }

    // code to create QR code image
    @Throws(WriterException::class)
    private fun textToImageEncode(Value: String): Bitmap? {
        val qrCodeWidth = 500
        val bitMatrix: BitMatrix
        try {
            bitMatrix = MultiFormatWriter().encode(
                Value,
                BarcodeFormat.QR_CODE,
                qrCodeWidth, qrCodeWidth, null
            )
        } catch (IllegalArgumentException: IllegalArgumentException) {
            return null
        }
        val bitMatrixWidth = bitMatrix.width
        val bitMatrixHeight = bitMatrix.height
        val pixels = IntArray(bitMatrixWidth * bitMatrixHeight)
        for (y in 0 until bitMatrixHeight) {
            val offset = y * bitMatrixWidth
            for (x in 0 until bitMatrixWidth) {
                pixels[offset + x] =
                if (bitMatrix.get(x, y))
                    resources.getColor(R.color.white)
                else
                    resources.getColor(R.color.black)
            }
        }
        val bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444)
        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight)
        return bitmap
    }
}
