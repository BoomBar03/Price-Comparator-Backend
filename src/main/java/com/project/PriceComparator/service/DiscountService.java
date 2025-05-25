// src/main/java/com/project/PriceComparator/service/DiscountService.java
package com.project.PriceComparator.service;

import com.project.PriceComparator.dto.BestDiscountResponse;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.*;

@Service
public class DiscountService {

    public List<BestDiscountResponse> getTopDiscountsAllStores(int limit) {
        List<BestDiscountResponse> discounts = new ArrayList<>();
        String folderPath = "data/";
        String basePath;

        try {
            basePath = new ClassPathResource(folderPath).getFile().getPath();
        } catch (Exception e) {
            e.printStackTrace();
            return discounts;
        }

        File folder = new File(basePath);
        File[] files = folder.listFiles((dir, name) -> name.matches(".*_discounts_\\d{4}-\\d{2}-\\d{2}\\.csv"));

        if (files == null) return discounts;

        for (File discountFile : files) {
            String discountFileName = discountFile.getName();

            try {
                String[] parts = discountFileName.replace(".csv", "").split("_discounts_");
                String storeName = parts[0];
                String date = parts[1];
                String priceFileName = storeName + "_" + date + ".csv";

                Map<String, Double> oldPrices = readOldPrices(folderPath + priceFileName);
                List<BestDiscountResponse> storeDiscounts = readDiscountsForFile(
                        folderPath + discountFileName, storeName, oldPrices, null
                );
                discounts.addAll(storeDiscounts);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        discounts.sort(Comparator.comparingDouble(BestDiscountResponse::getDiscountPercent).reversed());

        return discounts.stream().limit(limit).toList();
    }

    public List<BestDiscountResponse> getNewDiscounts(LocalDate referenceDate) {
        List<BestDiscountResponse> newDiscounts = new ArrayList<>();
        String folderPath = "data/";
        String basePath;

        try {
            basePath = new ClassPathResource(folderPath).getFile().getPath();
        } catch (Exception e) {
            e.printStackTrace();
            return newDiscounts;
        }

        File folder = new File(basePath);
        File[] files = folder.listFiles((dir, name) -> name.matches(".*_discounts_\\d{4}-\\d{2}-\\d{2}\\.csv"));    //cautam daor in fisierele care contin cuvantul "discounts"

        if (files == null) return newDiscounts;

        for (File discountFile : files) {
            String discountFileName = discountFile.getName();

            try {
                String[] parts = discountFileName.replace(".csv", "").split("_discounts_");
                String storeName = parts[0];
                String date = parts[1];
                String priceFileName = storeName + "_" + date + ".csv";

                Map<String, Double> oldPrices = readOldPrices(folderPath + priceFileName);
                List<BestDiscountResponse> storeDiscounts = readDiscountsForFile(
                        folderPath + discountFileName, storeName, oldPrices, referenceDate
                );
                newDiscounts.addAll(storeDiscounts);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return newDiscounts;
    }

    private Map<String, Double> readOldPrices(String priceFilePath) {
        Map<String, Double> oldPrices = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                new ClassPathResource(priceFilePath).getInputStream()))) {

            String line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(";");
                String productId = values[0].trim();
                double price = Double.parseDouble(values[6].trim());
                oldPrices.put(productId, price);
            }
        } catch (Exception e) {
            // Poate lipsi fișierul
        }
        return oldPrices;
    }

    private List<BestDiscountResponse> readDiscountsForFile(String discountFilePath, String storeName,
                                                            Map<String, Double> oldPrices,
                                                            LocalDate referenceDate) {
        List<BestDiscountResponse> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                new ClassPathResource(discountFilePath).getInputStream()))) {

            String line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(";");
                String productId = values[0].trim();
                String productName = values[1].trim();
                double discountPercent = Double.parseDouble(values[8].trim());
                LocalDate fromDate = LocalDate.parse(values[6].trim());

                if (referenceDate != null && fromDate.isBefore(referenceDate.minusDays(1))) {
                    continue; // ignoră reducerile mai vechi de 24h
                }

                if (oldPrices.containsKey(productId)) {
                    double oldPrice = oldPrices.get(productId);
                    double newPrice = oldPrice * (1 - discountPercent / 100.0); //calculam si noul pret pentru a fi mai usor utilizatorului de a vizualiza reducerea

                    result.add(new BestDiscountResponse(
                            productName,
                            capitalize(storeName),
                            oldPrice,
                            newPrice,
                            discountPercent
                    ));
                }
            }
        } catch (Exception e) {
            // Ignoră fișiere lipsă
        }
        return result;
    }

    private String capitalize(String word) {
        return word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
    }
}
