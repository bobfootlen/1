package guru.nnd.tutorial.java101;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class PlayerHandler extends TextWebSocketHandler {

    private List<WebSocketSession> clients = new ArrayList<>();

    private Map<WebSocketSession,String> players = new HashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        clients.add(session);
        String role = assignRole(session);

        // Send role to the client
        session.sendMessage(new TextMessage("role:" + role));
    }

    private String assignRole(WebSocketSession session) {
        if(!players.containsValue("x")){
            players.put(session, "x");
        }else if(!players.containsValue("o"){
            players.put(session,"o");
        }else{
            players.put(session,"spectator");
        }
        return players.get(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        players.remove(session);
        clients.remove(session); // Clean up after disconnect
    }
}
