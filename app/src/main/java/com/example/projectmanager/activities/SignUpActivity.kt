package com.example.projectmanager.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.WindowManager
import androidx.appcompat.widget.Toolbar
import com.example.projectmanager.R
import com.example.projectmanager.databinding.ActivitySignUpBinding

class SignUpActivity : BaseActivity() {
    private var binding: ActivitySignUpBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        // This is used to hide the status bar and make the splash screen as a full screen activity.
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setupActionBar()
    }

    private fun setupActionBar() {
        /// this is how we add a toolbar to behave like a toolbar
        var toolbar_sign_up_activity = findViewById<Toolbar>(R.id.toolbar_sign_up_activity)
        setSupportActionBar(toolbar_sign_up_activity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)
        }

        toolbar_sign_up_activity.setNavigationOnClickListener { onBackPressed() }

        binding!!.btnSignUp.setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser() {
        // Here we get the text from editText and trim the space
        /// .trim { it <= ' ' } => this trims the space in the text
        val name: String = binding!!.etName.text.toString().trim { it <= ' ' }
        val email: String = binding!!.etEmail.text.toString().trim { it <= ' ' }
        val password: String = binding!!.etPassword.text.toString().trim { it <= ' ' }

        if (validateForm(name, email, password)) {

            //// signup
        }
    }

    private fun validateForm(name: String, email: String, password: String): Boolean{
        var validation = when {
            /// we use lamda functions here
            TextUtils.isEmpty(name) -> {
                showErrorSnackBar("Please enter name")
                false
            }
            TextUtils.isEmpty(email) -> {
                showErrorSnackBar("Please enter email.")
                false
            }
            TextUtils.isEmpty(password) -> {
                showErrorSnackBar("Please enter password.")
                false
            }
            else -> {
                true
            }
        }

        return validation
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}