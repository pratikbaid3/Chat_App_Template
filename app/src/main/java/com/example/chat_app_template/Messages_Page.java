package com.example.chat_app_template;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class Messages_Page extends AppCompatActivity
{
    private ArrayList<String> mUsername=new ArrayList<>();
    private ArrayList<String> mNotification=new ArrayList<>();
    private ArrayList<String> mUid=new ArrayList<>();
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private FloatingActionButton btnAddMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages__page);

        for(int i=0;i<10;i++)
        {
            mUid.add("sfdkmsdlkfm");
            mUsername.add("Pratik");
            mNotification.add("HOLA");
        }

        initImageBitmaps();
    }
    private void initImageBitmaps()
    {
        Log.i("Pratik","Entered initImageBitmap");
        initRecyclerView();
    }

    private void initRecyclerView()
    {
        RecyclerView recyclerView = findViewById(R.id.recycler_view_messages);
        RecyclerViewAdapter_Messages adapter = new RecyclerViewAdapter_Messages(Messages_Page.this, mUsername, mNotification,mUid);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(Messages_Page.this));
    }
}
