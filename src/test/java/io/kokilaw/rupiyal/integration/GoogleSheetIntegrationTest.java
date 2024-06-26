package io.kokilaw.rupiyal.integration;

import io.kokilaw.rupiyal.RupiyalApiServiceApplication;
import io.kokilaw.rupiyal.dto.BankDTO;
import io.kokilaw.rupiyal.dto.FetchTaskDTO;
import io.kokilaw.rupiyal.dto.ProcessorType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by kokilaw on 2023-07-04
 */

@SpringBootTest(
        classes = RupiyalApiServiceApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class GoogleSheetIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;


    @BeforeAll
    public static void setup() {
    }

    @Test
    @DisplayName("Required test data is initialised when application starts")
    void whenApplicationStarts_testDataIsInitialised() {
        String requestPath = "/internal/banks";
        String requestUrl = String.format("http://localhost:%d%s", this.port, requestPath);
        ResponseEntity<BankDTO[]> response = this.restTemplate.getForEntity(requestUrl, BankDTO[].class);

        Map<String, BankDTO> bankEntriesByBankCode = Arrays.stream(Objects.requireNonNull(response.getBody()))
                .collect(Collectors.toMap(BankDTO::bankCode, Function.identity()));

        assertEquals(13, bankEntriesByBankCode.keySet().size());
        assertTrue(bankEntriesByBankCode.containsKey("NTB"));
        assertTrue(bankEntriesByBankCode.containsKey("COMBANK"));
        assertTrue(bankEntriesByBankCode.containsKey("HNB"));
        assertTrue(bankEntriesByBankCode.containsKey("SAMPATH"));
        assertTrue(bankEntriesByBankCode.containsKey("BOC"));
    }

    @Test
    @Disabled("In order to avoid load on API")
    @DisplayName("Currency data is fetched and saved when google sheet fetch is initiated")
    void whenGoogleSheetFetchInitiated_currencyDataIsFetchedAndSaved() {

        String requestPath = "/internal/tasks/fetch";
        String requestUrl = String.format("http://localhost:%d%s", this.port, requestPath);
        FetchTaskDTO payload = new FetchTaskDTO(ProcessorType.GOOGLE_SHEET_API);
        assertDoesNotThrow(() -> this.restTemplate.postForEntity(requestUrl, payload, Void.class));

    }

    @Test
    @Disabled("In order to avoid load on API")
    @DisplayName("Currency data is fetched and saved when google sheet fetch is initiated with custom period")
    void whenGoogleSheetFetchInitiated_withCustomPeriod_currencyDataIsFetchedAndSaved() {

        String requestPath = "/internal/tasks/fetch";
        String requestUrl = String.format("http://localhost:%d%s", this.port, requestPath);
        FetchTaskDTO payload = new FetchTaskDTO(
                ProcessorType.GOOGLE_SHEET_API,
                LocalDate.of(2023, 5, 30),
                LocalDate.of(2023, 5, 30)
        );
        assertDoesNotThrow(() -> this.restTemplate.postForEntity(requestUrl, payload, Void.class));

    }

}
