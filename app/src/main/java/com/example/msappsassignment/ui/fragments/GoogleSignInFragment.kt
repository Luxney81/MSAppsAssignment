package com.example.msappsassignment.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.msappsassignment.R
import com.example.msappsassignment.util.Constants
import com.example.msappsassignment.util.Utilities
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.fragment_google_sign_in.*


class GoogleSignInFragment : Fragment(R.layout.fragment_google_sign_in) {

    lateinit var googleSignInClient: GoogleSignInClient

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (GoogleSignIn.getLastSignedInAccount(view.context) != null) {
            login()
        } else {
            googleSignInClient = GoogleSignIn.getClient(view.context, Utilities.googleSignInOptions)
            signInButton.setSize(SignInButton.SIZE_WIDE)
            signInButton.setOnClickListener {
                signIn()
            }
        }
    }

    private fun signIn() {
        val signInIntent: Intent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, Constants.RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constants.RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>){
        try {
            Toast.makeText(this.context, "You have logged into your Google account!", Toast.LENGTH_SHORT).show()
            login()
        } catch (e: ApiException) {
            Log.w("sign_IN", "signInResult:failed code=" + e.statusCode)
        }
    }

    private fun login() = findNavController().navigate(
        R.id.action_googleSignInFragment_to_favoriteArticlesFragment,
    )
}