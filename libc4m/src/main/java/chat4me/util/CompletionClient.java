package chat4me.util;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class CompletionClient {
    private HttpClient httpClient;
    private String proxyServer;
    private URI requestUri;
    public CompletionClient(String urlString) {
        httpClient = HttpClient.newHttpClient();
        requestUri = URI.create(urlString);
        proxyServer = urlString;
    }
    public void sendCompletionRequest(String messages, CompletionMessageHandler handler) throws IOException, InterruptedException {
        HashMap<String, String> formMap = new HashMap<>();
        formMap.put("messages", messages);
        StringBuilder formBuilder = new StringBuilder();

        for (Map.Entry<String, String> param: formMap.entrySet()) {
            if (formBuilder.length() > 0) {
                formBuilder.append("&");
            }
            formBuilder.append(URLEncoder.encode(param.getKey(), StandardCharsets.UTF_8));
            formBuilder.append("=");
            formBuilder.append(URLEncoder.encode(param.getValue(), StandardCharsets.UTF_8));
        }

        HttpRequest req = HttpRequest.newBuilder()
            .uri(requestUri)
            .header("X-C4m", "y")
            .header("Content-Type", "application/x-www-form-urlencoded")
            .POST(HttpRequest.BodyPublishers.ofString(formBuilder.toString())).build();
        HttpResponse<String> resp =  httpClient.send(req, HttpResponse.BodyHandlers.ofString());
        System.out.printf("Response %d\n", resp.statusCode());
        System.out.println(req.uri());
        System.out.println(resp);
        handler.onReceiveCompletion(resp.statusCode(), resp.body());
    }
}
