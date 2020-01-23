package bloodrunserver.models.dto;

import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MicroServiceRequest {
    public static Object CreateRequest(String url, String message, Object object, HttpMethod method) {
        HttpHeaders headers = new HttpHeaders();

        String token = MessageConverter.getToken(message);
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity;

        if (method == HttpMethod.POST) {
            JSONObject postData = new JSONObject(message).getJSONObject("Content");
            entity = new HttpEntity<>(postData.toString(), headers);

        } else {
            entity = new HttpEntity<>(headers);
        }

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<?> response = restTemplate.exchange(url, method, entity, object.getClass());

        return response.getBody();
    }

    public static void PublicCreateRequest(String url, String message, Object object, HttpMethod method) throws IOException {

        final String POST_PARAMS = message;
        System.out.println(POST_PARAMS);
        URL obj = new URL(url);
        HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
        postConnection.setRequestMethod("POST");
        postConnection.setRequestProperty("Content-Type", "application/json");
        postConnection.setDoOutput(true);
        OutputStream os = postConnection.getOutputStream();
        os.write(POST_PARAMS.getBytes());
        os.flush();
        os.close();
        int response = postConnection.getResponseCode();

        System.out.println(response);
    }
}
