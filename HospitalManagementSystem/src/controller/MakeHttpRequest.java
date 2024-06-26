package controller;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import org.json.JSONObject;

public class MakeHttpRequest {
	private String accessToken;
	
	 public HttpResponse<String> makeHttpRequest(String url, String method, JSONObject jsonObject, String accessToken) {
		this.accessToken = accessToken;

        try {
            HttpClient httpClient = HttpClient.newHttpClient();

            // Debugging
            System.out.println("URL: " + url);
            System.out.println("Method: " + method);
            System.out.println("JSON Payload: " + (jsonObject != null ? jsonObject.toString() : "null"));
            System.out.println("Access Token: " + accessToken);

            // Check if URL is valid
            URI uri = URI.create(url);
            HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                    .uri(uri)
                    .header("Content-Type", "application/json");

            if (accessToken != null && !accessToken.isEmpty()) {
                requestBuilder.header("Authorization", "Bearer " + accessToken);
            }

            HttpRequest request;
            if ("POST".equalsIgnoreCase(method)) {
                request = requestBuilder
                        .POST(HttpRequest.BodyPublishers.ofString(jsonObject.toString()))
                        .build();

            } else if ("GET".equalsIgnoreCase(method)) {
                request = requestBuilder
                        .GET()
                        .build();

            } else if ("PATCH".equalsIgnoreCase(method)) {
                request = requestBuilder
                        .method("PATCH", HttpRequest.BodyPublishers.ofString(jsonObject.toString()))
                        .build();

            } else if ("DELETE".equalsIgnoreCase(method)) {
                request = requestBuilder
                        .DELETE()
                        .build();

            } else {
                throw new IllegalArgumentException("Invalid HTTP method: " + method);
            }

            return httpClient.send(request, BodyHandlers.ofString());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
