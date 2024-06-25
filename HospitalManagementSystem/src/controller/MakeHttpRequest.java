package controller;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;

import org.json.JSONObject;

public class MakeHttpRequest {
	
	
	 public HttpResponse<String> makeHttpRequest(String url, String method, JSONObject jsonObject) {
	        try {
	            HttpClient httpClient = HttpClient.newHttpClient();

	            if ("POST".equalsIgnoreCase(method)) {
	        
	                HttpRequest request = HttpRequest.newBuilder()
	                        .uri(new URI(url))
	                        .header("Content-Type", "application/json")
	                        .POST(BodyPublishers.ofString(jsonObject.toString()))
	                        .build();

	                return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
	               

	            } else if ("GET".equalsIgnoreCase(method)) {
	                HttpRequest request = HttpRequest.newBuilder()
	                        .uri(new URI(url))
	                        .GET()
	                        .build();

	               return  httpClient.send(request, HttpResponse.BodyHandlers.ofString());
	          
	            }
	            else if ("PATCH".equalsIgnoreCase(method)) {
	                HttpRequest request = HttpRequest.newBuilder()
	                    .uri(new URI(url))
	                    .header("Content-Type", "application/json")
	                    .method("PATCH", HttpRequest.BodyPublishers.ofString(jsonObject.toString()))
	                    .build();
	                
	                return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
	            }
	            else if ("DELETE".equalsIgnoreCase(method)) {
	                HttpRequest request = HttpRequest.newBuilder()
	                        .uri(new URI(url))
	                        .DELETE()
	                        .build();
	               
	               return  httpClient.send(request, HttpResponse.BodyHandlers.ofString());
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
			return null;

	 }
}
