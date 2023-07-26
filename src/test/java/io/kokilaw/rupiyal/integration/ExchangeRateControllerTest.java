package io.kokilaw.rupiyal.integration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.kokilaw.rupiyal.RupiyalApiServiceApplication;
import org.json.JSONException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import java.io.IOException;

/**
 * Created by kokilaw on 2023-07-27
 */
@SpringBootTest(
        classes = RupiyalApiServiceApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@Sql(value = {"/test-data/exchange-rate-controller-test-data-1.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/test-data/exchange-rate-controller-test-data-1-reset.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class ExchangeRateControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("Correct latest rates summary is returned")
    void whenLatestRatesSummaryIsRequested_correctRatesAreReturned() throws JSONException, IOException {
        String requestPath = "/internal/exchange-rates/summary";
        String requestUrl = String.format("http://localhost:%d%s", this.port, requestPath);
        ResponseEntity<String> response = this.restTemplate.getForEntity(requestUrl, String.class);

        JsonNode expectedResponse = objectMapper.readValue(new ClassPathResource("test-expected-response/latest-exchange-rates-summary-1.json").getFile(), JsonNode.class);
        JSONAssert.assertEquals(expectedResponse.toPrettyString(), response.getBody(), false);
    }

    @Test
    @DisplayName("Correct rates summary is returned for a specific date")
    void whenRatesSummaryIsRequestedForSpecificDate_correctRatesAreReturned() throws JSONException, IOException {

        String TARGET_DATE = "2023-07-20";

        String requestPath = "/internal/exchange-rates/summary";
        String requestUrl = String.format("http://localhost:%d%s?targetDate=%s", this.port, requestPath, TARGET_DATE);
        ResponseEntity<String> response = this.restTemplate.getForEntity(requestUrl, String.class);

        JsonNode expectedResponse = objectMapper.readValue(new ClassPathResource("test-expected-response/specific-date-exchange-rates-summary-1.json").getFile(), JsonNode.class);
        JSONAssert.assertEquals(expectedResponse.toPrettyString(), response.getBody(), false);
    }

}
