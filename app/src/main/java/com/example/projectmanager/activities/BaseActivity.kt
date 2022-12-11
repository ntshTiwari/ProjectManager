package com.example.projectmanager.activities

import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.projectmanager.R
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

/// Keyword open in kotlin means "Open for Extension"
/// means if you want any class to be inherited by any subclass or method
//  to be overriden in subclasses you have to mark as open
//  otherwise you will get compile time error
open class BaseActivity: AppCompatActivity() {
    /// is used to track if the back button is pressed once,
    /// if pressed again, we will exit the app
    private var doubleBackToExitPressedOnce = false

    /// This is a progress dialog instance which we will initialize later on.
    private lateinit var mProgressDialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun showProgressDialog(text: String){
        mProgressDialog = Dialog(this)

        /// we set the content of the dialog box here
        mProgressDialog.setContentView(R.layout.dialog_progress)

        /// we set the text of the dialog to the text passed
        mProgressDialog.findViewById<TextView>(R.id.tv_progress_text).text = text

        mProgressDialog.show()
    }

    fun hideProgressDialog() {
        mProgressDialog.dismiss()
    }

    fun getCurrentUserID(): String {
        return FirebaseAuth.getInstance().currentUser!!.uid
    }

    fun doubleBackToExit() {
        if(doubleBackToExitPressedOnce){
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(
            this,
            resources.getString(R.string.please_click_back_again_to_exit),
            Toast.LENGTH_SHORT
        ).show()

        Handler().postDelayed({
            doubleBackToExitPressedOnce = false
        }, 2000)
    }

    fun showErrorSnackBar(message: String) {
        val snackBar =
            Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
        val snackBarView = snackBar.view
        snackBarView.setBackgroundColor(
            ContextCompat.getColor(
                this@BaseActivity,
                R.color.snackbar_error_color
            )
        )
        snackBar.show()
    }
}