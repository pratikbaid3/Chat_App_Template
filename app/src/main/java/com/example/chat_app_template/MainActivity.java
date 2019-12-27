package com.example.chat_app_template;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {

    TextInputLayout emailId;
    TextInputLayout password;
    Button Login;
    Button Signup;
    Button Fb;
    Button Google;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailId=findViewById(R.id.edtEmailIdLogin);
        password=findViewById(R.id.edtPasswordLogin);
        Login=findViewById(R.id.btnLoginLogin);
        Signup=findViewById(R.id.btnSignupLogin);
        Fb=findViewById(R.id.btnFbLogin);
        Google=findViewById(R.id.btnSignupLogin);

        Signup.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Signup button clicked on the login page
                Intent intent=new Intent(MainActivity.this,Signup_Page.class);
                startActivity(intent);
            }
        });
        Login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Login button clicked on the login page
            }
        });

    }
}
