package vttp.batch4.csf.toiletnearme.configs;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import vttp.batch4.csf.toiletnearme.models.Message;
import vttp.batch4.csf.toiletnearme.models.OutputMessage;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
         registry.addEndpoint("/chat");
         registry.addEndpoint("/chat").withSockJS();
    }

    // message handler
    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public OutputMessage send(Message message) throws Exception {
        String time = new SimpleDateFormat("HH:mm").format(new Date());
        return new OutputMessage(message.getFrom(), message.getText(), time);
    }

}

// public void webSocket() {

// System.out.println("Starting web socket");

// try {
//     ServerSocket server = new ServerSocket(80);
    
//     System.out.println("Server has started on 127.0.0.1:80.\r\nWaiting for a connection…");
//     Socket client = server.accept();
//     System.out.println("A client connected.");
    
// } catch (IOException e1) {
//     e1.printStackTrace();
// }

// }

