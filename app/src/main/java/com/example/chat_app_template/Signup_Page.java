package com.example.chat_app_template;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;

public class Signup_Page extends AppCompatActivity {

    TextInputLayout emailId;
    TextInputLayout password;
    TextInputLayout username;
    Button Signup;
    Button Fb;
    Button Google;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup__page);

        emailId=findViewById(R.id.edtEmailSignup);
        password=findViewById(R.id.edtPasswordSignup);
        username=findViewById(R.id.edtUsernameSignup);

        Signup=findViewById(R.id.btnSignupSignup);
        Fb=findViewById(R.id.btnFbSignup);
        Google=findViewById(R.id.btnGoogleSignup);

        Signup.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Perform signup activity
            }
        });
    }
}
