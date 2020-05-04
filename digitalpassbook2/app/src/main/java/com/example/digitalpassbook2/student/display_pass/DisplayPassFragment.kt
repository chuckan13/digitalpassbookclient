package com.example.digitalpassbook2.student.display_pass

import android.graphics.Bitmap
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
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.digitalpassbook2.MyUser
import com.example.digitalpassbook2.R
import com.example.digitalpassbook2.Util
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import java.text.SimpleDateFormat
import java.util.*


class DisplayPassFragment() : Fragment(), FragmentManager.OnBackStackChangedListener {

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

        val passId = args.passArg.toInt()
        val clubName = args.clubNameArg
        val clubLogo = args.clubLogoArg

        val progress = view.findViewById(R.id.loading_spinner) as ProgressBar
        val orgLogo = view.findViewById(R.id.pass_club_logo) as ImageView
        val orgLogosmall = view.findViewById(R.id.pass_club_logo_small) as ImageView
        val orgName = view.findViewById(R.id.pass_club_name) as TextView
        val studentName = view.findViewById(R.id.user_name) as TextView
        val orgQR = view.findViewById(R.id.pass_qr) as ImageView
        val orgQRsmall = view.findViewById(R.id.pass_qr_small) as ImageView
        val startDateDisplay = view.findViewById(R.id.start_date) as TextView
        val startTimeDisplay = view.findViewById(R.id.start_time) as TextView
        val passImage = view.findViewById(R.id.pass_image) as ImageView

        // retrieves the pass object associated with this pass, sets date and time TextViews
        displayPassViewModel.getPass(passId)
        displayPassViewModel.pass.observe(context as FragmentActivity, Observer { it ->
            val pass = (it ?: return@Observer)
            // extracts date and time strings from pass.date, and sets TextViews in the xml
            //val date = pass.date.substringBefore('T').substringAfter('-')
            //val time = pass.date.substringAfter('T').substringBefore('.').substringBeforeLast(':')

            val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
            val dateformatter = SimpleDateFormat("M/d")
            val date = formatter.parse(pass.date)
            val timeformatter = SimpleDateFormat("h:mm a")
            // these are the actual strings
            val formattedDate = dateformatter.format(date)
            val formattedTime = timeformatter.format(date)

            startDateDisplay.text = formattedDate
            startTimeDisplay.text = formattedTime

            //startDateDisplay.text = date
            //startTimeDisplay.text = time
        })

        orgLogo.setImageResource(resources.getIdentifier(clubLogo, "drawable", context?.packageName))
        orgLogosmall.setImageResource(resources.getIdentifier(clubLogo, "drawable", context?.packageName))
        // view.setBackgroundColor(resources.getIdentifier(clubLogo, "values", context?.packageName))
        passImage.setImageResource(R.drawable.ic_passbook)
        orgName.text = args.clubNameArg
        studentName.text = MyUser.name

        // this code can be made substantially faster by doing a couple of things
        // 1. only generate the qr code when the small qr code/logo is clicked
        // 2. use a generic qr code that is stored in drawables as the small qr code (no one will scan it)
        // 3. (design idea) we can add a buffering/spinning wheel while the qr code renders... see register fragment lines 69 and 141 for an example
        try {
            val qrText = ""+clubName+passId
            val bitmap = textToImageEncode(qrText)
            orgQR.setImageBitmap(bitmap)
            orgQRsmall.setImageBitmap(bitmap)
        }
        catch (e: WriterException) {
            e.printStackTrace()
        }

        orgLogo.setOnClickListener{
            orgLogo.toggleVisibility()
            orgQR.toggleVisibility()
            orgQRsmall.toggleVisibility()
            passImage.toggleVisibility()
            startDateDisplay.toggleVisibility()
            startTimeDisplay.toggleVisibility()
        }

        orgQR.setOnClickListener{
            orgLogo.toggleVisibility()
            orgQR.toggleVisibility()
            orgQRsmall.toggleVisibility()
            passImage.toggleVisibility()
            startDateDisplay.toggleVisibility()
            startTimeDisplay.toggleVisibility()
        }

        orgQRsmall.setOnClickListener{
            orgLogo.toggleVisibility()
            orgQR.toggleVisibility()
            orgQRsmall.toggleVisibility()
            passImage.toggleVisibility()
            startDateDisplay.toggleVisibility()
            startTimeDisplay.toggleVisibility()
        }

        passImage.setOnClickListener{
            orgLogo.toggleVisibility()
            orgQR.toggleVisibility()
            orgQRsmall.toggleVisibility()
            passImage.toggleVisibility()
            startDateDisplay.toggleVisibility()
            startTimeDisplay.toggleVisibility()
        }



        progress.visibility = View.INVISIBLE
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.student_toolbar_nav_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Util.onOptionsStudent(item, context)
        return super.onOptionsItemSelected(item)
    }

    override fun onBackStackChanged() {
    }

    private fun setNavigation(view : View) {
        val navController = Navigation.findNavController(
            context as FragmentActivity,
            R.id.student_nav_host_fragment
        )
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.navigation_eventbook, R.id.navigation_notifications))
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
        (activity as AppCompatActivity?)!!.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity?)!!.supportActionBar?.setDisplayShowHomeEnabled(true)
        (activity as AppCompatActivity?)!!.supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar.setupWithNavController(navController, appBarConfiguration)
        setHasOptionsMenu(true)
        fragmentManager?.addOnBackStackChangedListener(this)
        val navView: BottomNavigationView = view.findViewById(R.id.student_nav_view)
        navView.setupWithNavController(navController)
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
