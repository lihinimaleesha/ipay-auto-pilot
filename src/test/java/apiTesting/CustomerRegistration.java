package apiTesting;

import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CustomerRegistration {
@Test
    public static void customerRegistration() throws IOException {
        RestAssured.config = RestAssured.config().sslConfig(new SSLConfig().relaxedHTTPSValidation());

        // Registration details from JSON file
        String jsonFilePath = "src/test/resources/requests/registration.json";
        String requestBody = new String(Files.readAllBytes(Paths.get(jsonFilePath)));

        // Get access token from AccessToken class
        String accessToken = apiTesting.accessToken.getAccessToken();

        String registrationUrl = "https://staging.ipay.lk/apis/v3/customer/registrationNew/init";

        //    Sending POST request with request body and access token
        Response registrationResponse = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization", "Bearer "+accessToken)
                .body(requestBody)
                .post(registrationUrl);

        int registrationStatusCode = registrationResponse.getStatusCode();
        String registrationResponseBody = registrationResponse.getBody().asString();
    System.out.println(requestBody);
    System.out.println("registration url"+registrationUrl);
        System.out.println("------Access Token------" + accessToken);
        System.out.println("------User Registration Status Code------" + registrationStatusCode);
        System.out.println("------User Registration Response Body------" + registrationResponseBody);

        //   Validate  OTP message
        validateOTPMessage(registrationResponseBody);
    }

    private static void validateOTPMessage(String response) {
        String expectedMessage = "OTP is sent to your phone. Please enter the code to continue the registration.";

        // Check if the expected message is present in the response body
//        if (response.contains(expectedMessage)) {
//            System.out.println("Validation Passed: OTP message is correct.");
//        } else {
//            System.out.println("Validation Failed: OTP message is not as expected.");
//        }
    }
}

