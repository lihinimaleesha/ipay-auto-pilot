package apiTesting;

import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class test1 {
    @Test
    public void getAccessTokenAndUserRegistration() {
        RestAssured.config = RestAssured.config().sslConfig(new SSLConfig().relaxedHTTPSValidation());

        //  Get Access Token
        String accessTokenUrl = "https://staging.ipay.lk/apis/token";
        Map<String, String> accessTokenFormParams = new HashMap<>();
        accessTokenFormParams.put("grant_type", "client_credentials");
        accessTokenFormParams.put("client_id", "Meq_qTUrqSVHvXetqIXX8R8rNfoa");
        accessTokenFormParams.put("client_secret", "mZDItwX1896lxAlvqIPLyZFKQvUa");

        Response accessTokenResponse = RestAssured.given()
                .urlEncodingEnabled(true)
                .contentType(ContentType.URLENC)
                .formParams(accessTokenFormParams)
                .post(accessTokenUrl);

        String accessToken = accessTokenResponse.jsonPath().getString("access_token");

        //  User Registration
        String registrationUrl = "https://staging.ipay.lk/apis/v3/customer/registrationNew/init";
        String mobileNo = "";
        String email = "lihinimaleesha9@gmail.com";
        String platform = "string";
        String deviceId = "867108065711502/01";

        // Constructing the request body
        String requestBody = String.format("{\"mobileNo\":\"%s\",\"email\":\"%s\",\"platform\":\"%s\",\"deviceId\":\"%s\"}",
                mobileNo, email, platform, deviceId);

        // Sending POST request with the constructed request body and access token
        Response registrationResponse = RestAssured.given()
                .urlEncodingEnabled(true)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + accessToken)
                .body(requestBody)
                .post(registrationUrl);

        int registrationStatusCode = registrationResponse.getStatusCode();
        String registrationResponseBody = registrationResponse.getBody().asString();


        System.out.println("------Access Token------" + accessToken);
        System.out.println("------User Registration Status Code------" + registrationStatusCode);
        System.out.println("------User Registration Response Body------" + registrationResponseBody);
    }
}
