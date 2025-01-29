package guru.nnd.tutorial.java101;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class PlayerHandler extends TextWebSocketHandler {

    private List<WebSocketSession> clients = new ArrayList<>();

    private class GameState {

        @JsonProperty("board")
        private char[][] board = {
                { ' ', ' ', ' ' },
                { ' ', ' ', ' ' },
                { ' ', ' ', ' ' }
        };

        @JsonProperty("activePlayer")
        private char currentPlayer = 'x';

        @JsonProperty("winState")
        private String winState;

    }

    private GameState gameState;
    private Map<WebSocketSession, String> players = new HashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        clients.add(session);
        String role = assignRole(session);

        // Send role to the client
        session.sendMessage(new TextMessage("role:" + role));
        for (var client : clients) {
            client.sendMessage(new TextMessage(
                    "state:" + new ObjectMapper()
                            .writeValueAsString(gameState)));
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // TODO Auto-generated method stub
        super.handleTextMessage(session, message);
        var payload = message.getPayload();
        String messageType = payload.substring(0, payload.indexOf(":"));
        var messageData = payload.substring(payload.indexOf(":") + 1);
        switch (messageType) {
            case "sample":
                // handle "Sample" message type
                System.out.println("Handle Sample");
                break;
            default:
                session.sendMessage(new TextMessage("error:Unhandled Message Type."));
        }
    }

    private String assignRole(WebSocketSession session) {
        if (!players.containsValue("x")) {
            players.put(session, "x");
        } else if (!players.containsValue("o")) {
            players.put(session, "o");
        } else {
            players.put(session, "spectator");
        }
        return players.get(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        players.remove(session);
        clients.remove(session); // Clean up after disconnect
    }
}
