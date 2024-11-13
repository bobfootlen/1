package guru.nnd.tutorial.java101;

import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

public class SocketConfig implements WebSocketConfigurer{

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'registerWebSocketHandlers'");
    }

}
