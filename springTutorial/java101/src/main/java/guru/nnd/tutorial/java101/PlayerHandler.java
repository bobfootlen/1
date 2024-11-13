package guru.nnd.tutorial.java101;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class PlayerHandler extends TextWebSocketHandler{
 private List<WebSocketSession> clients = new ArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        clients.add(session);
        String role = assignRole(clients.size());
        
        // Send role to the client
        session.sendMessage(new TextMessage("role:" + role));
    }

    private String assignRole(int connectionOrder) {
        if (connectionOrder == 1) return "x";
        if (connectionOrder == 2) return "o";
        return "spectator";
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        clients.remove(session); // Clean up after disconnect
    }
}
