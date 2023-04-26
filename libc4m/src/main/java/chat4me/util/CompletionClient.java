package chat4me.util;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;

public class CompletionClient {
    private HttpClient httpClient;
    private HttpRequest.Builder requestBuilder;
    public CompletionClient() {
        httpClient = HttpClient.newHttpClient();
        requestBuilder = HttpRequest.newBuilder();
    }
    public String sendCompletionRequest() {
        return "generated message text here";
    }
}
