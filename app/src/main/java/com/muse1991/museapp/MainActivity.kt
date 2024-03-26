package com.muse1991.museapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.muse1991.museapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding
    private lateinit var signInClient: GoogleSignInClient
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setBinding()
        setSignInClient()
        setSignInButton()
        registerActivityResult()
    }

    private fun setBinding() {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.activity_main, null, false)
        binding.lifecycleOwner = this
        setContentView(binding.root)
    }

    private fun setSignInClient() {
        val signInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_client_id))
            .requestEmail()
            .build()
        signInClient = GoogleSignIn.getClient(this, signInOptions)
        auth = Firebase.auth
    }

    private fun setSignInButton() {
        binding.imageViewSignIn.setOnClickListener {
            val signInIntent = signInClient.signInIntent
            activityResultLauncher.launch(signInIntent)
        }
    }

    private fun registerActivityResult() {
        activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val idToken = GoogleSignIn.getSignedInAccountFromIntent(result.data).result.idToken
                val credential = GoogleAuthProvider.getCredential(idToken, null)

                auth.signInWithCredential(credential)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            //TODO 화면 이동
                        }
                    }
            }
        }
    }
}