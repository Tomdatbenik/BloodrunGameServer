package bloodrunserver.models.dto;

import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

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

    public static void PublicCreateRequest(String url, String message, Class object) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity;
        entity = new HttpEntity<>(message, headers);

        RestTemplate restTemplate = new RestTemplate();

        restTemplate.postForEntity(url, entity, object);
    }
}
