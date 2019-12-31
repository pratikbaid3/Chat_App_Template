package com.example.chat_app_template;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>
{
    private static final String TAG = "RecyclerViewAdapter";
    private ArrayList<String> mUsername = new ArrayList<>();
    private ArrayList<String> mUid=new ArrayList<>();
    private ArrayList<String> mNotification = new ArrayList<>();
    private Context mContext;


    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    public RecyclerViewAdapter(Context context, ArrayList<String> txtUsername, ArrayList<String> txtNotification ,ArrayList<String> uid)
    {
        mUsername = txtUsername;
        mNotification = txtNotification;
        mContext = context;
        mUid=uid;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem, parent, false);
        mAuth=FirebaseAuth.getInstance();
        currentUser=mAuth.getCurrentUser();
        mDatabase= FirebaseDatabase.getInstance().getReference();

        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position)
    {
        holder.txtUsername.setText(mUsername.get(position));
        holder.txtNotification.setText(mNotification.get(position));
        holder.imgIcon.setImageResource(R.drawable.profile_pic);


        //On click listener for all the tab item
        holder.parentLayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(v.getContext(),Chat_Page.class);
                intent.putExtra("uid",mUid.get(position));
                v.getContext().startActivity(intent);
                Toast.makeText(mContext, mUsername.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public int getItemCount()
    {
        return mUsername.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView txtUsername;
        TextView txtNotification;
        ImageView imgIcon;
        RelativeLayout parentLayout;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            txtUsername=itemView.findViewById(R.id.txtNameNotification);
            txtNotification=itemView.findViewById(R.id.txtNotification);
            imgIcon=itemView.findViewById(R.id.iconPic);
            parentLayout=itemView.findViewById(R.id.parent_layout);

        }
    }
}

