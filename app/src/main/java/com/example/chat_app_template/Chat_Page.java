package com.example.chat_app_template;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import co.intentservice.chatui.ChatView;
import co.intentservice.chatui.models.ChatMessage;

public class Chat_Page extends AppCompatActivity
{
    private ChatView chatView;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    private boolean historyMessageLoaded=false;

    public class Conversation
    {

        public String senderUid;
        public String message;
        public long timestamp;
        public String time;

        public Conversation() {
            // Default constructor required for calls to DataSnapshot.getValue(User.class)
        }

        public Conversation(String senderP, String messageP,long timestampP,String timeP)
        {
            this.senderUid = senderP;
            this.message = messageP;
            this.timestamp=timestampP;
            this.time=timeP;

        }

    }
    public class UserConversation
    {

        public String conversationUid;
        public String message;

        public UserConversation() {
            // Default constructor required for calls to DataSnapshot.getValue(User.class)
        }

        public UserConversation(String conversationP, String messageP)
        {
            this.conversationUid = conversationP;
            this.message = messageP;
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat__page);

        final String uid=getIntent().getStringExtra("uid");

        mAuth=FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference();
        currentUser=mAuth.getCurrentUser();
        chatView=findViewById(R.id.chatView);


        //TODO: Debug the repeating sent message problem

        mDatabase.child("users").child(currentUser.getUid()).child("conversations").child(uid).addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.child("conversationUid").exists())
                {
                    String conversationUid=dataSnapshot.child("conversationUid").getValue().toString();
                    mDatabase.child("conversations").child(conversationUid).addChildEventListener(new ChildEventListener()
                    {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s)
                        {
                            String senderUid=dataSnapshot.child("senderUid").getValue().toString();


                            //Before the history messages have been loaded we will fetch all the messages on the server which includes messages to and from the current user
                            if(historyMessageLoaded==false)
                            {
                                if(senderUid.equals(currentUser.getUid()))
                                {
                                    //The message is a Sent message
                                    Long timestamp= (Long) dataSnapshot.child("timestamp").getValue();
                                    ChatMessage chatMessage=new ChatMessage(dataSnapshot.child("message").getValue().toString(),timestamp, ChatMessage.Type.SENT);
                                    chatView.addMessage(chatMessage);
                                }
                                else {
                                    //The message is a Received message
                                    ChatMessage chatMessage = new ChatMessage(dataSnapshot.child("message").getValue().toString(), (Long) dataSnapshot.child("timestamp").getValue(), ChatMessage.Type.RECEIVED);
                                    chatView.addMessage(chatMessage);
                                }
                            }

                            //After the history messages have been loaded we will fetch only the recieved messages
                            else
                            {
                                if(!(senderUid.equals(currentUser.getUid())))
                                {
                                    ChatMessage chatMessage = new ChatMessage(dataSnapshot.child("message").getValue().toString(), (Long) dataSnapshot.child("timestamp").getValue(), ChatMessage.Type.RECEIVED);
                                    chatView.addMessage(chatMessage);
                                }
                            }

                        }

                        @Override
                        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        }

                        @Override
                        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        chatView.setOnSentMessageListener(new ChatView.OnSentMessageListener()
        {
            @Override
            public boolean sendMessage(final ChatMessage chatMessage)
            {
                historyMessageLoaded=true;
                mDatabase.child("users").child(currentUser.getUid()).child("conversations").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                        if(dataSnapshot.child(uid).exists())
                        {
                            String conversationKey=dataSnapshot.child(uid).child("conversationUid").getValue().toString();

                            //Storing conversation data on the conversation node
                            Conversation conversation=new Conversation(currentUser.getUid(),chatMessage.getMessage(),chatMessage.getTimestamp(),chatMessage.getFormattedTime());
                            mDatabase.child("conversations").child(conversationKey).child(mDatabase.push().getKey()).setValue(conversation);

                            //Changing the latest message in the currentuser node
                            mDatabase.child("users").child(currentUser.getUid()).child("conversations").child(uid).child("message").setValue(chatMessage.getMessage());

                            //Changing the latest message in the otheruser node
                            mDatabase.child("users").child(uid).child("conversations").child(currentUser.getUid()).child("message").setValue(chatMessage.getMessage());
                        }

                        else
                        {
                            String conversationKey=mDatabase.push().getKey();

                            //Storing the conversation data in the current user node
                            UserConversation userConversation=new UserConversation(conversationKey,chatMessage.getMessage());
                            mDatabase.child("users").child(currentUser.getUid()).child("conversations").child(uid).setValue(userConversation);

                            //Storing the conversation data in the other user node
                            mDatabase.child("users").child(uid).child("conversations").child(currentUser.getUid()).setValue(userConversation);

                            //Storing conversation data on the conversation node
                            Conversation conversation=new Conversation(currentUser.getUid(),chatMessage.getMessage(),chatMessage.getTimestamp(),chatMessage.getFormattedTime());
                            mDatabase.child("conversations").child(conversationKey).child(mDatabase.push().getKey()).setValue(conversation);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                return true;
            }
        });

    }
}
