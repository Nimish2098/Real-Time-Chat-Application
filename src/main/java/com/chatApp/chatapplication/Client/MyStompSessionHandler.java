package com.chatApp.chatapplication.Client;

import com.chatApp.chatapplication.model.Message;
import org.springframework.messaging.simp.stomp.*;

import java.lang.reflect.Type;

public class MyStompSessionHandler extends StompSessionHandlerAdapter {
    private String username;
    private MessageListener messageListener;
    public MyStompSessionHandler(MessageListener messageListener,String username){
        this.username = username;
        this.messageListener = messageListener;
    }

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        System.out.println("Client Connected");
        session.send("/app/connect",username);
        session.subscribe("/topic/message", new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return Message.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                try{
                    if(payload instanceof Message){
                        messageListener.onMessageReceiev(new Message("sfj","jndn"));
                        Message message = (Message) payload;
                        System.out.println("Received message:"+message.getUser()+":"+message.getMessage() );
                    }
                    else{
                        System.out.println("Received unexpected payload type: "+payload.getClass());
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        System.out.println("Client Subscribe to /topic/message");
    }
@Override
    public void handleTransportError(StompSession session,Throwable exception){
        exception.printStackTrace();
}

}
