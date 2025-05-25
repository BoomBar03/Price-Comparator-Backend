package com.project.PriceComparator.service;

import com.project.PriceComparator.model.Product;
import org.springframework.stereotype.Service;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class CsvService {

    public List<Product> readProductCsv(String fileName, String storeName, LocalDate date) {
        List<Product> products = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                new ClassPathResource("data/" + fileName).getInputStream()))) {

            String line = br.readLine(); // skip header
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                for (int i = 0; i < values.length; i++) {
                    values[i] = values[i].trim();
                }

                Product product = new Product(
                        values[0],
                        values[1],
                        values[2],
                        values[3],
                        Double.parseDouble(values[4]),
                        values[5],
                        Double.parseDouble(values[6]),
                        values[7],
                        storeName,
                        date
                );

                products.add(product);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return products;
    }
}
