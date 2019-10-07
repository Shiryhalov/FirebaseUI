package com.nixsolutions.firebaseui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class MainActivity : AppCompatActivity() {

    private var currentUser = FirebaseAuth.getInstance().currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // db()
        if (currentUser == null) {
            startAuth()
        } else {
            startActivity(Intent(this@MainActivity, SecondActivity::class.java))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            if (resultCode == Activity.RESULT_OK) {
                startActivity(Intent(this@MainActivity, SecondActivity::class.java))
            } else {
                Toast.makeText(this, "Sign In Failed", Toast.LENGTH_SHORT).show()
                startAuth()
            }
        }
    }

    private fun startAuth() {
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build()
        )

        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setTheme(R.style.AppTheme)
                .build(),
            RC_SIGN_IN
        )
    }

    companion object {

        private const val RC_SIGN_IN = 123
    }
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //


    ////////////////-----------------------------------------------------------------/////////////

    private fun db() {
        FirebaseFirestore.getInstance().collection("profiles").document("articles")

        //--------------------------------------

        val article = hashMapOf(
            "title" to "android",
            "source" to "sss",
            "dsafas" to "fasdfas"
        )

        val docRef = FirebaseFirestore
            .getInstance()
            .collection("profiles")
            .document("profile")


        //-------------------------

        val data1 = hashMapOf(
            "favourite" to "article 2",
            "liked" to listOf("1", "2", "3"),
            "sources" to hashMapOf(
                "isMediumOn" to true,
                "isDevtoOn" to true
            )
        )
        docRef
            .collection("likedArticles")
            .document("article1")
            .set(Article("odin", null))
            .addOnSuccessListener { Log.d("ok", "ok") }
            .addOnFailureListener { Log.d("ne ok", "ne ok") }

//        docRef.get().addOnSuccessListener { document ->
//            if (document != null) {
//                val docName = docRef.id
//
//                Log.d("docName: ", docName)
//                Log.d("111", document.get("liked").toString())
//            } else {
//                Log.d("333", "333")
//            }
//        }.addOnFailureListener { exception -> Log.d("222", "222") }


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
