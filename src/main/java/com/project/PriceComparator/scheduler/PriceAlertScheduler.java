// src/main/java/com/project/PriceComparator/scheduler/PriceAlertScheduler.java
package com.project.PriceComparator.scheduler;

import com.project.PriceComparator.websocket.PriceAlertHandler;
import com.project.PriceComparator.websocket.PriceAlertHandler.Alert;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@EnableScheduling
public class PriceAlertScheduler {

    private final PriceAlertHandler alertHandler;
    private final Map<String, Double> triggeredAlerts = new ConcurrentHashMap<>();

    public PriceAlertScheduler(PriceAlertHandler alertHandler) {
        this.alertHandler = alertHandler;
    }

    @Scheduled(fixedRate = 10000)   //citim o data la 10s
    public void checkAlerts() {
        for (Map.Entry<WebSocketSession, Alert> entry : alertHandler.getActiveAlerts().entrySet()) {
            WebSocketSession session = entry.getKey();
            Alert alert = entry.getValue();

            String alertKey = session.getId() + ":" + alert.productName;

            // dacă targetul s-a schimbat ștergem alerta precedentă
            if (triggeredAlerts.containsKey(alertKey) && triggeredAlerts.get(alertKey) != alert.targetPrice) {
                triggeredAlerts.remove(alertKey);
            }
            if (triggeredAlerts.containsKey(alertKey)) continue;

            try {
                File folder = new ClassPathResource("data/").getFile();
                File[] files = folder.listFiles((dir, name) -> name.matches(".*_\\d{4}-\\d{2}-\\d{2}\\.csv") && !name.contains("discounts"));
                if (files == null) continue;

                for (File file : files) {
                    String fileName = file.getName();
                    String storeName = fileName.split("_")[0];

                    BufferedReader reader = new BufferedReader(new InputStreamReader(
                            new ClassPathResource("data/" + fileName).getInputStream()));
                    reader.readLine();

                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] values = line.split(";");
                        String name = values[1].trim();
                        double price = Double.parseDouble(values[6].trim());

                        if (name.equalsIgnoreCase(alert.productName) && price <= alert.targetPrice) {
                            String response = String.format("{\"alertTriggered\":true,\"productName\":\"%s\",\"storeName\":\"%s\",\"price\":%.2f,\"targetPrice\":%.2f}",
                                    name, storeName, price, alert.targetPrice);
                            session.sendMessage(new TextMessage(response));
                            triggeredAlerts.put(alertKey, alert.targetPrice); //marcat in sesiune pentru a nu fi notificat in loop
                            break;
                        }
                    }
                    reader.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
