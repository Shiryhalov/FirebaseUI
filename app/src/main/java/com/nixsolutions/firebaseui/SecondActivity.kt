package com.nixsolutions.firebaseui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity() {

    private var currentUser = FirebaseAuth.getInstance().currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
//        db()
        if (currentUser == null) {
            startActivity(Intent(this@SecondActivity, MainActivity::class.java))
        } else {
            setOnClickListeners()
            textView.text = currentUser?.displayName
            textView.text = currentUser?.displayName
            Glide.with(this)
                .load(currentUser?.photoUrl)
                .into(imageView)
        }
    }

    private fun setOnClickListeners() {
        button2.setOnClickListener {
            signOut()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)
            if (resultCode == Activity.RESULT_OK) {
                textView.text = currentUser?.displayName
                Glide.with(this)
                    .load(currentUser?.photoUrl)
                    .into(imageView)
            } else {
                response?.error?.errorCode
            }
        }
    }

    private fun signOut() {
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener {
            }
        startActivity(Intent(this@SecondActivity, MainActivity::class.java))
    }

    companion object {

        private const val RC_SIGN_IN = 123
    }
}