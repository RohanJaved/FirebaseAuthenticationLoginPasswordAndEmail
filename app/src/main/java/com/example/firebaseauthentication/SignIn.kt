package com.example.firebaseauthentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText

class SignIn : AppCompatActivity() {
    private lateinit var signinEmail:String
    private lateinit var signinPassword:String
    private lateinit var etSignInEmail:TextInputEditText
    private lateinit var etSignInPassword:TextInputEditText
    private lateinit var signInInputsArray:Array<TextInputEditText>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        etSignInEmail = findViewById(R.id.edtsigninemail)
        etSignInPassword = findViewById(R.id.edtsigninpassword)
        val btsignIn: Button = findViewById(R.id.btnsignin)
        signInInputsArray = arrayOf(etSignInEmail,etSignInPassword)
        btsignIn.setOnClickListener{
            signInUser()
        }
    }

    private fun signInUser() {
        signinEmail = etSignInEmail.text.toString().trim()
        signinPassword = etSignInPassword.text.toString().trim()
        if(notEmpty()){
            FirebaseUtils.firebaseAuth.signInWithEmailAndPassword(signinEmail,signinPassword).addOnCompleteListener{
                if(it.isSuccessful)
                {
                    startActivity(Intent(this,LoginIn::class.java))
                    finish()
                }
                else{
                    Toast.makeText(this,"Sign In Failed",Toast.LENGTH_LONG).show()
                }
            }
        }
        else{
            signInInputsArray.forEach{
                if(it.text.toString().trim().isEmpty())
                    it.error = "${it.hint} is required"
            }
        }
    }
    private fun notEmpty():Boolean = signinEmail.isNotEmpty() && signinPassword.isNotEmpty()
}