package com.example.firebaseauthentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var userEmail:String
    lateinit var userPassword:String
    lateinit var etEmail:TextInputEditText
    lateinit var etPassword:TextInputEditText
    lateinit var etConfirmPassword:TextInputEditText
    lateinit var createAccountInputsArray:Array<EditText>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btncreateaccount: Button = findViewById(R.id.btnSignup)
        etEmail = findViewById(R.id.edtemail)
       etPassword = findViewById(R.id.edtpassword)
       etConfirmPassword = findViewById(R.id.edtconfirmpassword)

        createAccountInputsArray = arrayOf(etEmail,etPassword,etConfirmPassword)
        btncreateaccount.setOnClickListener{
            signIn()
        }


    }

    override fun onClick(p0: View?) {
        when(p0?.id)
        {
            R.id.btnSignIn->{
                startActivity(Intent(this,SignIn::class.java))
                finish()
            }

        }
    }

    private fun signIn() {
        if(identicalPassword()){
            userEmail = etEmail.text.toString().trim()
            userPassword = etPassword.text.toString().trim()
             FirebaseUtils.firebaseAuth.createUserWithEmailAndPassword(userEmail,userPassword).addOnCompleteListener{
                 if(it.isSuccessful){
                     Toast.makeText(this,"create account successfully",Toast.LENGTH_SHORT).show()
                     sendemailVerification()
                     startActivity(Intent(this,LoginIn::class.java))
                     finish()
                 }
             }

        }
    }

    private fun sendemailVerification() {
        FirebaseUtils.firebaseUser?.let{
            it.sendEmailVerification().addOnCompleteListener{
                if(it.isSuccessful){
                    Toast.makeText(this,"email sent to $userEmail",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun identicalPassword(): Boolean {
        var identical = false
        if(notEmpty()&&etPassword.text.toString().trim()==etConfirmPassword.text.toString().trim())
        {
            identical = true
        }
        else if (!notEmpty())
        {
            createAccountInputsArray.forEach{
                if(it.text.toString().trim().isEmpty())
                {
                    it.error = "${it.hint} is required"
                }
            }
        }
        else{
            Toast.makeText(this,"passwords are ot matching",Toast.LENGTH_LONG).show()
        }
        return identical

    }

    private fun notEmpty(): Boolean = etEmail.text.toString().trim().isNotEmpty()&&etPassword.text.toString().trim().isNotEmpty()&&etConfirmPassword.text.toString().trim().isNotEmpty()
}