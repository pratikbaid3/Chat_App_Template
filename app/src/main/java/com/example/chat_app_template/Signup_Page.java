package com.example.chat_app_template;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Signup_Page extends AppCompatActivity {

    TextInputLayout emailId;
    TextInputLayout password;
    TextInputLayout username;
    Button Signup;
    Button Fb;
    Button Google;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup__page);

        mAuth=FirebaseAuth.getInstance();

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
                mAuth.createUserWithEmailAndPassword(emailId.getEditText().getText().toString(), password.getEditText().getText().toString())
                        .addOnCompleteListener(Signup_Page.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful())
                                {
                                    //Sign up successful
                                    FirebaseUser user = mAuth.getCurrentUser();
                                } 
                                else {
                                    //Signup error
                                    Toast.makeText(Signup_Page.this, "", Toast.LENGTH_SHORT).show();
                                }

                                // ...
                            }
                        });
            }
        });
    }
}
