package com.example.msappsassignment.util

import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class Utilities {
    companion object {
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
    }
}