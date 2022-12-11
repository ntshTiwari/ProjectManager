package com.example.projectmanager.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.projectmanager.R
import com.example.projectmanager.databinding.ActivitySignInBinding
import com.example.projectmanager.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth

class SignInActivity : BaseActivity() {
    private var binding: ActivitySignInBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        //// temporarily added
        FirebaseAuth.getInstance().signOut()

        // This is used to hide the status bar and make the splash screen as a full screen activity.
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setupActionBar()

        binding!!.btnSignIn.setOnClickListener {
            signInUser()
        }
    }

    private fun setupActionBar() {
        var toolbar_sign_in_activity = findViewById<Toolbar>(R.id.toolbar_sign_in_activity)
        setSupportActionBar(toolbar_sign_in_activity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)
        }

        toolbar_sign_in_activity.setNavigationOnClickListener { onBackPressed() }
    }

    fun signInUser() {
        val email: String = binding!!.etEmail.text.toString().trim { it <= ' ' }
        val password: String = binding!!.etPassword.text.toString().trim { it <= ' ' }

        if(validateForm(email, password)){
            showProgressDialog("Signing in...")

            FirebaseAuth.getInstance().signInWithEmailAndPassword(
                email, password
            ).addOnCompleteListener{
                    task ->
                        hideProgressDialog()
                        if(task.isSuccessful){
                            Toast.makeText(
                                this@SignInActivity,
                                "Signed in",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                this@SignInActivity,
                                task.exception!!.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
            }
        }
    }

    private fun validateForm( email: String, password: String): Boolean{
        var validation = when {
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