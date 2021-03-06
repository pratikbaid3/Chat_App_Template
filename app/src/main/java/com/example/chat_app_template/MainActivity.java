package com.example.chat_app_template;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    TextInputLayout emailId;
    TextInputLayout password;
    Button Login;
    Button Signup;
    Button Fb;
    Button Google;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    static boolean calledAlready=false;

    @Override
    protected void onStart()
    {
        super.onStart();
        FirebaseUser currentUser=mAuth.getCurrentUser();
        if(currentUser!=null)
        {
            Intent intent=new Intent(MainActivity.this,Messages_Page.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!calledAlready)
        {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
            calledAlready = true;
        }
        mAuth=FirebaseAuth.getInstance();

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
                mAuth.signInWithEmailAndPassword(emailId.getEditText().getText().toString(), password.getEditText().getText().toString())
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful())
                                {
                                    //Signin successful
                                    FirebaseUser currentUser = mAuth.getCurrentUser();
                                    Intent intent=new Intent(MainActivity.this,Messages_Page.class);
                                    startActivity(intent);
                                    finish();

                                }
                                else {
                                    //Signin failed
                                    Toast.makeText(MainActivity.this, "Authentication failed.",Toast.LENGTH_SHORT).show();
                                }

                                // ...
                            }
                        });
            }
        });

    }
}
