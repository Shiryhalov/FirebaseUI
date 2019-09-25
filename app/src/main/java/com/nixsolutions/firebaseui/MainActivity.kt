package com.nixsolutions.firebaseui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createSignInIntent()
        db()
    }

    private fun createSignInIntent() {
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build()
        )

        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(),
            RC_SIGN_IN
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)
            if (resultCode == Activity.RESULT_OK) {
                val user = FirebaseAuth.getInstance().currentUser
                textView.text = user!!.displayName
                Glide.with(this)
                    .load(user.photoUrl)
                    .into(imageView)
            } else {
                response!!.error!!.errorCode
            }
        }
    }

    private fun signOut() {
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener {
            }
    }

    companion object {

        private const val RC_SIGN_IN = 123
    }

    private fun db() {
        val docRef = FirebaseFirestore.getInstance()
            .collection("profiles")
            .document("profile")

        val data1 = hashMapOf(
            "favourite" to "article 2",
            "liked" to listOf("1", "2", "3"),
            "sources" to hashMapOf(
                "isMediumOn" to true,
                "isDevtoOn" to true
            )
        )
        docRef.update(
            FieldPath.of("sources", "isDevtoOn"), false
        )

        docRef.get().addOnSuccessListener { document ->
            if (document != null) {
                Log.d("111", document.get("liked").toString())
            } else {
                Log.d("333", "333")
            }
        }.addOnFailureListener { exception -> Log.d("222", "222") }

        //docRef.set(data1)

//        docRef.get().addOnSuccessListener { document ->
//            if (document != null) {
//                val hash = document.get("sources") as HashMap<String,Boolean>
//                Log.d("111", hash.toString())
//            } else {
//                Log.d("333", "333")
//            }
//        }.addOnFailureListener { exception -> Log.d("222", "222") }
    }
}
