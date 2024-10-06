package com.graduate.hou.handler;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WebSocketHandler extends TextWebSocketHandler{ //xử lý kết quả khi giao tiếp với client qua websocket

    @SuppressWarnings("null")
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        log.info("Message: {}", message.getPayload());
        session.sendMessage(new TextMessage(message.getPayload().toString()));
    }
}
