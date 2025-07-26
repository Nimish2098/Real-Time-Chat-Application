package com.chatApp.chatapplication.Client;

import com.chatApp.chatapplication.model.Message;

import java.util.ArrayList;

public interface MessageListener {
    void onMessageReceiev(Message message);
    void onActiveUsersUpdated(ArrayList<String> users);
}
