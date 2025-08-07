package com.chatApp.chatapplication.Controller;

import com.chatApp.chatapplication.model.Message;
import com.chatApp.chatapplication.service.WebSocketSessionManager;
import jdk.jfr.MemoryAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
@Controller
public class WebSocketController {
    private final SimpMessagingTemplate messagingTemplate;
    private final WebSocketSessionManager sessionManager;



    @Autowired
    public WebSocketController(SimpMessagingTemplate messagingTemplate,WebSocketSessionManager sessionManager){
        this.sessionManager = sessionManager;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/message")
    public void handleMessage(Message message){
        System.out.println("Received message from user: "+message.getUser()+": "+message.getMessage());
        messagingTemplate.convertAndSend("/topic/messages ",message);
        System.out.println("Sent message to /topic/message: "+ message.getUser()+": "+message.getMessage());
    }

    @MessageMapping("/connect")
    public void connectUser(String username){
        sessionManager.addUsername(username);
        sessionManager.broadcastActiveUsername();
        System.out.println(username+" connected");
    }

    @MessageMapping("/disconnect")
    public void disconnectUser(String username){
        sessionManager.removeUsername(username);
        sessionManager.broadcastActiveUsername();
        System.out.println(username+" disconnected");
    }


}
