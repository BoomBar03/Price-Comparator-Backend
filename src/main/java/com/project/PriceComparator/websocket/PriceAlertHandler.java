package com.project.PriceComparator.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class PriceAlertHandler extends TextWebSocketHandler {

    public static class Alert {
        public String productName;
        public double targetPrice;
    }

    private final Map<WebSocketSession, Alert> sessionAlerts = new ConcurrentHashMap<>();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        System.out.println("WebSocket connected: " + session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessionAlerts.remove(session);
        System.out.println("WebSocket disconnected: " + session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        try {
            Alert alert = mapper.readValue(message.getPayload(), Alert.class);
            sessionAlerts.put(session, alert);
            System.out.println("Received alert: " + alert.productName + " @ " + alert.targetPrice);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Map<WebSocketSession, Alert> getActiveAlerts() {
        return sessionAlerts;
    }
}
