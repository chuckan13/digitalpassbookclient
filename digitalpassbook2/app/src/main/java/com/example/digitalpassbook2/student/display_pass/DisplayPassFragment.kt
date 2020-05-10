package com.example.digitalpassbook2.student.display_pass

import android.graphics.Bitmap
import android.media.Image
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextClock
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.digitalpassbook2.MyUser
import com.example.digitalpassbook2.R
import com.example.digitalpassbook2.Util
import com.example.digitalpassbook2.server.Pass
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import java.text.SimpleDateFormat
import java.util.*

class DisplayPassFragment() : Fragment() {

    private lateinit var displayPassViewModel: DisplayPassViewModel

    private val args: DisplayPassFragmentArgs by navArgs()

    // function for switching an element's visibility between VISIBLE and INVISIBLE
    private fun View.toggleVisibility() {
        visibility = when {
            (visibility == View.VISIBLE) -> View.INVISIBLE
            else -> View.VISIBLE
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        displayPassViewModel = ViewModelProviders.of(this).get(DisplayPassViewModel::class.java)
        return inflater.inflate(R.layout.fragment_display_pass, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setNavigation(view)

        val pass: Pass = args.passArg
        val clubLogo = args.clubLogoArg

        val orgLogo = view.findViewById(R.id.pass_club_logo) as ImageView
        val orgLogosmall = view.findViewById(R.id.pass_club_logo_small) as ImageView
        val orgName = view.findViewById(R.id.pass_club_name) as TextView
        val studentName = view.findViewById(R.id.user_name) as TextView
        val startDateDisplay = view.findViewById(R.id.start_date) as TextView
        val startTimeDisplay = view.findViewById(R.id.start_time) as TextView

        orgLogo.setImageResource(resources.getIdentifier(clubLogo, "drawable", context?.packageName))
        orgLogosmall.setImageResource(resources.getIdentifier(clubLogo, "drawable", context?.packageName))
        orgName.text = args.clubNameArg
        studentName.text = MyUser.name

        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
        val dateFormatter = SimpleDateFormat("M/d")
        val date: Date = formatter.parse(pass.date)
        val timeFormatter = SimpleDateFormat("h:mm a")

        // these are the actual strings
        val formattedDate = dateFormatter.format(date)
        val formattedTime = timeFormatter.format(date)

        startDateDisplay.text = formattedDate
        startTimeDisplay.text = formattedTime

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.student_toolbar_nav_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Util.onOptionsStudent(item, context)
        return super.onOptionsItemSelected(item)
    }

    private fun setNavigation(view : View) {
        val navController = Navigation.findNavController(
            context as FragmentActivity,
            R.id.student_nav_host_fragment
        )
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.navigation_eventbook))
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
        (activity as AppCompatActivity?)!!.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity?)!!.supportActionBar?.setDisplayShowHomeEnabled(true)
        (activity as AppCompatActivity?)!!.supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar.setupWithNavController(navController, appBarConfiguration)
        setHasOptionsMenu(true)
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
