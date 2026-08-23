package com.fitness.aiService.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
public class GeminiService {

    private final WebClient webClient;

    @Value("${gemini.api.url}")
    private String geminiApiUrl;

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    public GeminiService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public String getAnswer(String question) {

        System.out.println("Sending request to Gemini...");
        System.out.println("Gemini URL: [" + geminiApiUrl + "]");

        Map<String, Object> requestBody = Map.of(
                "contents", new Object[]{
                        Map.of(
                                "parts", new Object[]{
                                        Map.of("text", question)
                                }
                        )
                }
        );

        return webClient.post()
                .uri(geminiApiUrl.trim())
                .header("Content-Type", "application/json")
                .header("x-goog-api-key", geminiApiKey)
                .bodyValue(requestBody)
                .exchangeToMono(response -> {

                    System.out.println("Gemini HTTP Status: " + response.statusCode());
                    System.out.println("Gemini Headers: " + response.headers().asHttpHeaders());

                    return response.bodyToMono(String.class)
                            .defaultIfEmpty("")
                            .map(body -> {

                                System.out.println("Gemini Response Body:");
                                System.out.println(body);

                                if (response.statusCode().isError()) {
                                    throw new RuntimeException(
                                            "Gemini API Error: "
                                                    + response.statusCode()
                                                    + " - "
                                                    + body
                                    );
                                }

                                if (body.isBlank()) {
                                    throw new RuntimeException(
                                            "Gemini returned an empty response body"
                                    );
                                }

                                return body;
                            });
                })
                .block();
    }
}