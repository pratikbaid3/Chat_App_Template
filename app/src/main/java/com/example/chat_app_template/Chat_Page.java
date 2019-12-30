package com.example.chat_app_template;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import co.intentservice.chatui.ChatView;
import co.intentservice.chatui.models.ChatMessage;

public class Chat_Page extends AppCompatActivity
{
    private ChatView chatView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat__page);
        chatView=findViewById(R.id.chatView);
        chatView.setOnSentMessageListener(new ChatView.OnSentMessageListener()
        {
            @Override
            public boolean sendMessage(ChatMessage chatMessage)
            {
                // perform actual message sending
                return true;
            }
        });
    }
}
