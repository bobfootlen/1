package guru.nnd.tutorial.java101;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class PlayerHandler extends TextWebSocketHandler {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
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

    private GameState gameState = new GameState();
    private Map<WebSocketSession, String> players = new HashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        clients.add(session);
        String role = assignRole(session);

        // Send role to the client
        session.sendMessage(new TextMessage("role:" + role));
        sendState();
    }

    private void sendState() throws IOException, JsonProcessingException {
        for (var client : clients) {
            client.sendMessage(new TextMessage(
                    "state:" + OBJECT_MAPPER
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
            case "move":
                // handle "move" message type
                handlemove(session, messageData);
                break;
            case "restart":
                resetboard();
                // handle "Sample" message type
                System.out.println("Handle Sample");
                break;
            default:
                session.sendMessage(new TextMessage("error:Unhandled Message Type."));
        }
    }

    public void resetboard() throws JsonProcessingException, IOException {
        for (var i = 0; i < 3; i++) {
            for (var j = 0; j < 3; j++) {
                gameState.board[i][j] = ' ';
            }
        }
        if (' ' == gameState.currentPlayer) {
            gameState.currentPlayer = Math.floor(Math.random() * 100) % 2 == 1 ? 'x' : 'o';
        }
        if (gameState.winState != null) {
            gameState.winState = null;
        }
        sendState();
    }

    private static class MoveRequest {
        @JsonProperty
        private int row;
        @JsonProperty
        private int column;
    }

    private void handlemove(WebSocketSession session, String payload) throws IOException {
        if (gameState.currentPlayer != players.get(session).charAt(0)) {
            session.sendMessage(new TextMessage("error:Not your turn!"));
            return;
        }
        var request = OBJECT_MAPPER.readValue(payload, MoveRequest.class);
        if (request.column > 2 || request.column < 0 || request.row > 2 || request.row < 0) {
            session.sendMessage(new TextMessage("error:Row or Column Out of Bounds"));
            return;
        }
        if (gameState.board[request.row][request.column] != ' ') {
            session.sendMessage(new TextMessage("error:Space Already Occupied"));
            return;
        }
        gameState.board[request.row][request.column] = gameState.currentPlayer;
        var endgame = checkEndGame();
        if (endgame == null)
            gameState.currentPlayer = gameState.currentPlayer == 'x' ? 'o' : 'x';
        else {
            gameState.winState = endgame;

            gameState.currentPlayer = ' ';
        }
        sendState();
    }

    private String checkEndGame() {
        var board = gameState.board;
        for (var i = 0; i < 3; i++) {

            if (checkSet(board[i][0], board[i][1], board[i][2]))
                return "" + board[i][0] + " Wins";
        }
        for (var i = 0; i < 3; i++) {

            if (checkSet(board[0][i], board[1][i], board[2][i]))
                return "" + board[0][i] + " Wins";
        }
        if (checkSet(board[0][0], board[1][1], board[2][2]))
            return "" + board[0][0] + " Wins";
        if (checkSet(board[2][0], board[1][1], board[0][2]))
            return "" + board[2][0] + " Wins";
        if (boardToString(board).contains(" "))
            return null;
        else
            return "Cat's Game!";
    }

    private boolean checkSet(char c1, char c2, char c3) {
        return c1 != ' ' && c1 == c2 && c1 == c3;
    }

    private String boardToString(char[][] board) {
        StringBuilder builder = new StringBuilder(11);
        builder.append('"');
        builder.append(board[0]);
        builder.append(board[1]);
        builder.append(board[2]);
        builder.append('"');
        return builder.toString();
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
