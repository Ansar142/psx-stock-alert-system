package com.psx.common;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PsxApiTest {

    public static void main(String[] args) {
        System.out.println("=== PSX Stock Price Test ===\n");
        System.out.println("Fetching REAL-TIME PSX data...\n");

        // Test with popular PSX stocks
        String[] symbols = {"HUBC", "MARI", "ENGRO", "LUCK", "SYS", "MEBL"};

        for (String symbol : symbols) {
            fetchStockPrice(symbol);
            try {
                Thread.sleep(500); // Small delay to be nice to the API
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("\n=== Test Complete ===");
    }

    private static void fetchStockPrice(String symbol) {
        try {
            // PSX Terminal API endpoint
            String url = "https://psxterminal.com/api/ticks/REG/" + symbol;

            // Create HTTP client
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("User-Agent", "Mozilla/5.0")
                    .header("Accept", "application/json")
                    .GET()
                    .build();

            // Send request
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                // Parse JSON response
                ObjectMapper mapper = new ObjectMapper();
                JsonNode root = mapper.readTree(response.body());

                // Check if API returned success
                if (root.has("success") && root.get("success").asBoolean()) {
                    // Data is nested inside "data" object
                    JsonNode data = root.get("data");

                    if (data != null) {
                        String symbol_ = data.path("symbol").asText(symbol);
                        double price = data.path("price").asDouble();
                        long volume = data.path("volume").asLong();
                        double change = data.path("changePercent").asDouble();
                        double high = data.path("high").asDouble();
                        double low = data.path("low").asDouble();
                        long trades = data.path("trades").asLong();

                        // Calculate percentage for display
                        double changePercent = change * 100;

                        // Determine trend emoji
                        String trend = change >= 0 ? "📈" : "📉";
                        String colorCode = change >= 0 ? "\u001B[32m" : "\u001B[31m"; // Green or Red for console

                        System.out.printf("%s %s%s\u001B[0m: PKR %.2f | %s%.4f%%\u001B[0m | Vol: %,d | High: %.2f | Low: %.2f | Trades: %d%n",
                                trend, colorCode, symbol_, price,
                                change >= 0 ? "+" : "", changePercent,
                                volume, high, low, trades);
                    } else {
                        System.out.printf("⚠️ %s: No data available%n", symbol);
                    }
                } else {
                    System.out.printf("⚠️ %s: API returned error%n", symbol);
                }
            } else {
                System.out.printf("❌ %s: HTTP %d%n", symbol, response.statusCode());
            }

        } catch (Exception e) {
            System.out.printf("❌ %s: Error - %s%n", symbol, e.getMessage());
        }
    }
}