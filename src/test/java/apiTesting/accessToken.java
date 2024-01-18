package apiTesting;

import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class accessToken {
    public static String getAccessToken() {
        RestAssured.config = RestAssured.config().sslConfig(new SSLConfig().relaxedHTTPSValidation());

        String url = "https://staging.ipay.lk/apis/token";

        // Request parameters as form parameters
        Map<String, String> formParams = new HashMap<>();
        formParams.put("grant_type", "client_credentials");
        formParams.put("client_id", "Meq_qTUrqSVHvXetqIXX8R8rNfoa");
        formParams.put("client_secret", "mZDItwX1896lxAlvqIPLyZFKQvUa");

        // Sending POST request with form parameters
        Response response = RestAssured.given()
                .urlEncodingEnabled(true)
                .contentType(ContentType.URLENC)
                .formParams(formParams)
                .post(url);

        int statusCode = response.getStatusCode();
        String responseBody = response.getBody().asString();

        System.out.println("------status code------" + statusCode);
        System.out.println("------response body------" + responseBody);

        // Assuming the access token is present in the response body
        String accessToken = response.jsonPath().getString("access_token");
        return accessToken;
    }
}
